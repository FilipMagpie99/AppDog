package com.example.demo.gui;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import javax.annotation.PostConstruct;

@Route("/home/post")
public class PostingPage extends VerticalLayout {

    @PostConstruct
    public void init() {

        H2 title = new H2(MainPage.optionalPosting.get().getName());
        HorizontalLayout titleHorizontalLayout = new HorizontalLayout(title);
        titleHorizontalLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        titleHorizontalLayout.setWidth("100%");

        H3 description = new H3("Opis: " + MainPage.optionalPosting.get().getDescription());
        HorizontalLayout descriptionHorizontalLayout = new HorizontalLayout(description);
        descriptionHorizontalLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        descriptionHorizontalLayout.setWidth("100%");

        Label user = new Label("Sprzedawca: " + MainPage.optionalPosting.get().getUser().getName());
        HorizontalLayout userHorizontalLayout = new HorizontalLayout(user);
        userHorizontalLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        userHorizontalLayout.setWidth("100%");

        setSizeFull();
        setMargin(false);
        setSpacing(false);
        setPadding(false);
        add(titleHorizontalLayout, descriptionHorizontalLayout, userHorizontalLayout);
    }
}