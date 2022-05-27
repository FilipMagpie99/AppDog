package com.example.demo.models;

import javax.persistence.*;

@Entity
public class Posting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postingId;
    private String name;
    private String image;
    private String description;
    @ManyToOne
    @JoinColumn(name="userId")
    private User user = null;
    @ManyToOne
    @JoinColumn(name="shelterId")
    //null bo nie wiadomo na początku czy to normalny użytkownik czy shelter||trzeba sprawdzać kto dodaje posting przed dodaniem postingu
    private DogShelter shelter = null;

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

    public Integer getPostingId() {
        return postingId;
    }

    public void setPostingId(Integer postingId) {
        this.postingId = postingId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DogShelter getShelter() {
        return shelter;
    }

    public void setShelter(DogShelter shelter) {
        this.shelter = shelter;
    }
}
