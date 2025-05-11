package com.bibleclock.controller;

import com.bibleclock.model.BibleVerse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/book/bible")
public class BibleController {

    @GetMapping("/{timeString}")
    public BibleVerse getVerseForTime(@PathVariable String timeString) {
        // TODO: Implement actual verse lookup logic
        // For now, return a sample verse
        final BibleVerse verse = new BibleVerse();
        verse.setBook("John");
        verse.setChapter("3");
        verse.setVerse("16");
        verse.setText("For God so loved the world that he gave his one and only Son, that whoever believes in him shall not perish but have eternal life.");
        return verse;
    }
}