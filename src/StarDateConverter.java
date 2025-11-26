/**
 * StarDateConverter.java
 *
 * Created by Dakota Leahy (1st project)
 * Date: October 21st, 2025 — revised october 22nd,23rd,24th,25th, 2025
 *
 * Description:
 * Voyager-themed LCARS interface for converting Earth dates (YYYY-MM-DD)
 * into Star Trek like stardates using a simplified formula.
 *
 * Notes:
 * - Proper LCARS colors for input and output panels are defined as constants.
 * - Stardate scaling uses day-of-year for correct fractional progress through the year.
 * (ME and VS CODE not GETTING ALONG with multiple save iterations being indentical!!!!!! AHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH)
 */

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class StarDateConverter extends JFrame {

    // Window dimensions
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int PANEL_WIDTH = 300;
    private static final int PANEL_HEIGHT = 100;

    // LCARS color palette (kept as constants for clarity)
    private static final Color LCARS_BACKGROUND = new Color(0, 0, 0);
    private static final Color LCARS_ACCENT_ORANGE = new Color(255, 153, 0);
    private static final Color LCARS_ACCENT_VIOLET = new Color(204, 153, 255);
    private static final Color LCARS_INPUT_BG = new Color(40, 40, 40);      // dark terminal background for input field
    private static final Color LCARS_INPUT_TEXT = LCARS_ACCENT_ORANGE;     // orange text for input
    private static final Color LCARS_OUTPUT_BG = new Color(30, 30, 30);     // slightly different dark for output
    private static final Color LCARS_OUTPUT_TEXT = LCARS_ACCENT_VIOLET;    // violet text for output
    private static final Color LCARS_PANEL_INPUT = new Color(255, 153, 0);  // panel accent (orange)
    private static final Color LCARS_PANEL_OUTPUT = new Color(204, 153, 255);// panel accent (violet)

    public StarDateConverter() {
        setTitle("StarDateConverter Interface");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        layeredPane.setLayout(null);

        // Background and border accents
        layeredPane.add(createLCARSPanel(0, 0, WIDTH, HEIGHT, LCARS_BACKGROUND, null), Integer.valueOf(0));
        layeredPane.add(createLCARSPanel(0, 0, WIDTH, 6, LCARS_ACCENT_ORANGE, null), Integer.valueOf(1));
        layeredPane.add(createLCARSPanel(0, HEIGHT - 6, WIDTH, 6, LCARS_ACCENT_ORANGE, null), Integer.valueOf(1));
        layeredPane.add(createLCARSPanel(0, 0, 6, HEIGHT, LCARS_ACCENT_ORANGE, null), Integer.valueOf(1));
        layeredPane.add(createLCARSPanel(WIDTH - 6, 0, 6, HEIGHT, LCARS_ACCENT_ORANGE, null), Integer.valueOf(1));

        // Accent strips
        layeredPane.add(createLCARSPanel(50, 150, 12, 420, LCARS_ACCENT_ORANGE, null), Integer.valueOf(1));
        layeredPane.add(createLCARSPanel(400, 150, 12, 420, LCARS_ACCENT_VIOLET, null), Integer.valueOf(1));

        // Headers
        layeredPane.add(createLCARSPanel(50, 50, PANEL_WIDTH, PANEL_HEIGHT, LCARS_PANEL_INPUT, "LCARS INPUT NODE"), Integer.valueOf(2));
        layeredPane.add(createLCARSPanel(400, 50, PANEL_WIDTH, PANEL_HEIGHT, LCARS_PANEL_OUTPUT, "LCARS OUTPUT NODE"), Integer.valueOf(2));

        // Input / Output components
        JTextField earthDateField = new JTextField();
        JTextField starDateField = new JTextField();
        JButton convertButton = new JButton("Convert to StarDate");

        // Button styling (LCARS feel)
        convertButton.setBackground(LCARS_BACKGROUND);
        convertButton.setForeground(LCARS_ACCENT_ORANGE);
        convertButton.setFont(new Font("Arial", Font.BOLD, 16));
        convertButton.setFocusPainted(false);
        convertButton.setBorder(BorderFactory.createLineBorder(LCARS_ACCENT_ORANGE, 2));

        layeredPane.add(createInputPanel(earthDateField, convertButton), Integer.valueOf(2));
        layeredPane.add(createOutputPanel(starDateField), Integer.valueOf(2));

        // Conversion action
        convertButton.addActionListener(e -> {
            String input = earthDateField.getText();
            starDateField.setText(convertToStardate(input));
        });

        setContentPane(layeredPane);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createLCARSPanel(int x, int y, int width, int height, Color color, String text) {
        JPanel panel = new JPanel();
        panel.setBounds(x, y, width, height);
        panel.setBackground(color);
        panel.setLayout(new BorderLayout());

        if (text != null && !text.isEmpty()) {
            JLabel label = new JLabel(text, SwingConstants.CENTER);
            label.setForeground(Color.BLACK);
            label.setFont(new Font("Arial", Font.BOLD, 18));
            panel.add(label, BorderLayout.CENTER);
        }

        return panel;
    }

    private JPanel createInputPanel(JTextField earthDateField, JButton convertButton) {
        JPanel panel = new JPanel();
        panel.setBounds(50, 200, PANEL_WIDTH, PANEL_HEIGHT);
        panel.setBackground(LCARS_PANEL_INPUT);
        panel.setLayout(new GridLayout(3, 1, 6, 6));

        JLabel label = new JLabel("Earth Date (YYYY-MM-DD)", SwingConstants.CENTER);
        label.setForeground(Color.BLACK);
        label.setFont(new Font("Arial", Font.BOLD, 14));

        earthDateField.setHorizontalAlignment(JTextField.CENTER);
        earthDateField.setBackground(LCARS_INPUT_BG);
        earthDateField.setForeground(LCARS_INPUT_TEXT);
        earthDateField.setBorder(BorderFactory.createLineBorder(LCARS_ACCENT_ORANGE, 2));
        earthDateField.setFont(new Font("Monospaced", Font.PLAIN, 14));

        panel.add(label);
        panel.add(earthDateField);
        panel.add(convertButton);

        return panel;
    }

    private JPanel createOutputPanel(JTextField starDateField) {
        JPanel panel = new JPanel();
        panel.setBounds(400, 200, PANEL_WIDTH, PANEL_HEIGHT);
        panel.setBackground(LCARS_PANEL_OUTPUT);
        panel.setLayout(new GridLayout(2, 1, 6, 6));

        JLabel label = new JLabel("Star Date", SwingConstants.CENTER);
        label.setForeground(Color.BLACK);
        label.setFont(new Font("Arial", Font.BOLD, 14));

        starDateField.setHorizontalAlignment(JTextField.CENTER);
        starDateField.setEditable(false);
        starDateField.setBackground(LCARS_OUTPUT_BG);
        starDateField.setForeground(LCARS_OUTPUT_TEXT);
        starDateField.setBorder(BorderFactory.createLineBorder(LCARS_ACCENT_VIOLET, 2));
        starDateField.setFont(new Font("Monospaced", Font.BOLD, 14));

        panel.add(label);
        panel.add(starDateField);
        return panel;
    }

    // Convert using year offset and day-of-year scaling for fractional component
    private String convertToStardate(String input) {
        try {
            LocalDate date = LocalDate.parse(input.trim()); // expects YYYY-MM-DD
            int year = date.getYear();
            int baseYear = 2323;
            int dayOfYear = date.getDayOfYear();
            int daysInYear = date.isLeapYear() ? 366 : 365;

            // Stardate: 1000*(year - baseYear) + fractional part based on day-of-year
            double stardate = 1000.0 * (year - baseYear) + (1000.0 * (dayOfYear - 1) / daysInYear);
            return String.format("%.2f", stardate);
        } catch (Exception ex) {
            return "Invalid date format";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StarDateConverter::new);
    }
}

