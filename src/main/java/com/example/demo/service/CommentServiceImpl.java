package com.example.demo.service;

import com.example.demo.models.Comment;
import com.example.demo.models.Posting;
import com.example.demo.models.User;
import com.example.demo.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepo;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepo){ this.commentRepo = commentRepo; }

    @Override
    public List<Comment> GetComments(){
        return commentRepo.findAll();
    }
    @Override
    public void addComment(Comment comment){
        commentRepo.save(comment);
    }
    @Override
    public Comment findById(Long commentId){
        Comment comment = commentRepo.findById(commentId).get();
        return comment;
    }
    @Override
    public void deleteComment(Comment comment){
        commentRepo.delete(comment);
    }
    @Override
    public void deleteComments(List<Comment> comments){
        commentRepo.deleteAll(comments);
    }
    @Override
    public List<Comment> findByUser(User user){
        return commentRepo.findByOthers(user);
    }
}
