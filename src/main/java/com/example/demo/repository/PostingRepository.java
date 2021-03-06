package com.example.demo.repository;

import com.example.demo.models.Posting;
import com.example.demo.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostingRepository extends JpaRepository<Posting, Integer> {
    Page<Posting> findByNameContainingIgnoreCase( String nazwa,Pageable pageable);
    Page<Posting> findPostingByUser_UserId(Long userId,Pageable pageable);
    List<Posting> findByUser(User user);
}
