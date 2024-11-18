package com.example.countinga;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.ComponentActivity;
import com.google.android.material.slider.Slider;
import java.io.File;
import java.util.List;
import java.util.Map;

public class MainActivity extends ComponentActivity {

    private TextView statisticsTextView;
    private TextView randomParagraphTextView;
    private TextView sentimentTextView;
    private Map<String, Integer> wordFrequency;
    private List<String> sentences;
    private List<String> uniqueWords;
    private String randomParagraph;
    private double temperature;
    private SentimentAnalyser sentimentAnalyser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statisticsTextView = findViewById(R.id.statisticsTextView);
        randomParagraphTextView = findViewById(R.id.randomParagraphTextView);
        sentimentTextView = findViewById(R.id.sentimentTextView);
        Slider temperatureSlider = findViewById(R.id.temperatureSlider);
        sentimentAnalyser = new SentimentAnalyser();

        temperatureSlider.addOnChangeListener((slider, value, fromUser) -> {
            temperature = value;
            Toast.makeText(this, "Temperature set to: " + temperature, Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.generateRandomParagraphButton).setOnClickListener(v -> {
            randomParagraph = RandomParagraphGenerator.generateRandomParagraph(wordFrequency, temperature);
            displayRandomParagraph(randomParagraph);
        });

        findViewById(R.id.savePdfButton).setOnClickListener(v -> {
            saveToPdf();
        });

        findViewById(R.id.importTextFileButton).setOnClickListener(v -> openFilePicker("text/plain"));
        findViewById(R.id.importPdfFileButton).setOnClickListener(v -> openFilePicker("application/pdf"));
    }

    private void openFilePicker(String fileType) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType(fileType);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                String mimeType = getContentResolver().getType(uri);
                String content = mimeType.equals("text/plain")
                        ? FileReaderHelper.readTextFileFromUri(this, uri)
                        : FileReaderHelper.readPdfFileFromUri(this, uri);

                processFileContent(content);
            }
        }
    }

    private void processFileContent(String content) {
        wordFrequency = StatisticsGenerator.generateWordFrequency(content);
        sentences = StatisticsGenerator.extractSentences(content);
        uniqueWords = StatisticsGenerator.getUniqueWords(wordFrequency);

        String statistics = StatisticsGenerator.generateStatisticsDisplay(wordFrequency, sentences);

        int sentimentScore = sentimentAnalyser.analyseSentiment(content);
        String sentimentText = "Sentiment Score: " + sentimentScore + " (0 = negative, 100 = positive)";

        statisticsTextView.setText(statistics);
        sentimentTextView.setText(sentimentText);

        collapseRandomParagraph();
    }

    private void displayRandomParagraph(String paragraph) {
        randomParagraphTextView.setText(paragraph);
        randomParagraphTextView.setVisibility(View.VISIBLE);
    }

    private void saveToPdf() {
        String statistics = statisticsTextView.getText().toString();

        String paragraphToExport = (randomParagraph != null && !randomParagraph.isEmpty())
                ? "Random Paragraph:\n" + randomParagraph + "\n\n"
                : "";

        String contentToExport = paragraphToExport + statistics;

        String filePath = PDFGenerator.saveAsPdf(this, contentToExport);
        if (filePath != null) {
            Toast.makeText(this, "PDF saved to: " + filePath, Toast.LENGTH_LONG).show();
            openPdf(filePath);
        } else {
            Toast.makeText(this, "Failed to save PDF", Toast.LENGTH_SHORT).show();
        }
    }

    private void openPdf(String filePath) {
        File pdfFile = new File(filePath);
        Uri pdfUri = Uri.fromFile(pdfFile);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(pdfUri, "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        try {
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "No PDF viewer found", Toast.LENGTH_SHORT).show();
        }
    }

    private void collapseRandomParagraph() {
        randomParagraphTextView.setVisibility(View.GONE);
    }
}