package com.example.demo.repository;

import com.example.demo.models.Comment;
import com.example.demo.models.Posting;
import com.example.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByOthers(User user);
}
