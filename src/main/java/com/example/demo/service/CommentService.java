package com.example.demo.service;

import com.example.demo.models.Comment;
import com.example.demo.models.Posting;
import com.example.demo.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    List<Comment> GetComments();
    void addComment(Comment comment);
    Comment findById(Long commentId);
    void deleteComment(Comment comment);
    void deleteComments(List<Comment> comments);
    List<Comment> findByUser(User user);
    List<Comment> findByOwner(User user);
}
