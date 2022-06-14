package com.example.demo.models;

import javax.persistence.*;

@Entity
public class Posting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postingId;
    private String name;
    private String description;
    private String posting_image = "no_image.jpg";
    @ManyToOne(targetEntity = User.class,fetch = FetchType.EAGER)
    @JoinColumn(name="userId")
    private User user = null;
    @ManyToOne(targetEntity = DogShelter.class,fetch = FetchType.EAGER)
    @JoinColumn(name="shelterId")
    private DogShelter dogShelter = null;
    private String animal;
    private String sex;

    public Posting(String name, String description, String posting_image) {
        this.name = name;
        this.description = description;
        this.posting_image = posting_image;
    }

    public String getPosting_image() {
        return posting_image;
    }

    public void setPosting_image(String posting_image) {
        this.posting_image = posting_image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Posting() {
    }

    public Posting(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Integer getPostingId() {
        return postingId;
    }

    public void setPostingId(Integer postingId) {
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
    public String getUserName(){
        return user.getName();
    }

    public DogShelter getDogShelter() {
        return dogShelter;
    }

    public void setDogShelter(DogShelter dogShelter) {
        this.dogShelter = dogShelter;
    }

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
