package xyz.kocsisantal;

import lombok.extern.apachecommons.CommonsLog;
import xyz.kocsisantal.pdf.Generator;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@CommonsLog
public class SzoroSzovegForm {
    public JPanel mainPanel;
    private JButton buttonGenerate;
    private JTextField textFieldName;
    private JTextField textFieldAddress;
    private JTextField textFieldDate;
    private JTextField textFieldAddress2;
    private JCheckBox checkBoxAddress2;
    private JSpinner spinnerName;
    private JLabel nameLabel;

    public SzoroSzovegForm() {
        spinnerName.setModel(new SpinnerNumberModel(100, 1, 100, 5));

        buttonGenerate.addActionListener(actionEvent -> {
            final String name = textFieldName.getText();
            final Integer nameSplitter = (Integer) spinnerName.getValue();

            final String address = textFieldAddress.getText();
            final String address2 = checkBoxAddress2.isSelected() ? textFieldAddress2.getText() : null;
            final String date = textFieldDate.getText();

            try {
                generatePdf(name, nameSplitter, address, address2, date);
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
        textFieldAddress2.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                textFieldAddress2.selectAll();
            }
        });
        textFieldDate.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                textFieldDate.selectAll();
            }
        });
        checkBoxAddress2.addChangeListener(e -> textFieldAddress2.setEnabled(checkBoxAddress2.isSelected()));

        textFieldName.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateNameLabel();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateNameLabel();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateNameLabel();
            }
        });
        spinnerName.addChangeListener(e -> updateNameLabel());
        updateNameLabel();
    }

    protected void updateNameLabel() {
        final String name = textFieldName.getText();
        final Integer nameSplitter = (Integer) spinnerName.getValue();

        final int splitPoint = Generator.findSplitPoint(name, nameSplitter);

        String name1 = name.toUpperCase().substring(0, splitPoint);
        String name2 = name.toUpperCase().substring(Math.min(name.length(), splitPoint + 1));

        nameLabel.setText("[" + name1 + "] <=> [" + name2 + "]");
    }

    private void generatePdf(final String name, Integer nameSplitter, final String address, final String address2, final String date) throws IOException, NoSuchMethodException, IllegalAccessException, InstantiationException, ClassNotFoundException, InvocationTargetException, NoSuchFieldException {
        final File file = new File(name + ".pdf");
        if (file.exists()) {
            UIManager.put("OptionPane.yesButtonText", "Igen");
            UIManager.put("OptionPane.noButtonText", "Nem");

            int result = JOptionPane.showConfirmDialog(mainPanel, "Már létezik a fájl, felül legyen írva? \n[" + file.getName() + "]", "Figyelmeztetés!",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            if (result != JOptionPane.YES_OPTION) {
                log.info("Overwrite not selected");
                return;
            }
            log.info("Overwrite selected...");
        }

        final Generator generator = new Generator(name, nameSplitter, address, address2, date);
        generator.generate(file);
    }
}
