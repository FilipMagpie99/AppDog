package com.example.demo.service;

import com.example.demo.models.Posting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface PostingService {
    Page<Posting> searchByName(String name, Pageable pageable);
    Posting setPosting(Posting posting);
    void deletePosting(Integer postingId);
    List<Posting> getPostings();
    Optional<Posting> getPosting(Integer postingId);
}