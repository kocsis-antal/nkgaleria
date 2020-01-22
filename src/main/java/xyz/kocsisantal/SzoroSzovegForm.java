package xyz.kocsisantal;

import xyz.kocsisantal.pdf.Generator;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class SzoroSzovegForm {
    public JPanel mainPanel;
    private JButton buttonGenerate;
    private JTextField textFieldName;
    private JTextField textFieldAddress;
    private JTextField textFieldDate;

    public SzoroSzovegForm() {
        buttonGenerate.addActionListener(actionEvent -> {
            final String name = textFieldName.getText();
            final String address = textFieldAddress.getText();
            final String date = textFieldDate.getText();

            try {
                generatePdf(name, address, date);
            } catch (final IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void generatePdf(final String name, final String address, final String date) throws IOException {
        final Generator generator = new Generator(name, address, date);
        final File file = new File(name + ".pdf");
        generator.generate(file);
    }

}
