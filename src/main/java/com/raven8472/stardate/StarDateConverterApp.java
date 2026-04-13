package com.raven8472.stardate;

import javax.swing.SwingUtilities;

public final class StarDateConverterApp {

    private StarDateConverterApp() {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StarDateConverter frame = new StarDateConverter();
            frame.setVisible(true);
        });
    }
}
