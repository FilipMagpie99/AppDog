package com.example.demo.service;

import com.example.demo.models.Posting;
import com.example.demo.models.User;
import com.example.demo.repository.PostingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostingServiceImpl implements PostingService {
    @Autowired
    private PostingRepository postingRepository;

    @Autowired
    public PostingServiceImpl(PostingRepository postingRepository) {
        this.postingRepository = postingRepository;
    }

    @Override
    public Page<Posting> searchByName(String name,Pageable pageable ) {
        return postingRepository.findByNameContainingIgnoreCase(name,pageable);
    }

    @Override
    public Posting setPosting(Posting posting) {
        return postingRepository.save(posting);
    }


    @Override
    public void deletePosting(Integer bookId){
        Posting posting = postingRepository.findById(bookId).get();
        postingRepository.delete(posting);
    }

    @Override
    public List<Posting> getPostings() {
        return postingRepository.findAll();
    }

    @Override
    public Optional<Posting> getPosting(Integer postingId) {
        return postingRepository.findById(postingId);
    }

    @Override
    public Page<Posting> getPostings(Pageable pageable) {
        return postingRepository.findAll(pageable);
    }

    @Override
    public Page<Posting> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.postingRepository.findAll(pageable);
    }

    @Override
    public Page<Posting> findPaginatedByName(int pageNo, int pageSize, String sortField, String sortDirection,String name) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.postingRepository.findByNameContainingIgnoreCase(name,pageable);
    }
    @Override
    public Page<Posting> searchByUser(int pageNo, int pageSize, String sortField, String sortDirection, Long userId) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.postingRepository.findPostingByUser_UserId(userId, pageable);
    }

    @Override
    public List<Posting> findByUser(User user) {
        return postingRepository.findByUser(user);
    }

    @Override
    public void deletePostings(List<Posting> postings) {
        postingRepository.deleteAll(postings);
    }


}
