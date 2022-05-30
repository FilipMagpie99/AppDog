package com.example.demo.gui;
import com.example.demo.service.AdminService;
import com.example.demo.service.DogShelterService;
import com.example.demo.service.PostingService;
import com.example.demo.service.UserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@Route("/ui")
public class MainPage extends VerticalLayout {

    @Autowired
    private PostingService postingService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;
    @Autowired
    private DogShelterService dogShelterService;

    @PostConstruct
    public void init(){
        TextField textField= new TextField("aaa");
        add(textField);
    }
}
