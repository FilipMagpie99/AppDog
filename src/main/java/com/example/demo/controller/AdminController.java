package com.example.demo.controller;

import com.example.demo.models.Posting;
import com.example.demo.models.User;
import com.example.demo.service.PostingService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private UserService userService;

    @Autowired
    public AdminController(UserService userService){
        this.userService = userService;
    }
    @Autowired
    PostingService postingService;

    @GetMapping
    public String adminHome(Model model){
        List<Posting> posting = postingService.getPostings();
        model.addAttribute("posting", posting);
        return "homePageAdmin.html";
    }

    @GetMapping("/deletePosting/{postingId}")
    public String deleteBook(Model model
            ,@PathVariable( value="postingId" ) Integer postingId){
        Optional<Posting> posting = postingService.getPosting(postingId);
        postingService.deletePosting(postingId);
        model.addAttribute("posting",posting);
        return "redirect:/admin";
    }

    @GetMapping("/updatePosting/{postingId}")
    public String updatePosting(Model model
            ,@PathVariable( value="postingId" ) Integer postingId){
        Optional<Posting> posting = postingService.getPosting(postingId);
        model.addAttribute("posting",posting);
        return "updatePosting.html";
    }



    @GetMapping("/admin/{userId}")
    ResponseEntity<User> getUser(@PathVariable Long userId){
        return ResponseEntity.of(userService.getUser(userId));
    }

    @GetMapping("/admins")
    List<User> getUsers(){
        return userService.getUsers();
    }

    @PostMapping(path = "/admin")
    ResponseEntity<Void> createUser(@RequestBody User user){
        User createdUser= userService.setUser(user);
        URI location= ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{adminId}").buildAndExpand(createdUser.getUserId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/admins/{adminId}")
    ResponseEntity<Void> updateUser(@RequestBody User user,@PathVariable Long userId){
        return userService.getUser(userId)
                .map(p->{userService.setUser(user);
                    return new ResponseEntity<Void>(HttpStatus.OK);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/admins/{adminId}")
    ResponseEntity<Void> deleteShelter(@PathVariable Long userId){
        return  userService.getUser(userId).map(p->{
            userService.deleteUser(userId);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }).orElseGet(()->ResponseEntity.notFound().build());
    }
}
