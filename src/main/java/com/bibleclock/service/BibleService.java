package com.bibleclock.service;

import com.bibleclock.model.BibleVerse;
import com.bibleclock.utils.Loggable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BibleService implements Loggable {

    public BibleVerse getVerseForTime(String timeString) {
        // Parse time string (format: "HH:mm")
        String[] timeParts = timeString.split(":");
        if (timeParts.length != 2) {
            throw new IllegalArgumentException("Invalid time format. Expected HH:mm");
        }

        int hours = Integer.parseInt(timeParts[0]);
        int minutes = Integer.parseInt(timeParts[1]);

        // Convert to 12-hour format for verse lookup
        int displayHours = hours % 12;
        if (displayHours == 0) displayHours = 12;

        // Create the key in format "chapter:verse"
        String chapter = String.valueOf(displayHours);
        String verse = String.format("%02d", minutes);

        // Try to find exact match
        for(BibleVerse bibleVerse : BibleVerseParserService.INSTANCE.getBibleBibleVerses()) {
            getLogger().info(String.format("Evalauting %s...", bibleVerse));
            boolean hasCorrectChapter = bibleVerse.getChapter().equals(chapter);
            boolean hasCorrectVerse = bibleVerse.getVerse().equals(verse);
            if(hasCorrectChapter && hasCorrectVerse) {
                return bibleVerse;
            }
        }

        // If no exact match, find the closest verse
        return findClosestVerse(chapter, verse);
    }

    private BibleVerse findClosestVerse(String targetChapter, String targetVerse) {
        int targetHours = Integer.parseInt(targetChapter);
        int targetMinutes = Integer.parseInt(targetVerse);
        int targetTotalMinutes = targetHours * 60 + targetMinutes;

        return BibleVerseParserService.INSTANCE.getBibleBibleVerses().stream()
            .filter(v -> {
                try {
                    int verseHours = Integer.parseInt(v.getChapter());
                    int verseMinutes = Integer.parseInt(v.getVerse());
                    int verseTotalMinutes = verseHours * 60 + verseMinutes;
                    return verseTotalMinutes <= targetTotalMinutes;
                } catch (NumberFormatException e) {
                    return false;
                }
            })
            .max((v1, v2) -> {
                int time1 = Integer.parseInt(v1.getChapter()) * 60 + Integer.parseInt(v1.getVerse());
                int time2 = Integer.parseInt(v2.getChapter()) * 60 + Integer.parseInt(v2.getVerse());
                return Integer.compare(time1, time2);
            })
            .orElseGet(() -> {
                // Default verse if none found
                return new BibleVerse("John", "For God so loved the world that he gave his one and only Son, that whoever believes in him shall not perish but have eternal life.", "3", "16");
            });
    }
}
