package xyz.kocsisantal.pdf;

import org.apache.fontbox.ttf.OTFParser;
import org.apache.fontbox.ttf.OpenTypeFont;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import java.io.File;
import java.io.IOException;

public class Generator {
    public static final float WIDTH = 420f;
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
            final PDType0Font fontNormal = PDType0Font.load(document, parseFont("/ZAPFELLIPTICAL711PFL.OTF"), false);
            final PDType0Font fontBold = PDType0Font.load(document, parseFont("/ZAPFELLIPTICAL711PFL-BOLD.OTF"), false);
            final PDType0Font fontItalic = PDType0Font.load(document, parseFont("/ZAPFELLIPTICAL711PFL-ITALIC.OTF"), false);
            final PDType0Font fontBoldItalic = PDType0Font.load(document, parseFont("/ZAPFELLIPTICAL711PFL-BOLDITALIC.OTF"), false);

            // create page
            final PDPage currentPage = new PDPage(new PDRectangle(WIDTH, 1133.86f));
            document.addPage(currentPage);

            // start the page stream
            try (final PDPageContentStream contentStream = new PDPageContentStream(document, currentPage)) {

                contentStream.beginText();
                final float linePointer = 114;

                // name
                final float nameFontSize = calculateSize(HELYE_STRING + name, fontBold, 14f);

                contentStream.newLineAtOffset(WIDTH / 2 - calculateTextWidth(HELYE_STRING, fontItalic, nameFontSize) / 2 - calculateTextWidth(name, fontBold, nameFontSize) / 2, linePointer);
                contentStream.setFont(fontItalic, nameFontSize);
                contentStream.showText(HELYE_STRING);
                contentStream.setFont(fontBold, nameFontSize);
                contentStream.showText(name);
                contentStream.endText();

                // address
                final float addressFontSize = calculateSize(address, fontNormal, 12.5f);

                contentStream.beginText();
                contentStream.newLineAtOffset(WIDTH / 2 - calculateTextWidth(address, fontNormal, addressFontSize) / 2, linePointer - 17.5f);
                contentStream.setFont(fontNormal, addressFontSize);
                contentStream.showText(address);
                contentStream.endText();

                // date
                final float dateFontSize = calculateSize(date, fontNormal, 12.5f);

                contentStream.beginText();
                contentStream.newLineAtOffset(WIDTH / 2 - calculateTextWidth(date, fontNormal, dateFontSize) / 2, linePointer - 17.5f * 2);
                contentStream.setFont(fontNormal, dateFontSize);
                contentStream.showText(date);
                contentStream.endText();

                // footer
                contentStream.beginText();
                contentStream.newLineAtOffset(18, 60);
                contentStream.setFont(fontNormal, 12);
                contentStream.showText("Tel.: 36-1/267-52-62, 36-20/265-25-49, 36-20/9-156-076, 36-20/366-8000");
                contentStream.endText();
            }

            document.save(file);
        }
    }

    private OpenTypeFont parseFont(String fileName) throws IOException {
        return new OTFParser(true).parse(new File(this.getClass().getResource(fileName).getFile()));
    }

    private float calculateSize(final String text, final PDFont font, float size) throws IOException {
        while (calculateTextWidth(text, font, size) > BOX_WIDTH) {
            size -= 0.1f;
        }

        return size;
    }

    public float calculateTextWidth(final String text, final PDFont font, final float size) throws IOException {
        return font.getStringWidth(text) / 1000.0f * size;
    }
}
