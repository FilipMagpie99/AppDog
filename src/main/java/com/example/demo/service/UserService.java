package com.example.demo.service;

import com.example.demo.models.DogShelter;
import com.example.demo.models.Posting;
import com.example.demo.models.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    Optional<User> getUser(Long userId);
    List<User> getUsers();
    User setUser(User user);
    void deleteUser(Long userId);
}
