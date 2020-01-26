package xyz.kocsisantal;

import xyz.kocsisantal.pdf.Generator;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

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
            } catch (final IOException | InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | NoSuchFieldException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        textFieldName.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                textFieldName.selectAll();
            }
        });
        textFieldAddress.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                textFieldAddress.selectAll();
            }
        });
        textFieldDate.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                textFieldDate.selectAll();
            }
        });
    }

    private void generatePdf(final String name, final String address, final String date) throws IOException, NoSuchMethodException, IllegalAccessException, InstantiationException, ClassNotFoundException, InvocationTargetException, NoSuchFieldException {
        final Generator generator = new Generator(name, address, date);
        final File file = new File(name + ".pdf");
        generator.generate(file);
    }

}
