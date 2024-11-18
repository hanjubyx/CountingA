package com.example.countinga;

import java.util.*;

public class RandomParagraphGenerator {

    public static String generateRandomParagraph(Map<String, Integer> wordFrequency, double temperature) {
        List<String> words = new ArrayList<>(wordFrequency.keySet());
        Random random = new Random();
        int wordCount = (int) (temperature * 100);
        StringBuilder paragraph = new StringBuilder();
        for (int i = 0; i < wordCount; i++) {
            int index = (int) (Math.pow(random.nextDouble(), 1 / temperature) * words.size());
            paragraph.append(words.get(Math.min(words.size() - 1, index))).append(" ");
        }
        return paragraph.toString().trim();
    }
}