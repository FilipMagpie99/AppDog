package com.example.demo.controller;

import com.example.demo.models.DogShelter;
import com.example.demo.models.Posting;
import com.example.demo.service.DogShelterService;
import com.example.demo.service.PostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostingController {
    private PostingService postingService;

    @Autowired
    public PostingController(PostingService postingService) {
        this.postingService = postingService;
    }

    @GetMapping("/postings/{postingId}")
    ResponseEntity<Posting> getShelter(@PathVariable Integer postingId){
        return ResponseEntity.of(postingService.getPosting(postingId));
    }

    @GetMapping("/postings")
    List<Posting> getPostings(){
        return postingService.getPostings();
    }
    @GetMapping(value = "/postings", params = "name")
    Page<Posting> getSheltersByName(@RequestParam String name, Pageable pageable){
        return postingService.searchByName(name,pageable);
    }

    @PostMapping(path = "/postings")
    ResponseEntity<Void> createShelter(@RequestBody Posting posting){
        Posting createdPosting= postingService.setPosting(posting);
        URI location= ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{postingId}").buildAndExpand(createdPosting.getPostingId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/postings/{postingId}")
    ResponseEntity<Void> updateShelter(@RequestBody Posting posting,@PathVariable Integer postingId){
        return postingService.getPosting(postingId)
                .map(p->{postingService.setPosting(posting);
                    return new ResponseEntity<Void>(HttpStatus.OK);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/postings/{postingId}")
    ResponseEntity<Void> deleteShelter(@PathVariable Integer postingId){
        return  postingService.getPosting(postingId).map(p->{
            postingService.deletePosting(postingId);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }).orElseGet(()->ResponseEntity.notFound().build());
    }



}
