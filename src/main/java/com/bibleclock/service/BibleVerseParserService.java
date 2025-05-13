package com.bibleclock.service;

import com.bibleclock.model.BibleVerse;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public enum BibleVerseParserService {
    INSTANCE;

    private List<BibleVerse> bibleVerses;

    BibleVerseParserService() {
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

        this.bibleVerses = bibleVerses;
    }


    public List<BibleVerse> getBibleBibleVerses() {
        return this.bibleVerses;
    }
}
