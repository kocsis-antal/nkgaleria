package xyz.kocsisantal.pdf;

import org.apache.fontbox.ttf.OTFParser;
import org.apache.fontbox.ttf.OpenTypeFont;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class Font {
    public static final String ZAPF = "Zapf";
    public static final String CRIMSON_TEXT = "CrimsonText";
    public static final String CHARIS = "Charis";
    public static final String AMIRI = "Amiri";
    public static final String LIBERATION_SERIF = "LiberationSerif";

    public static PDFont load(String name, PDDocument document, TYPE type) throws ClassNotFoundException, NoSuchFieldException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        Class<?> aClass = Class.forName(Font.class.getPackage().getName() + ".Font$Font" + name);
//        Class<?> aClass = Class.forName(FontZapf.class.getCanonicalName());
        Field aClassField = aClass.getDeclaredField("font" + type);

        String fileName = (String) aClassField.get(aClass.getDeclaredConstructor().newInstance());

        PDFont font;
        if (fileName.toUpperCase().endsWith(".OTF")) {
            font = PDType0Font.load(document, parseFont(fileName), false);
        } else if (fileName.toUpperCase().endsWith(".TTF")) {
            font = PDType0Font.load(document, Font.class.getResourceAsStream(fileName));
        } else throw new RuntimeException("Hibás font név");

        return font;
    }

    private static OpenTypeFont parseFont(String fileName) throws IOException {
        return new OTFParser(true).parse(Font.class.getResourceAsStream(fileName));
    }

    public enum TYPE {Regular, Bold, Italic, BoldItalic}

    public static class FontZapf {
        final static String fontRegular = "/zapf-eliptical-711/ZAPFELLIPTICAL711PFL.OTF";
        final static String fontBold = "/zapf-eliptical-711/ZAPFELLIPTICAL711PFL-BOLD.OTF";
        final static String fontItalic = "/zapf-eliptical-711/ZAPFELLIPTICAL711PFL-ITALIC.OTF";
        final static String fontBoldItalic = "/zapf-eliptical-711/ZAPFELLIPTICAL711PFL-BOLDITALIC.OTF";
    }

    private static class FontCrimsonText {
        final static String fontRegular = "/crimson-text/CrimsonText-Roman.ttf";
        final static String fontBold = "/crimson-text/CrimsonText-Bold.ttf";
        final static String fontItalic = "/crimson-text/CrimsonText-Italic.ttf";
        final static String fontBoldItalic = "/crimson-text/CrimsonText-BoldItalic.ttf";
    }

    private static class FontCharis {
        final static String fontRegular = "/charis-sil/CharisSILR.ttf";
        final static String fontBold = "/charis-sil/CharisSILB.ttf";
        final static String fontItalic = "/charis-sil/CharisSILBI.ttf";
        final static String fontBoldItalic = "/charis-sil/CharisSILI.ttf";
    }

    private static class FontAmiri {
        final static String fontRegular = "/amiri/Amiri-Regular.ttf";
        final static String fontBold = "/amiri/Amiri-Bold.ttf";
        final static String fontItalic = "/amiri/Amiri-Slanted.ttf";
        final static String fontBoldItalic = "/amiri/Amiri-BoldSlanted.ttf";
    }

    private static class FontLiberationSerif {
        final static String fontRegular = "/liberation-serif/LiberationSerif-Regular.ttf";
        final static String fontBold = "/liberation-serif/LiberationSerif-Bold.ttf";
        final static String fontItalic = "/liberation-serif/LiberationSerif-Italic.ttf";
        final static String fontBoldItalic = "/liberation-serif/LiberationSerif-BoldItalic.ttf";
    }
}
