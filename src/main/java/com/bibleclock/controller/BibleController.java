package com.bibleclock.controller;

import com.bibleclock.model.BibleVerse;
import com.bibleclock.service.BibleService;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/book/bible")
@CrossOrigin(origins = "*")
public class BibleController {

    private final BibleService bibleService;

    public BibleController(BibleService bibleService) {
        this.bibleService = bibleService;
    }

    @GetMapping("/{timeString}")
    public BibleVerse getVerseForTime(@PathVariable String timeString) {
        return bibleService.getVerseForTime(timeString);
    }
}