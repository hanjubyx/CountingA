package com.example.countinga;

import java.util.HashSet;
import java.util.Set;

public class SentimentAnalyser {

    private static final Set<String> positiveWords = new HashSet<>();
    private static final Set<String> negativeWords = new HashSet<>();

    static {
        positiveWords.add("happy");
        positiveWords.add("joy");
        positiveWords.add("love");
        positiveWords.add("excellent");
        positiveWords.add("good");
        positiveWords.add("great");
        positiveWords.add("wonderful");
        positiveWords.add("amazing");
        positiveWords.add("positive");
        positiveWords.add("fantastic");
        positiveWords.add("beautiful");
        positiveWords.add("delightful");
        positiveWords.add("cheerful");
        positiveWords.add("bright");
        positiveWords.add("optimistic");
        positiveWords.add("superb");
        positiveWords.add("pleased");
        positiveWords.add("content");
        positiveWords.add("satisfied");
        positiveWords.add("grateful");
        positiveWords.add("glorious");
        positiveWords.add("successful");
        positiveWords.add("victorious");
        positiveWords.add("peaceful");
        positiveWords.add("enthusiastic");

        negativeWords.add("sad");
        negativeWords.add("bad");
        negativeWords.add("terrible");
        negativeWords.add("horrible");
        negativeWords.add("hate");
        negativeWords.add("angry");
        negativeWords.add("awful");
        negativeWords.add("negative");
        negativeWords.add("poor");
        negativeWords.add("depressed");
        negativeWords.add("miserable");
        negativeWords.add("unhappy");
        negativeWords.add("frustrated");
        negativeWords.add("disappointed");
        negativeWords.add("hopeless");
        negativeWords.add("upset");
        negativeWords.add("annoyed");
        negativeWords.add("hurt");
        negativeWords.add("defeated");
        negativeWords.add("failure");
        negativeWords.add("bitter");
        negativeWords.add("pessimistic");
        negativeWords.add("jealous");
        negativeWords.add("resentful");
        negativeWords.add("sorrow");
    }

    public int analyseSentiment(String content) {
        int positiveCount = 0;
        int negativeCount = 0;

        String[] words = content.split("\\W+");

        for (String word : words) {
            word = word.toLowerCase();
            if (positiveWords.contains(word)) {
                positiveCount++;
            } else if (negativeWords.contains(word)) {
                negativeCount++;
            }
        }

        if (positiveCount == 0 && negativeCount == 0) return 50;

        int totalSentimentWords = positiveCount + negativeCount;

        double sentimentRatio = (double) positiveCount / totalSentimentWords;

        int sentimentScore = (int) (sentimentRatio * 100);

        return Math.max(0, Math.min(100, sentimentScore));
    }
}