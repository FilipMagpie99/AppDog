package com.example.demo.gui;

import com.example.demo.models.Posting;
import com.example.demo.service.AdminService;
import com.example.demo.service.DogShelterService;
import com.example.demo.service.PostingService;
import com.example.demo.service.UserService;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Route("/home")
public class MainPage extends VerticalLayout {

    @Autowired
    private PostingService postingService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;
    @Autowired
    private DogShelterService dogShelterService;

    private Grid<Posting> postingGrid = new Grid<>(Posting.class, false);

    public static Optional<Posting> optionalPosting;

    @PostConstruct
    public void init() {
        //Label title = new Label("App dog");
        // Tytuł strony
        H2 title = new H2("App dog");

        TextField searchField = new TextField();
        searchField.setPlaceholder("Szukaj...");
        searchField.setClearButtonVisible(true);
        searchField.addValueChangeListener(this::onNameFilterTextChange);

        postingGrid.addColumn(Posting::getImage).setHeader("Obraz").setAutoWidth(true);
        postingGrid.addColumn(Posting::getName).setHeader("Nazwa").setAutoWidth(true);
        postingGrid.addColumn(Posting::getDescription).setHeader("Opis").setAutoWidth(true);
        postingGrid.addColumn(Posting::getUser).setHeader("Użytkownik").setAutoWidth(true);

//        postingGrid.addComponentColumn(e -> {
//            Button detailsButton = new Button("Szczegóły", p -> {
//                optionalPosting = p.get
//                UI.getCurrent().navigate(PostingPage.class);
//            });
//            return detailsButton;
//        }).setHeader("Szczegóły").setAutoWidth(true);

        List<Posting> postingList = postingService.getPostings();

        postingGrid.setItems(postingList);

        postingGrid.addSelectionListener(selectionEvent -> {
            optionalPosting = selectionEvent.getFirstSelectedItem();
            UI.getCurrent().navigate(PostingPage.class);
//            Notification not = Notification.show(String.valueOf(optionalPosting));
//            not.setDuration(9000);
//            not.setPosition(Notification.Position.MIDDLE);

        });

        HorizontalLayout titleHorizontalLayout = new HorizontalLayout(title);
        titleHorizontalLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        titleHorizontalLayout.setWidth("100%");

        HorizontalLayout searchHorizontalLayout = new HorizontalLayout(searchField);
        searchHorizontalLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        searchHorizontalLayout.setWidth("100%");

        HorizontalLayout postingHorizontalLayout = new HorizontalLayout(postingGrid);
        postingHorizontalLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        postingHorizontalLayout.setWidth("100%");

        setSizeFull();
        setMargin(false);
        setSpacing(false);
        setPadding(false);
        add(titleHorizontalLayout, searchHorizontalLayout, postingHorizontalLayout);
    }

    private void onNameFilterTextChange(HasValue.ValueChangeEvent<String> event) {
        ListDataProvider<Posting> postingListDataProvider = (ListDataProvider<Posting>) postingGrid.getDataProvider();
        postingListDataProvider.setFilter(Posting::getName, s -> caseIntensitiveContains(s, event.getValue()));
    }

    private Boolean caseIntensitiveContains(String where, String what) {
        return where.toLowerCase().contains(what.toLowerCase());
    }
}