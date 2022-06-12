package com.example.demo.models;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String username;
    private String name;
    private String surname;
    private String password;
    private String email;
    private boolean enabled = true;
    private String phone_number;
    private String profile_picture = "default_profile.jpg";
    private String role = "ROLE_USER";
    @OneToMany(mappedBy = "user",targetEntity = Posting.class,fetch = FetchType.EAGER)
    private List<Posting> userPostings;



    public User(String username, String name, String surname, String password, String email, String phone_number) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email = email;
        this.phone_number = phone_number;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Long getUserId() {
        return userId;
    }
    public User(){
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserPostings(List<Posting> userPostings) {
        this.userPostings = userPostings;
    }

    public List<Posting> getUserPostings() {
        return userPostings;
    }

    @Override
    public String toString() {
        return name+" "+surname;
    }
}
