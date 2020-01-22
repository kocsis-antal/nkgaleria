package xyz.kocsisantal.pdf;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

public class Generator {
    public static final float SIDE_MARGIN = 20;
    public static final float WIDTH = 420;
    public static final String HELYE_STRING = "Helye: ";
    private static final float BOX_WIDTH = 1134;
    private final String name;
    private final String address;
    private final String date;

    public Generator(final String name, final String address, final String date) {
        this.name = name.toUpperCase();
        this.address = address;
        this.date = date;
    }

    public void generate(final File file) throws IOException {
        // create document
        try (final PDDocument document = new PDDocument()) {
            final PDType0Font fontNormal = PDType0Font.load(document, new File("c:/windows/fonts/arial.ttf"));
            final PDType0Font fontBold = PDType0Font.load(document, new File("c:/windows/fonts/arialbd.ttf"));

            // create page
            final PDPage currentPage = new PDPage(new PDRectangle(WIDTH, 1134));
            document.addPage(currentPage);

            // start the page stream
            try (final PDPageContentStream contentStream = new PDPageContentStream(document, currentPage)) {

                contentStream.beginText();
                final float linePointer = 115;

                // name
                final float nameFontSize = calculateSize(HELYE_STRING + name, fontBold, 15);

                contentStream.newLineAtOffset(WIDTH / 2 - calculateTextWidth(HELYE_STRING, fontNormal, nameFontSize) / 2 - calculateTextWidth(name, fontBold, nameFontSize) / 2, linePointer);
                contentStream.setFont(fontNormal, nameFontSize);
                contentStream.showText(HELYE_STRING);
                contentStream.setFont(fontBold, nameFontSize);
                contentStream.showText(name);
                contentStream.endText();

                // address
                final float addressFontSize = calculateSize(address, fontNormal, 10);

                contentStream.beginText();
                contentStream.newLineAtOffset(WIDTH / 2 - calculateTextWidth(address, fontNormal, addressFontSize) / 2, linePointer - 17.5f);
                contentStream.setFont(fontNormal, addressFontSize);
                contentStream.showText(address);
                contentStream.endText();

                // date
                final float dateFontSize = calculateSize(date, fontNormal, 10);

                contentStream.beginText();
                contentStream.newLineAtOffset(WIDTH / 2 - calculateTextWidth(date, fontNormal, dateFontSize) / 2, linePointer - 17.5f * 2);
                contentStream.setFont(fontNormal, dateFontSize);
                contentStream.showText(date);
                contentStream.endText();

                // footer
                contentStream.beginText();
                contentStream.newLineAtOffset(SIDE_MARGIN, linePointer - 17.5f * 3);
                contentStream.setFont(fontNormal, 10);
                contentStream.showText("Tel.: 36-1/267-52-62, 36-20/265-25-49, 36-20/9-156-076, 36-20/366-8000");
                contentStream.endText();
            }

            document.save(file);
        }
    }

    private float calculateSize(final String text, final PDFont font, float size) throws IOException {
        while (calculateTextWidth(text, font, size) > BOX_WIDTH) {
            size -= 0.1;
        }

        return size;
    }

    public float calculateTextWidth(final String text, final PDFont font, final float size) throws IOException {
        return font.getStringWidth(text) / 1000 * size;
    }
}
