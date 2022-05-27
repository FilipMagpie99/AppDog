package com.example.demo.models;

import net.bytebuddy.dynamic.loading.InjectionClassLoader;
import org.hibernate.boot.model.naming.Identifier;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String name;
    private String surname;
    private String password;
    private String email;
    private String phone_number;
    @OneToMany(mappedBy = "user")
    private List<Posting> userPostings;



    public User(String name, String surname, String password, String email, String phone_number) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email = email;
        this.phone_number = phone_number;
    }

    public Long getUserId() {
        return userId;
    }
    public User(){

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

    public List<Posting> getUserPostings() {
        return userPostings;
    }

    public void setUserPostings(List<Posting> userPostings) {
        this.userPostings = userPostings;
    }
}
