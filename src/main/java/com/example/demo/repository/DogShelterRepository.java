package com.example.demo.repository;

import com.example.demo.models.DogShelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DogShelterRepository extends JpaRepository<DogShelter,Integer> {
    List<DogShelter> findByNameContainingIgnoreCase(String name);
}
