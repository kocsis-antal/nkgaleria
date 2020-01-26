package xyz.kocsisantal.pdf;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static xyz.kocsisantal.pdf.Font.LIBERATION_SERIF;
import static xyz.kocsisantal.pdf.Font.TYPE.*;

public class Generator {
    private static final Log logger = LogFactory.getLog(Generator.class);

    private static final float WIDTH = 420f;
    private static final float MARGIN = 10f;
    private static final String HELYE_STRING = "Helye: ";
    private static final String TEL = "Tel.: 36-1/267-52-62, 36-20/265-25-49, 36-20/9-156-076, 36-20/366-8000";

    private final String name;
    private final String address;
    private final String date;

    public Generator(final String name, final String address, final String date) {
        this.name = name.toUpperCase();
        this.address = address;
        this.date = date;
    }

    public void generate(final File file) throws IOException, NoSuchMethodException, IllegalAccessException, NoSuchFieldException, InstantiationException, InvocationTargetException, ClassNotFoundException {
        logger.info("Creating new PDF [" + file.getName() + "]");
        // create document
        try (final PDDocument document = new PDDocument()) {
            final PDFont fontNormal = Font.load(LIBERATION_SERIF, document, Regular);
            final PDFont fontBold = Font.load(LIBERATION_SERIF, document, Bold);
            final PDFont fontItalic = Font.load(LIBERATION_SERIF, document, Italic);

            // create page
            final PDPage currentPage = new PDPage(new PDRectangle(WIDTH, 1133.86f));
            document.addPage(currentPage);

            // start the page stream
            try (final PDPageContentStream contentStream = new PDPageContentStream(document, currentPage)) {
                // first line
                final float linePointer = 114;

                // name
                final float nameFontSize = calculateSize(HELYE_STRING + name, fontBold, 14f);

                contentStream.beginText();
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
                final float telFontSize = 12;

                contentStream.beginText();
                contentStream.newLineAtOffset(WIDTH / 2 - calculateTextWidth(TEL, fontNormal, telFontSize) / 2, 60);
                contentStream.setFont(fontNormal, telFontSize);
                contentStream.showText(TEL);
                contentStream.endText();
            }

            logger.info("Created new PDF [" + file.getName() + "]");
            document.save(file);
        }
    }

    private float calculateSize(final String text, final PDFont font, float size) throws IOException {
        while (calculateTextWidth(text, font, size) > WIDTH - 2 * MARGIN) {
            size -= 0.1f;
        }

        logger.debug("Font size set to [" + size + "] for text: " + text);
        return size;
    }

    public float calculateTextWidth(final String text, final PDFont font, final float size) throws IOException {
        return font.getStringWidth(text) / 1000.0f * size;
    }
}
