package com.example.demo.service;

import com.example.demo.models.Admin;
import com.example.demo.models.User;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    Optional<Admin> getAdmin(Long adminId);
    List<Admin> getAdmins();
    Admin setAdmin(Admin admin);
    void deleteAdmin(Long adminId);
}
