package com.example.demo.controller;

import com.example.demo.models.DogShelter;
import com.example.demo.models.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/user/{userId}")
    ResponseEntity<User> getUser(@PathVariable Long userId){
        return ResponseEntity.of(userService.getUser(userId));
    }

    @GetMapping("/users")
    List<User> getUsers(){
        return userService.getUsers();
    }

    @PostMapping(path = "/user")
    ResponseEntity<Void> createUser(@Valid @RequestBody User user){
        User createdUser= userService.setUser(user);
        URI location= ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{userId}").buildAndExpand(createdUser.getUserId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/users/{userId}")
    ResponseEntity<Void> updateUser(@Valid @RequestBody User user,@PathVariable Long userId){
        return userService.getUser(userId)
                .map(p->{userService.setUser(user);
                    return new ResponseEntity<Void>(HttpStatus.OK);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/users/{userId}")
    ResponseEntity<Void> deleteShelter(@PathVariable Long userId){
        return  userService.getUser(userId).map(p->{
            userService.deleteUser(userId);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }).orElseGet(()->ResponseEntity.notFound().build());
    }
}
