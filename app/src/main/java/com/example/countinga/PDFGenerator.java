package com.example.countinga;

import android.content.Context;
import android.util.Log;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.io.File;
import java.io.FileOutputStream;

public class PDFGenerator {

    public static String saveAsPdf(Context context, String content) {
        try {
            File externalFilesDir = context.getExternalFilesDir(null);
            if (externalFilesDir == null) return null;

            String filePath = externalFilesDir.getPath() + "/statistics_report.pdf";
            File file = new File(filePath);

            PdfWriter writer = new PdfWriter(new FileOutputStream(file));
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);
            document.add(new Paragraph(content));
            document.close();

            return filePath;
        } catch (Exception e) {
            Log.e("PDFGenerator", "Error generating PDF", e);
            return null;
        }
    }
}