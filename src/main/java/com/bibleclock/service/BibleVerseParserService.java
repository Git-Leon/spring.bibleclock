package com.bibleclock.service;

import com.bibleclock.model.BibleVerse;
import com.bibleclock.repository.BibleVerseRepository;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class BibleVerseParserService {
    @Autowired
    private BibleVerseRepository repository;

    @PostConstruct
    public void setup() {
        String[] line;
        final List<BibleVerse> bibleVerses = new ArrayList<>();
        final InputStream inputStream;
        try {
            inputStream = new ClassPathResource("data.csv").getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        final InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        final CSVParser csvParser = new CSVParserBuilder().withSeparator('\t').build();
        final CSVReader csvReader = new CSVReaderBuilder(reader).withCSVParser(csvParser).build();
        while (true) {
            try {
                if ((line = csvReader.readNext()) == null) {
                    break;
                }
            } catch (IOException | CsvValidationException e) {
                throw new RuntimeException(e);
            }
            if (line.length < 4) {
                continue; // skip malformed lines
            }
            final String book = line[0];
            final String chapter = line[1];
            final String verse = line[2];
            final String text = line[3];
            final BibleVerse bibleVerse = new BibleVerse(book, text, chapter, verse);
            bibleVerses.add(bibleVerse);
        }

        repository.saveAll(bibleVerses);
    }


    public List<BibleVerse> getBibleBibleVerses() {
        final Iterable<BibleVerse> bibleVerseIterable = repository.findAll();
        final List<BibleVerse> bibleVerseList = new ArrayList<>();
        bibleVerseIterable.forEach(bibleVerseList::add);
        return bibleVerseList;
    }
}
