package com.bibleclock.repository;

import com.bibleclock.model.BibleVerse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BibleVerseRepository extends CrudRepository<BibleVerse, Long> {
}
