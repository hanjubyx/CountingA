package com.example.countinga;

import java.util.*;
import java.util.stream.Collectors;

public class StatisticsGenerator {

    public static Map<String, Integer> generateWordFrequency(String content) {
        Map<String, Integer> wordFrequency = new HashMap<>();
        String[] words = content.split("\\W+");
        for (String word : words) {
            word = word.toLowerCase();
            wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
        }
        return wordFrequency;
    }

    public static List<String> extractSentences(String content) {
        return Arrays.asList(content.split("[.!?]\\s*"));
    }

    public static String generateStatisticsDisplay(Map<String, Integer> wordFrequency, List<String> sentences) {
        int totalWords = wordFrequency.values().stream().mapToInt(Integer::intValue).sum();
        String topWords = wordFrequency.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(5)
                .map(e -> e.getKey() + ": " + e.getValue())
                .collect(Collectors.joining(", "));

        return String.format("Total Words: %d\nTotal Sentences: %d\nTop 5 Words: %s", totalWords, sentences.size(), topWords);
    }

    public static List<String> getUniqueWords(Map<String, Integer> wordFrequency) {
        return wordFrequency.keySet().stream().filter(word -> wordFrequency.get(word) == 1).collect(Collectors.toList());
    }
}