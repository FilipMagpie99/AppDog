package com.example.demo.controller;

import com.example.demo.models.Posting;
import com.example.demo.security.CurrentUserFinder;
import com.example.demo.service.PostingService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/home")
public class PostingController {
    private PostingService postingService;
    private CurrentUserFinder currentUserFinder;

    @Autowired
    public PostingController(PostingService postingService, CurrentUserFinder currentUserFinder) {
        this.postingService = postingService;
        this.currentUserFinder = currentUserFinder;
    }


    @GetMapping()
    public String homePage(Model model) {
        List<Posting> postings = postingService.getPostings();
        model.addAttribute("postings", postings);

        return "homePage2";
    }

    @GetMapping("/postings/{postingId}")
    public String postingPage(@PathVariable Integer postingId, Model model) {
        model.addAttribute("posting", postingService.getPosting(postingId).get());
        model.addAttribute("userPst", postingService.getPosting(postingId).get().getUser());
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
    Page<Posting> getSheltersByName(@RequestParam String name,Pageable pageable) {
        return postingService.searchByName(name,pageable);
    }

    @PostMapping(path = "/postings")
    String createShelter(Posting posting,@RequestParam(value="image") MultipartFile file) throws IOException {

        posting.setUser(currentUserFinder.getCurrentUser());
        String fileName = file.getOriginalFilename();
        if(!file.isEmpty()){
            String path = System.getProperty("user.dir")+"/uploads"+"/"+fileName;
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(path)));
            stream.write(file.getBytes());
            stream.close();
            posting.setPosting_image(fileName);
        }
        Posting createdPosting = postingService.setPosting(posting);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{postingId}").buildAndExpand(createdPosting.getPostingId()).toUri();
        return "redirect:/";
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

    @GetMapping("/addPosting")
    public String addPosting(Model model){
        model.addAttribute("newPosting", new Posting());
        return "postingAddPage.html";
    }

    @GetMapping("/postingsfilter")
    public String searchByFilters(Model model, @RequestParam(value= "animal",required = false) String animal, @RequestParam(value= "sex",required = false) String sex)
                                  {
        List<Posting> postings = postingService.getPostings().stream().filter(x -> x.getSex().equals(sex) && x.getAnimal().equals(animal)).collect(Collectors.toList());

        model.addAttribute("posting", postings);
        return "sortedHomePage";
    }



}
