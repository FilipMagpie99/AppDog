package com.example.demo.service;

import com.example.demo.models.DogShelter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface DogShelterService {
    Optional<DogShelter> getShelter(Integer ShelterID);
    DogShelter setShelter(DogShelter dogShelter);
    void deleteShelter(Integer ShelterID);
    List<DogShelter> getShelters();
    List<DogShelter> searchByName(String name);
}
