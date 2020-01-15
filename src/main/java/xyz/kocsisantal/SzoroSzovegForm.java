package xyz.kocsisantal;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class SzoroSzovegForm {
    public JPanel mainPanel;
    private JButton buttonGenerate;
    private JTextField textFieldName;
    private JTextField textFieldAddress;
    private JTextField textFieldDate;

    public SzoroSzovegForm() {
        buttonGenerate.addActionListener(actionEvent -> {
            String name = textFieldName.getText();
            String address = textFieldAddress.getText();
            String date = textFieldDate.getText();

            try {
                generatePdf(name, address, date);
            } catch (FileNotFoundException | DocumentException e) {
                e.printStackTrace();
            }
        });
    }

    private static void addMetaData(Document document, String name) {
        document.addTitle(name);
        // document.addSubject("Using iText");

        document.addAuthor("Kocsis Antal");
        document.addCreator("Kocsis Antal");
    }

    private void generatePdf(String name, String address, String date) throws FileNotFoundException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("target/" + name + ".pdf"));
        document.open();
        addMetaData(document, name);

        addLine(document, name);
        addLine(document, address);
        addLine(document, date);

        document.close();
    }

    private void addLine(Document document, String string) throws DocumentException {
        Paragraph nameParagraph = new Paragraph(string);
        nameParagraph.setAlignment(Element.ALIGN_CENTER);
        document.add(nameParagraph);
    }
}
