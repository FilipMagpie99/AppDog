package com.example.demo;

import com.example.demo.models.User;
import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DemoApplicationTestsUserController {

    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    @BeforeAll
    public static void init() throws FileNotFoundException, SQLException {
        entityManagerFactory = Persistence.createEntityManagerFactory("testManager");
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterAll
    public static void afterAll() {
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    @Order(1)
    public void createUserTest() {
        User user = new User("userName", "imie", "nazwisko", "haslo", "mail", "111222333");
        User user2 = new User("userName2", "imie", "nazwisko", "haslo", "mail", "111222333");
        User user3 = new User("userName3", "imie", "nazwisko", "haslo", "mail", "111222333");

        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.persist(user2);
        entityManager.persist(user3);
        entityManager.getTransaction().commit();
        entityManager.refresh(user);

        List<User> userList = new ArrayList<>();
        userList.add(user); userList.add(user2); userList.add(user3);

        for (User userLoop : userList) {
            System.out.println("User - Nickname: " + userLoop.getUsername() + ", Imię: " + userLoop.getName() + ", Nazwisko: " + userLoop.getSurname() +
                    ", Hasło: " + userLoop.getPassword() + ", Email: " + userLoop.getEmail() + ", Numer telefonu: " + userLoop.getPhone_number() + "\n");
        }
    }

    @Test
    @Order(2)
    public void getUserTest() {
        TypedQuery<User> userTypedQuery = entityManager.createQuery("SELECT p FROM User p", User.class);
        String username = "userName2";
        List<User> resultList = userTypedQuery.getResultList();

        for (User userLoop : resultList) {
            if (userLoop.getUsername().contains(username)) {
                System.out.println("Result: " + username + " = " + userLoop.getUsername());
            }
        }
    }

    @Test
    @Order(3)
    public void updateUserTest() {
        TypedQuery<User> userTypedQuery = entityManager.createQuery("SELECT p FROM User p", User.class);
        String username = "userName2";
        List<User> resultList = userTypedQuery.getResultList();

        User user = new User();

        for (User userLoop : resultList) {
            if (userLoop.getUsername().contains(username)) {
                userLoop.setSurname("surnameUpdate");
                user = userLoop;
            }
        }
        System.out.println("Result: " + user.getSurname());
    }

    @Test
    @Order(4)
    public void deleteUserTest() {
        TypedQuery<User> userTypedQuery = entityManager.createQuery("SELECT p FROM User p", User.class);
        List<User> resultList = userTypedQuery.getResultList();

        entityManager.getTransaction().begin();
        resultList.forEach(action -> entityManager.remove(action));
        entityManager.getTransaction().commit();
    }

//    @Test
//    @Order(2)
//    public void createPostTest() {
//        Posting posting = new Posting("nazwa", "opis", "obrazek");
//
//        entityManager.getTransaction().begin();
//        entityManager.persist(posting);
//        entityManager.getTransaction().commit();
//        entityManager.refresh(posting);
//
//        System.out.println("Post - Name: " + posting.getName() + ", Description: " + posting.getDescription() + ", Obrazek: " + posting.getPosting_image());
//    }
}
