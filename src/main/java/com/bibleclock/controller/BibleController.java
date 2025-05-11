package com.bibleclock.controller;

import com.bibleclock.model.BibleVerse;
import com.bibleclock.service.BibleService;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/book/bible")
public class BibleController {

    private final BibleService bibleService;

    public BibleController(BibleService bibleService) {
        this.bibleService = bibleService;
    }

    @GetMapping("/{timeString}")
    public BibleVerse getVerseForTime(@PathVariable String timeString) {
        try {
            return bibleService.parseBibleBibleVerses().stream().findAny().get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }
}