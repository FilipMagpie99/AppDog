package com.example.demo.controller;

import com.example.demo.models.Posting;
import com.example.demo.models.User;
import com.example.demo.security.CurrentUserFinder;
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
@RequestMapping("/admin")
public class AdminController {
    private UserService userService;
    @Autowired
    CurrentUserFinder currentUserFinder;

    @Autowired
    public AdminController(UserService userService){
        this.userService = userService;
    }
    @Autowired
    PostingService postingService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @GetMapping
    public String adminHome(Model model){
        List<Posting> posting = postingService.getPostings();
        model.addAttribute("posting", posting);
        return "redirect:/admin/page/1?sortField=name&sortDir=asc";
    }
    @GetMapping("/search")
    public String admin(String keyword, Model model, Pageable pageable) {
        Long userId = currentUserFinder.getCurrentUserId();
        if (keyword == "") {
            return "redirect:/admin";
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
            return "sortedAdminPage";
        }
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
        return "sortedAdminPage";
    }


    @GetMapping("/deletePosting/{postingId}")
    public String deleteBook(Model model
            ,@PathVariable( value="postingId" ) Integer postingId){
        Optional<Posting> posting = postingService.getPosting(postingId);
        postingService.deletePosting(postingId);
        model.addAttribute("posting",posting);
        return "redirect:/admin";
    }

    @GetMapping("/updatePosting/{postingId}")
    public String updatePosting(Model model
            ,@PathVariable( value="postingId" ) Integer postingId){
        Optional<Posting> posting = postingService.getPosting(postingId);
        model.addAttribute("posting",posting);
        return "updatePosting.html";
    }

    @GetMapping("/admins")
    List<User> getUsers(){
        return userService.getUsers();
    }

    @PostMapping(path = "/admin")
    ResponseEntity<Void> createUser(@RequestBody User user){
        User createdUser= userService.setUser(user);
        URI location= ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{adminId}").buildAndExpand(createdUser.getUserId()).toUri();
        return ResponseEntity.created(location).build();
    }




    @GetMapping("/deleteUser/{userId}")
    public String deleteUser(Model model
            ,@PathVariable( value="userId" ) Long userId){
        User user = userService.getUser(userId);
        userService.deleteUser(userId);
        model.addAttribute("user",user);
        return "redirect:/admin";
    }
    @GetMapping("/updateUser/{userId}")
    public String updateUser(Model model
            ,@PathVariable( value="userId" ) Long userId){

        User user = userService.getUser(userId);
        model.addAttribute("user",user);
        return "updateuser";
    }
    @GetMapping("/users")
    public String usersTableView(Model model) {

            List<User> users = userService.getUsers();
            model.addAttribute("users",users);

        return "usersPageAdmin";
    }
    @PostMapping("/saveUser")
    public String saveBook(@ModelAttribute("user") User user,@RequestParam(value = "newpassword") String newpassword){
        if(!newpassword.isEmpty()) {
            user.setPassword(bCryptPasswordEncoder.encode(newpassword));
        }
        userService.setUser(user);
        return "redirect:/admin/users";
    }
}
