package com.example.demo.security;

import com.example.demo.models.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CurrentUserFinder {
    @Autowired
    UserService userService;

    public long getCurrentUserId(){
        UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username = details.getUsername();
        long userId = -1;
        for(User user : userService.getUsers()){
            if(user.getName().equals(username)){
                userId = user.getUserId();
                break;
            }
        }
        return userId;
    }

    public Optional<User> getCurrentUser(){
        Optional<User> currentUser = userService.getUser(getCurrentUserId());
        return currentUser;
    }
}
