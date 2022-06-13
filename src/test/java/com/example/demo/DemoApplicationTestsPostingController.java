package com.example.demo;

import com.example.demo.models.Posting;
import com.example.demo.models.User;
import org.junit.jupiter.api.*;

import javax.persistence.*;
import javax.transaction.Transaction;
import java.awt.*;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DemoApplicationTestsPostingController {

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
    public void createPostTest() {
        Posting posting = new Posting("imie", "opis", "zdjecie");
        Posting posting2 = new Posting("imie2", "opis", "zdjecie");
        Posting posting3 = new Posting("imie3", "opis", "zdjecie");

        entityManager.getTransaction().begin();
        entityManager.persist(posting);
        entityManager.persist(posting2);
        entityManager.persist(posting3);
        entityManager.getTransaction().commit();
        entityManager.refresh(posting);
        entityManager.refresh(posting2);
        entityManager.refresh(posting3);

        List<Posting> postingList = new ArrayList<>();
        postingList.add(posting); postingList.add(posting2); postingList.add(posting3);

        for (Posting postingLoop : postingList) {
            System.out.println("Post - Name: " + postingLoop.getName() + ", Description: " + postingLoop.getDescription()
                    + ", Posting image: " + postingLoop.getPosting_image());
        }
    }

    @Test
    @Order(2)
    public void getPostTest() {
        TypedQuery<Posting> postingTypedQuery = entityManager.createQuery("SELECT p FROM Posting p", Posting.class);
        String name = "imie3";
        List<Posting> resultList = postingTypedQuery.getResultList();

        for (Posting postingLoop : resultList) {
            if (postingLoop.getName().contains(name)) {
                System.out.println("Result: " + name + " = " + postingLoop.getName());
            }
        }
    }

    @Test
    @Order(3)
    public void updatePostTest() {
        TypedQuery<Posting> postingTypedQuery = entityManager.createQuery("SELECT p FROM Posting p", Posting.class);
        String nameOg = "imie3";
        String updatedName = "nameUpdate";
        List<Posting> resultList = postingTypedQuery.getResultList();

        entityManager.getTransaction().begin();

        for (Posting postingLoop : resultList) {
            if (postingLoop.getName().contains(nameOg)) {
                postingLoop.setName(updatedName);
                entityManager.getTransaction().commit();
            }
        }

        TypedQuery<Posting> postingTypedQuery2 = entityManager.createQuery("SELECT p FROM Posting p", Posting.class);
        List<Posting> resultList2 = postingTypedQuery2.getResultList();

        for (Posting postingLoop : resultList2) {
            if (postingLoop.getName().contains(updatedName)) {
                System.out.println("Result: " + postingLoop.getName());
            }
        }
    }

    @Test
    @Order(4)
    public void deletePostTest() {
        TypedQuery<Posting> postingTypedQuery = entityManager.createQuery("SELECT p FROM Posting p", Posting.class);
        List<Posting> resultList = postingTypedQuery.getResultList();

        entityManager.getTransaction().begin();
        resultList.forEach(action -> entityManager.remove(action));
        entityManager.getTransaction().commit();
    }
}
