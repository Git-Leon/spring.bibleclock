package com.bibleclock.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class BibleVerse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String book;
    private String text;
    private String chapter;
    private String verse;

    public BibleVerse(String book, String text, String chapter, String verse) {
        this.book = book;
        this.text = text;
        this.chapter = chapter;
        this.verse = verse;
    }
}