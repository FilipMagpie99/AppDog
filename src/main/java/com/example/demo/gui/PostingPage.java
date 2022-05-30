package com.example.demo.gui;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import javax.annotation.PostConstruct;

@Route("/home/post")
public class PostingPage extends VerticalLayout {

    @PostConstruct
    public void init() {
        Label name = new Label(MainPage.optionalPosting.get().getName());
        Label description = new Label(MainPage.optionalPosting.get().getDescription());
        Label user = new Label(MainPage.optionalPosting.get().getUser().getName());
        HorizontalLayout horizontalLayout = new HorizontalLayout(name, description, user);

        add(horizontalLayout);
    }
}