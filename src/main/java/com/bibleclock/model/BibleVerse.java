package com.bibleclock.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;

@Entity
public class BibleVerse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String book;

    @Column(length = 1000) // if you're using VARCHAR(1000)
    private String text;
    private String chapter;
    private String verse;

    public BibleVerse(String book, String text, String chapter, String verse) {
        this.book = book;
        this.text = text;
        this.chapter = chapter;
        this.verse = verse;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getVerse() {
        return verse;
    }

    public void setVerse(String verse) {
        this.verse = verse;
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return "BibleVerse{" + "id=" + id + ", book='" + book + '\'' + ", text='" + text + '\'' + ", chapter='" + chapter + '\'' + ", verse='" + verse + '\'' + '}';
        }
    }
}