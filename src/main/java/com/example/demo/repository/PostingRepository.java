package com.example.demo.repository;

import com.example.demo.models.Posting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostingRepository extends JpaRepository<Posting, Integer> {
    @Query("SELECT z FROM Posting z WHERE z.name LIKE '%name%'")
    Page<Posting> findByName(@Param("name") String nazwa, Pageable pageable);
}
