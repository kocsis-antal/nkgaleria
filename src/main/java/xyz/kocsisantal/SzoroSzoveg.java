package xyz.kocsisantal;

import javax.swing.*;

public class SzoroSzoveg {
    public static void main(String[] args) {
        JFrame form = new JFrame();
        form.setContentPane(new SzoroSzovegForm().mainPanel);
        form.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        form.pack();
        form.setVisible(true);
    }
}
