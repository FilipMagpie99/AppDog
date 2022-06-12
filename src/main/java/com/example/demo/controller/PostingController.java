package com.example.demo.controller;

import com.example.demo.models.Posting;
import com.example.demo.service.PostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/home")
public class PostingController {
    private PostingService postingService;

    @Autowired
    public PostingController(PostingService postingService) {
        this.postingService = postingService;
    }


    @GetMapping()
    public String homePage(Model model) {
        List<Posting> postings = postingService.getPostings();
        model.addAttribute("postings", postings);

        return "homePage2";
    }
    @GetMapping("/postings/{postingId}")
    public String postingPage(@PathVariable Integer postingId, Model model) {
        Optional<Posting> currentPosting = postingService.getPosting(postingId);
        currentPosting.ifPresent(posting -> model.addAttribute("posting",posting));
        return "postingPage";
    }

//    @GetMapping("/postings/{postingName}")
//    public String postingSearchByName(@PathVariable String postingName, Model model,Pageable pageable) {
//       if(postingName!=null) {
//           Page<Posting> postings = postingService.searchByName(postingName, pageable);
//           model.addAttribute("posting", postings);
//       }
//       else return "redirect:/home";
//
//       return "homePage";
//    }

    @GetMapping("/postings")
    List<Posting> getPostings() {
        return postingService.getPostings();
    }

    @GetMapping(value = "/postings", params = "name")
    Page<Posting> getSheltersByName(@RequestParam String name, Pageable pageable) {
        return postingService.searchByName(name, pageable);
    }

    @PostMapping(path = "/postings")
    ResponseEntity<Void> createShelter(@RequestBody Posting posting) {
        Posting createdPosting = postingService.setPosting(posting);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{postingId}").buildAndExpand(createdPosting.getPostingId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/postings/{postingId}")
    ResponseEntity<Void> updateShelter(@RequestBody Posting posting, @PathVariable Integer postingId) {
        return postingService.getPosting(postingId)
                .map(p -> {
                    postingService.setPosting(posting);
                    return new ResponseEntity<Void>(HttpStatus.OK);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/postings/{postingId}")
    ResponseEntity<Void> deleteShelter(@PathVariable Integer postingId) {
        return postingService.getPosting(postingId).map(p -> {
            postingService.deletePosting(postingId);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }


}
