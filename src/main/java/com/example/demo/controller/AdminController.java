package com.example.demo.controller;

import com.example.demo.models.Admin;
import com.example.demo.service.AdminService;
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
public class AdminController {
    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService){
        this.adminService = adminService;
    }

    @GetMapping("/admin/{adminId}")
    ResponseEntity<Admin> getAdmin(@PathVariable Long adminId){
        return ResponseEntity.of(adminService.getAdmin(adminId));
    }

    @GetMapping("/admins")
    List<Admin> getAdmin(){
        return adminService.getAdmins();
    }

    @PostMapping(path = "/admin")
    ResponseEntity<Void> createAdmin(@Valid @RequestBody Admin admin){
        Admin createdAdmin= adminService.setAdmin(admin);
        URI location= ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{adminId}").buildAndExpand(createdAdmin.getAdminId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/admin/{adminId}")
    ResponseEntity<Void> updateAdmin(@Valid @RequestBody Admin admin,@PathVariable Long adminId){
        return adminService.getAdmin(adminId)
                .map(p->{adminService.setAdmin(admin);
                    return new ResponseEntity<Void>(HttpStatus.OK);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/admin/{adminId}")
    ResponseEntity<Void> deleteAdmin(@PathVariable Long adminId){
        return  adminService.getAdmin(adminId).map(p->{
            adminService.deleteAdmin(adminId);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }).orElseGet(()->ResponseEntity.notFound().build());
    }
}
