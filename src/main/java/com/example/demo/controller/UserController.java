package com.example.demo.controller;

import com.example.demo.models.DogShelter;
import com.example.demo.models.Posting;
import com.example.demo.models.User;
import com.example.demo.security.CurrentUserFinder;
import com.example.demo.service.DogShelterService;
import com.example.demo.service.PostingService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/search")
    public String userHome1(String keyword, Model model, Pageable pageable) {
        Long userId = currentUserFinder.getCurrentUserId();
        if (keyword == "") {
            return "redirect:/user";
        } else {

            int pageSize = 5;
            Page<Posting> page = postingService.findPaginatedByName(1, pageSize, "name", "asc", keyword);
            List<Posting> posting = page.getContent();
            model.addAttribute("currentPage", 1);
            model.addAttribute("totalPages", page.getTotalPages());
            model.addAttribute("totalItems", page.getTotalElements());
            model.addAttribute("sortField", "name");
            model.addAttribute("sortDir", "asc");
            model.addAttribute("reverseSortDir", "desc");
            model.addAttribute("posting", posting);
            model.addAttribute("userId", userId);
            return "sortedHomePage";
        }
    }

    @GetMapping
    public String userHome(Model model) {
        return "redirect:/user/page/1?sortField=name&sortDir=asc";
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 5;
        Page<Posting> page = postingService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Posting> posting = page.getContent();
        String keyword = " ";
        Long userId = currentUserFinder.getCurrentUserId();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("posting", posting);
        model.addAttribute("userId", userId);
        return "sortedHomePage";
    }

    @GetMapping("/{userId}")
    ResponseEntity<User> getUser(@PathVariable Long userId) {
        return ResponseEntity.of(userService.getUser(userId));
    }

    @GetMapping("/userProfile")
    public String userProfile(Model model) {
        Optional<User> currentUser = currentUserFinder.getCurrentUser();
        currentUser.ifPresent(user -> model.addAttribute("currUser", user));
        currentUser.ifPresent(user -> model.addAttribute("userPostings", user.getUserPostings()));
        return "userProfile.html";
    }
/*
    @GetMapping("/userProfile/page/{pageNo}")
    public String userProfilePaginated(@PathVariable(value = "pageNo") int pageNo,
                                       @RequestParam("sortField") String sortField,
                                       @RequestParam("sortDir") String sortDir,
                                       Model model) {
        int pageSize = 5;

        Page<Posting> page = postingService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Posting> posting = page.getContent();
        String keyword = " ";
        Long userId = currentUserFinder.getCurrentUserId();

        Optional<User> currentUser = currentUserFinder.getCurrentUser();
        currentUser.ifPresent(user -> model.addAttribute("currUser", user));
        currentUser.ifPresent(user -> model.addAttribute("userPostings", user.getUserPostings()));
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        return "userProfile.html";
    }
*/

    @PostMapping(path = "/")
    ResponseEntity<Void> createUser(@RequestBody User user) {
        User createdUser = userService.setUser(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{userId}").buildAndExpand(createdUser.getUserId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/all/{userId}")
    ResponseEntity<Void> updateUser(@RequestBody User user, @PathVariable Long userId) {
        return userService.getUser(userId)
                .map(p -> {
                    userService.setUser(user);
                    return new ResponseEntity<Void>(HttpStatus.OK);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/changePassword/{userId}")
    public String changePassword(Model model
            ,@PathVariable( value="userId" ) Long userId){
        Optional<User> user = userService.getUser(userId);
        model.addAttribute("user",user);
        return "changepassword.html";
    }
    @PostMapping("/saveUserPassword")
    public String saveUserPassword(@ModelAttribute("user") User user,
                                   @RequestParam(value = "oldpassword") String oldpassword,
                                   @RequestParam(value = "newpassword") String newpassword){
        if(bCryptPasswordEncoder.matches(oldpassword,user.getPassword()) && oldpassword!=null && newpassword!=null) {
            user.setPassword(bCryptPasswordEncoder.encode(newpassword));
            userService.setUser(user);
        }
        return "redirect:/";
    }
}
