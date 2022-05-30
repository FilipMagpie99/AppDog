package com.example.demo.models;

import javax.persistence.*;

@Entity
public class Posting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postingId;
    private String name;
    private String image;
    private String description;
    @ManyToOne(targetEntity = User.class,fetch = FetchType.EAGER)
    @JoinColumn(name="userId")
    private User user = null;
    @ManyToOne(targetEntity = DogShelter.class,fetch = FetchType.EAGER)
    @JoinColumn(name="shelterId")
    private DogShelter dogShelter = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Posting() {
    }

    public Posting(String name, String image, String description) {
        this.name = name;
        this.image = image;
        this.description = description;
    }

    public Long getPostingId() {
        return postingId;
    }

    public void setPostingId(Long postingId) {
        this.postingId = postingId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setShelter(DogShelter dogShelter) {
        this.dogShelter = dogShelter;
    }

    public User getUser() {
        return user;
    }

    public DogShelter getDogShelter() {
        return dogShelter;
    }

    public void setDogShelter(DogShelter dogShelter) {
        this.dogShelter = dogShelter;
    }
}
