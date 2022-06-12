package com.example.demo.controller;

import com.example.demo.models.DogShelter;
import com.example.demo.models.Posting;
import com.example.demo.models.User;
import com.example.demo.security.CurrentUserFinder;
import com.example.demo.service.DogShelterService;
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
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    @Autowired
    CurrentUserFinder currentUserFinder;

    @Autowired
    PostingService postingService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public String userHome(Model model){
        List<Posting> posting = postingService.getPostings();
        model.addAttribute("posting", posting);
        return "homePage2.html";
    }
//    @GetMapping
//    public String userProfile(Model model){
//        Optional<User> currentUser = currentUserFinder.getCurrentUser();
//        currentUser.ifPresent(user -> model.addAttribute("currUser", user));
//        currentUser.ifPresent(user -> model.addAttribute("userPostings", user.getUserPostings()));
//        return "homePage.html";
//    }

    @GetMapping("/{userId}")
    ResponseEntity<User> getUser(@PathVariable Long userId){
        return ResponseEntity.of(userService.getUser(userId));
    }

    @GetMapping("/userProfile")
    public String userProfile(Model model){
        Optional<User> currentUser = currentUserFinder.getCurrentUser();
        currentUser.ifPresent(user -> model.addAttribute("currUser", user));
        currentUser.ifPresent(user -> model.addAttribute("userPostings", user.getUserPostings()));
        return "userProfile.html";
    }




    @PostMapping(path = "/")
    ResponseEntity<Void> createUser(@RequestBody User user){
        User createdUser= userService.setUser(user);
        URI location= ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{userId}").buildAndExpand(createdUser.getUserId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/all/{userId}")
    ResponseEntity<Void> updateUser(@RequestBody User user,@PathVariable Long userId){
        return userService.getUser(userId)
                .map(p->{userService.setUser(user);
                    return new ResponseEntity<Void>(HttpStatus.OK);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/all/{userId}")
    ResponseEntity<Void> deleteShelter(@PathVariable Long userId){
        return  userService.getUser(userId).map(p->{
            userService.deleteUser(userId);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }).orElseGet(()->ResponseEntity.notFound().build());
    }
}
