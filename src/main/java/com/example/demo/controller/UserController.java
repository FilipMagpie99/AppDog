package com.example.demo.controller;

import com.example.demo.models.Comment;
import com.example.demo.models.DogShelter;
import com.example.demo.models.Posting;
import com.example.demo.models.User;
import com.example.demo.security.CurrentUserFinder;
import com.example.demo.service.CommentService;
import com.example.demo.service.DogShelterService;
import com.example.demo.service.PostingService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    @Autowired
    CurrentUserFinder currentUserFinder;

    @Autowired
    PostingService postingService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    CommentService commentService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/search")
    public String userHome1(String keyword, Model model, Pageable pageable) {
        Long userId = currentUserFinder.getCurrentUserId();
        if (keyword == "") {
            return "redirect:/user";
        } else {

            int pageSize = 5;
            Page<Posting> page = postingService.findPaginatedByName(1, pageSize, "name", "asc", keyword);
            List<Posting> posting = page.getContent();
            model.addAttribute("currentPage", 1);
            model.addAttribute("totalPages", page.getTotalPages());
            model.addAttribute("totalItems", page.getTotalElements());
            model.addAttribute("sortField", "name");
            model.addAttribute("sortDir", "asc");
            model.addAttribute("reverseSortDir", "desc");
            model.addAttribute("posting", posting);
            model.addAttribute("userId", userId);
            return "sortedHomePage";
        }
    }

    @GetMapping
    public String userHome(Model model) {
        return "redirect:/user/page/1?sortField=name&sortDir=asc";
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 5;
        Page<Posting> page = postingService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Posting> posting = page.getContent();
        String keyword = " ";
        Long userId = currentUserFinder.getCurrentUserId();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("posting", posting);
        model.addAttribute("userId", userId);
        return "sortedHomePage";
    }

    @GetMapping("/userProfile")
    public String userProfile(Model model) {
        return "redirect:/user/userProfile/page/1?sortField=name&sortDir=asc";
    }

    @GetMapping("/profiles/{id}")
    public String profile(@PathVariable(value="id") Long id, Model model) {
        return "redirect:/user/profiles/page/"+id+"/1?sortField=name&sortDir=asc";
    }

    @GetMapping("/userProfile/page/{pageNo}")
    public String userProfilePaginated(@PathVariable(value = "pageNo") int pageNo,
                                       @RequestParam("sortField") String sortField,
                                       @RequestParam("sortDir") String sortDir,
                                       Model model) {
        User currentUser = currentUserFinder.getCurrentUser();

        int pageSize = 5;

        String keyword = " ";
        Page<Posting> page = postingService.searchByUser(pageNo, pageSize, sortField, sortDir, currentUser.getUserId());
        List<Posting> posting = page.getContent();
        model.addAttribute("currUser", currentUser);
        model.addAttribute("userPostings",posting);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("userPostings", currentUser.getUserPostings());
        List<Comment> comments = commentService.findByUser(currentUser);
        int numberComments = comments.size();
        model.addAttribute("numberOfComments",numberComments);
        model.addAttribute("comments",comments);
        model.addAttribute("userRole", currentUser.getRole());

        return "userProfilePaginated.html";
    }

    @GetMapping("/profiles/page/{id}/{pageNo}")
    public String getUser(@PathVariable(value="id") Long id, @PathVariable(value = "pageNo") int pageNo,
                          @RequestParam("sortField") String sortField,
                          @RequestParam("sortDir") String sortDir,Model model){
        User currentUser = currentUserFinder.getCurrentUser();
        User profile = userService.getUser(id);
        /////////////////////////////////////////////////////////////////////
        int pageSize = 5;

        String keyword = " ";
        Page<Posting> page = postingService.searchByUser(pageNo, pageSize, sortField, sortDir, profile.getUserId());
        List<Posting> posting = page.getContent();
        model.addAttribute("userPostings",posting);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        ////////////////////////////////////////////////////////////////////

        model.addAttribute("userProfile", currentUser);
        model.addAttribute("currUser", profile);
        model.addAttribute("userPostings", profile.getUserPostings());

        List<Comment> comments = commentService.findByUser(profile);
        Comment currUserComment = null;
        for (Comment com : comments){
            if(com.getUser().getUserId().equals(currentUser.getUserId())){
                currUserComment = com;
                break;
            }
        }
        int numberComments = comments.size();
        model.addAttribute("numberOfComments",numberComments);
        model.addAttribute("userComment",currUserComment);
        model.addAttribute("comments",comments);
        model.addAttribute("userRole", profile.getRole());
        model.addAttribute("newComment", new Comment());
        //model.addAttribute("log","user");

        return "profile.html";
    }

    @PostMapping(value="/comment/{userId}")
    public String addComment(@PathVariable(value="userId") Long userId, @ModelAttribute("newComment") Comment comment){
        User userRated = userService.getUser(userId);
        User commentOwner = currentUserFinder.getCurrentUser();
        comment.setOthers(userRated);
        comment.setUser(commentOwner);
        commentService.addComment(comment);
        float newUserRating = 0;
        float sum = 0;

        float numberOfComments = 0;
        List<Comment> comments = commentService.findByUser(userRated);
        for (Comment com: comments)
        {
            sum+=com.getRate();
            numberOfComments++;
        }
        newUserRating = sum/numberOfComments;
        userRated.setRating_score(newUserRating);
        userService.setUser(userRated);

        return "redirect:/user/profiles/" + userId;
    }

    @GetMapping("/delete/{id}")
    public String deleteComment(@PathVariable("id") long id, Model model){
        Comment comment = commentService.findById(id);
        commentService.deleteComment(comment);
        User ratedUser = userService.getUser(comment.getOthers().getUserId());
        float newUserRating = 0;
        float sum = 0;
        float numberOfComments = 0;
        List<Comment> comments = commentService.findByUser(ratedUser);
        for (Comment com: comments)
        {
            sum+=com.getRate();
            numberOfComments++;
        }
        if(numberOfComments != 0){
            newUserRating = sum/numberOfComments;
            ratedUser.setRating_score(newUserRating);
            userService.setUser(ratedUser);
        }else{
            ratedUser.setRating_score(0);
            userService.setUser(ratedUser);
        }
        return "redirect:/user/profiles/" + comment.getOthers().getUserId();
    }

    @GetMapping("/changePassword")
    public String changePassword(Model model){
        Long userId = currentUserFinder.getCurrentUserId();
        User user = userService.getUser(userId);
        model.addAttribute("user",user);
        return "changepassword.html";
    }
    @PostMapping("/saveUserPassword")
    public String saveUserPassword(@ModelAttribute("user") User user,
                                   @RequestParam(value = "oldpassword") String oldpassword,
                                   @RequestParam(value = "newpassword") String newpassword){
        if(bCryptPasswordEncoder.matches(oldpassword,user.getPassword()) && oldpassword!=null && newpassword!=null) {
            user.setPassword(bCryptPasswordEncoder.encode(newpassword));
            userService.setUser(user);
        }
        return "redirect:/";
    }
}
