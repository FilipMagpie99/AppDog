package com.example.demo.service;

import com.example.demo.models.Posting;
import com.example.demo.repository.PostingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostingServiceImpl implements PostingService{
    @Autowired
    private PostingRepository postingRepository;

    @Autowired
    public PostingServiceImpl(PostingRepository postingRepository) {
        this.postingRepository = postingRepository;
    }

    @Override
    public Page<Posting> searchByName(String name, Pageable pageable) {
        return postingRepository.findByName(name, pageable);
    }

    @Override
    public Posting setPosting(Posting posting) {
        return postingRepository.save(posting);
    }

    @Override
    public void deletePosting(Integer postingId) {
        postingRepository.deleteById(postingId);
    }

    @Override
    public List<Posting> getPostings() {
        return postingRepository.findAll();
    }

    @Override
    public Optional<Posting> getPosting(Integer postingId) {
        return postingRepository.findById(postingId);
    }
}
