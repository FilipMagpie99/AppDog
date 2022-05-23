package com.example.demo.service;

import com.example.demo.models.DogShelter;
import com.example.demo.repository.DogShelterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DogShelterServiceImpl implements DogShelterService {

    private DogShelterRepository dogShelterRepository;

    @Autowired
    public DogShelterServiceImpl(DogShelterRepository dogShelterRepository) {
        this.dogShelterRepository = dogShelterRepository;
    }

    @Override
    public Optional<DogShelter> getShelter(Integer shelterID) {
        return dogShelterRepository.findById(shelterID);

    }

    @Override
    public DogShelter setShelter(DogShelter dogShelter) {
        return dogShelterRepository.save(dogShelter);
    }

    @Override
    public void deleteShelter(Integer shelterID) {
        dogShelterRepository.deleteById(shelterID);
    }

    @Override
    public List<DogShelter> getShelters() {
        return dogShelterRepository.findAll();
    }

    @Override
    public List<DogShelter> searchByName(String name) {
        return dogShelterRepository.findByNameContainingIgnoreCase(name);
    }
}
