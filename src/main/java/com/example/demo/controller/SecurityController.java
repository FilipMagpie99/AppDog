package com.example.demo.controller;

import com.example.demo.models.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class SecurityController {
    @Autowired
    BCryptPasswordEncoder pwEncoder;

    @Autowired
    UserService accService;

    @GetMapping(value="/login")
    public String login() {
        return "security/login.html";
    }

    @GetMapping(value="/logout")
    public String logout() {
        return "security/logout.html";
    }

    @GetMapping(value="/register")
    public String register(Model model) {
        model.addAttribute("newAccount", new User());
        return "security/register.html";
    }

    @PostMapping(value="/register/save")
    public String saveNewAccount(User account) {
        account.setPassword(pwEncoder.encode(account.getPassword()));
        accService.setUser(account);
        return "redirect:/login";
    }

    @GetMapping(value="/register/accountcreated")
    public String accountCreated() {
        return "security/account-created.html";
    }
}
