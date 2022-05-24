package com.example.demo.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    private Long userId;
    private String name;
    private String surname;
    private String password;
    private String email;
    private Integer phone_number;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String name, String surname, String password, String email, Integer phone_number) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email = email;
        this.phone_number = phone_number;
    }

    public Long getUserId() {
        return userId;
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

    public Integer getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(Integer phone_number) {
        this.phone_number = phone_number;
    }
}
