package com.example.demo.repository;

import com.example.demo.models.DogShelter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface DogShelterRepository extends JpaRepository<DogShelter,Integer> {
    List<DogShelter> findByNameContainingIgnoreCase(String name);
}
