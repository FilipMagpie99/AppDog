package com.example.demo.service;

import com.example.demo.models.Admin;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface AdminService {
    Optional<Admin> getAdmin(Long adminId);
    List<Admin> getAdmins();
    Admin setAdmin(Admin admin);
    void deleteAdmin(Long adminId);
}
