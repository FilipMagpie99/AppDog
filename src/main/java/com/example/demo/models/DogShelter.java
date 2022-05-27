package com.example.demo.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class DogShelter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shelterId;
    private String name;
    private String address;
    private String password;
    private String nip_number;
    private String phone_number;
    private String email;
    @OneToMany(mappedBy = "dogShelter",targetEntity = Posting.class, fetch = FetchType.EAGER)
    private List<Posting> shelterPostings;

    public DogShelter(String name, String address, String password, String nip_number, String phone_number, String email) {
        this.name = name;
        this.address = address;
        this.password = password;
        this.nip_number = nip_number;
        this.phone_number = phone_number;
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public DogShelter() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNip_number() {
        return nip_number;
    }

    public void setNip_number(String nip_number) {
        this.nip_number = nip_number;
    }

    public void setShelterId(Long id) {
        this.shelterId = id;
    }

    @Id
    public Long getShelterId() {
        return shelterId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setShelterPostings(List<Posting> shelterPostings) {
        this.shelterPostings = shelterPostings;
    }
}
