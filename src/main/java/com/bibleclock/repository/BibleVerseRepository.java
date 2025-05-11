package com.bibleclock.repository;

import com.bibleclock.model.BibleVerse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BibleVerseRepository extends JpaRepository<BibleVerse, Long> {
    // Custom query methods can be added here if needed
} 