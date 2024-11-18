package com.example.countinga;
import android.content.Context;
import android.net.Uri;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileReaderHelper {

    public static String readTextFileFromUri(Context context, Uri uri) {
        StringBuilder content = new StringBuilder();
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    public static String readPdfFileFromUri(Context context, Uri uri) {
        StringBuilder content = new StringBuilder();
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            PdfReader reader = new PdfReader(inputStream);
            PdfDocument pdfDocument = new PdfDocument(reader);
            for (int i = 1; i <= pdfDocument.getNumberOfPages(); i++) {
                content.append(PdfTextExtractor.getTextFromPage(pdfDocument.getPage(i)));
            }
            pdfDocument.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}