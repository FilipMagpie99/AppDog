package com.example.demo.controller;

import com.example.demo.models.DogShelter;
import com.example.demo.service.DogShelterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DogShelterController {
    private DogShelterService dogShelterService;

    @Autowired
    public DogShelterController(DogShelterService dogShelterService) {
        this.dogShelterService = dogShelterService;
    }

    @GetMapping("/shelters/{shelterId}")
    ResponseEntity<DogShelter> getShelter(@PathVariable Integer shelterId){
        return ResponseEntity.of(dogShelterService.getShelter(shelterId));
    }

    @GetMapping("/shelters")
    List<DogShelter> getShelters(){
        return dogShelterService.getShelters();
    }
    @GetMapping(value = "/shelters", params = "name")
    List<DogShelter> getSheltersByName(@RequestParam String name){
        return dogShelterService.searchByName(name);
    }

    @PostMapping(path = "/shelters")
    ResponseEntity<Void> createShelter(@Valid @RequestBody DogShelter dogShelter){
        DogShelter createdShelter= dogShelterService.setShelter(dogShelter);
        URI location= ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{shelterId}").buildAndExpand(createdShelter.getShelterId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/shelters/{shelterId}")
    ResponseEntity<Void> updateShelter(@Valid @RequestBody DogShelter dogShelter,@PathVariable Integer shelterId){
        return dogShelterService.getShelter(shelterId)
                .map(p->{dogShelterService.setShelter(dogShelter);
                return new ResponseEntity<Void>(HttpStatus.OK);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/shelters/{shelterId}")
    ResponseEntity<Void> deleteShelter(@PathVariable Integer shelterId){
        return  dogShelterService.getShelter(shelterId).map(p->{
            dogShelterService.deleteShelter(shelterId);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }).orElseGet(()->ResponseEntity.notFound().build());
    }



}
