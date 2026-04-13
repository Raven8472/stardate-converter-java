package com.raven8472.stardate;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class StarDateConverter extends JFrame {

    private static final Dimension WINDOW_SIZE = new Dimension(1240, 780);

    private static final Color LCARS_BLACK = new Color(8, 8, 14);
    private static final Color LCARS_PANEL = new Color(18, 18, 27);
    private static final Color LCARS_PANEL_ALT = new Color(24, 23, 35);
    private static final Color LCARS_LINE = new Color(56, 40, 22);
    private static final Color LCARS_ORANGE = new Color(255, 148, 38);
    private static final Color LCARS_GOLD = new Color(244, 188, 82);
    private static final Color LCARS_VIOLET = new Color(183, 118, 238);
    private static final Color LCARS_BLUE = new Color(123, 132, 244);
    private static final Color LCARS_PEACH = new Color(242, 177, 123);
    private static final Color LCARS_TEXT = new Color(250, 231, 206);
    private static final Color LCARS_MUTED = new Color(191, 177, 155);

    private static final Font HERO_FONT = new Font("Dialog", Font.BOLD, 28);
    private static final Font SECTION_FONT = new Font("Dialog", Font.BOLD, 16);
    private static final Font LABEL_FONT = new Font("Dialog", Font.BOLD, 13);
    private static final Font BODY_FONT = new Font("Monospaced", Font.PLAIN, 16);
    private static final Font OUTPUT_FONT = new Font("Monospaced", Font.BOLD, 22);
    private static final Font MICRO_FONT = new Font("Dialog", Font.BOLD, 10);

    private final StardateCalculator calculator;
    private final JTextField earthDateField;
    private final JTextField earthToStarField;
    private final JTextField starDateField;
    private final JTextField starToEarthField;
    private final JLabel statusLabel;
    private final JLabel todayStardateLabel;

    public StarDateConverter() {
        super("StarDate Converter");
        this.calculator = new StardateCalculator();
        this.earthDateField = new JTextField();
        this.earthToStarField = new JTextField("AWAITING INPUT");
        this.starDateField = new JTextField();
        this.starToEarthField = new JTextField("AWAITING INPUT");
        this.statusLabel = new JLabel("READY");
        this.todayStardateLabel = new JLabel();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(WINDOW_SIZE);
        setPreferredSize(WINDOW_SIZE);
        setContentPane(buildContent());
        pack();
        setLocationRelativeTo(null);

        updateTodayStardate();
        Timer timer = new Timer(1000, e -> updateTodayStardate());
        timer.start();
    }

    private JPanel buildContent() {
        JPanel root = new JPanel(new BorderLayout(14, 14));
        root.setBackground(LCARS_BLACK);
        root.setBorder(BorderFactory.createEmptyBorder(10, 12, 12, 12));

        root.add(createLeftRail(), BorderLayout.WEST);
        root.add(createMainConsole(), BorderLayout.CENTER);
        return root;
    }

    private JComponent createLeftRail() {
        JPanel rail = new JPanel();
        rail.setBackground(LCARS_BLACK);
        rail.setPreferredSize(new Dimension(160, 0));
        rail.setLayout(new BoxLayout(rail, BoxLayout.Y_AXIS));

        JPanel plaque = new JPanel();
        plaque.setBackground(LCARS_PANEL);
        plaque.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 0, 3, LCARS_VIOLET),
            BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));
        plaque.setLayout(new BoxLayout(plaque, BoxLayout.Y_AXIS));
        plaque.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel ship = new JLabel("USS VOYAGER");
        ship.setForeground(LCARS_GOLD);
        ship.setFont(MICRO_FONT);
        ship.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel title = new JLabel("<html>STARDATE<br>CONSOLE</html>");
        title.setForeground(LCARS_ORANGE);
        title.setFont(new Font("Dialog", Font.BOLD, 25));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel desc = new JLabel("<html>Auxiliary converter for mission logs, personnel records, and database utilities.</html>");
        desc.setForeground(LCARS_TEXT);
        desc.setFont(new Font("Dialog", Font.PLAIN, 11));
        desc.setAlignmentX(Component.LEFT_ALIGNMENT);

        plaque.add(ship);
        plaque.add(Box.createVerticalStrut(10));
        plaque.add(title);
        plaque.add(Box.createVerticalStrut(12));
        plaque.add(desc);

        rail.add(plaque);
        rail.add(Box.createVerticalStrut(12));
        rail.add(createNavButton("DATE TO STAR", LCARS_ORANGE));
        rail.add(Box.createVerticalStrut(8));
        rail.add(createNavButton("STAR TO DATE", LCARS_VIOLET));
        rail.add(Box.createVerticalStrut(8));
        rail.add(createNavButton("TODAY READOUT", LCARS_BLUE));
        rail.add(Box.createVerticalStrut(12));
        rail.add(createSystemPanel());
        rail.add(Box.createVerticalGlue());
        return rail;
    }

    private JComponent createSystemPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(LCARS_PANEL);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(LCARS_LINE, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(systemLabel("SYSTEM STATUS", LCARS_GOLD));
        panel.add(Box.createVerticalStrut(10));
        panel.add(systemValue("Converter: READY"));
        panel.add(Box.createVerticalStrut(6));
        panel.add(systemValue("Profile: AUX"));
        panel.add(Box.createVerticalStrut(6));
        panel.add(systemValue("Mode: BIDIRECTIONAL"));
        panel.add(Box.createVerticalStrut(6));
        panel.add(systemValue("Display: LCARS"));
        return panel;
    }

    private JLabel systemLabel(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setForeground(color);
        label.setFont(MICRO_FONT);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JLabel systemValue(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(LCARS_TEXT);
        label.setFont(new Font("Dialog", Font.PLAIN, 11));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JComponent createNavButton(String text, Color color) {
        JPanel panel = new RoundedPanel(18, color);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 26));
        panel.setPreferredSize(new Dimension(0, 26));
        panel.setLayout(new BorderLayout());

        JLabel label = new JLabel(text);
        label.setForeground(Color.BLACK);
        label.setFont(new Font("Dialog", Font.BOLD, 11));
        label.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 0));
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    private JComponent createMainConsole() {
        JPanel console = new JPanel(new BorderLayout(0, 10));
        console.setBackground(LCARS_BLACK);

        console.add(createHeader(), BorderLayout.NORTH);
        console.add(createWorkspace(), BorderLayout.CENTER);
        console.add(createFooter(), BorderLayout.SOUTH);
        return console;
    }

    private JComponent createHeader() {
        JPanel header = new JPanel(new BorderLayout(16, 0));
        header.setBackground(LCARS_PANEL);
        header.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(4, 4, 0, 0, LCARS_LINE),
            BorderFactory.createEmptyBorder(12, 14, 10, 14)
        ));

        JPanel titleBlock = new JPanel();
        titleBlock.setBackground(LCARS_PANEL);
        titleBlock.setLayout(new BoxLayout(titleBlock, BoxLayout.Y_AXIS));

        JLabel station = new JLabel("LCARS WORKSTATION");
        station.setForeground(LCARS_GOLD);
        station.setFont(MICRO_FONT);
        station.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel title = new JLabel("STARDATE CONVERTER CONSOLE");
        title.setForeground(LCARS_ORANGE);
        title.setFont(HERO_FONT);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        titleBlock.add(station);
        titleBlock.add(Box.createVerticalStrut(4));
        titleBlock.add(title);

        JPanel meta = new JPanel();
        meta.setBackground(LCARS_PANEL);
        meta.setLayout(new BoxLayout(meta, BoxLayout.Y_AXIS));

        JLabel role = rightMeta("ACTIVE ROLE: DATABASE OPS");
        JLabel mode = rightMeta("MODE: BIDIRECTIONAL CONVERSION");
        JLabel api = rightMeta("API: LCARS/STARDATE/AUX");

        meta.add(Box.createVerticalGlue());
        meta.add(role);
        meta.add(Box.createVerticalStrut(4));
        meta.add(mode);
        meta.add(Box.createVerticalStrut(4));
        meta.add(api);

        JPanel shell = new JPanel(new BorderLayout(16, 8));
        shell.setBackground(LCARS_PANEL);
        shell.add(titleBlock, BorderLayout.CENTER);
        shell.add(meta, BorderLayout.EAST);
        shell.add(createTopRule(), BorderLayout.SOUTH);

        return shell;
    }

    private JLabel rightMeta(String text) {
        JLabel label = new JLabel(text, SwingConstants.RIGHT);
        label.setForeground(LCARS_TEXT);
        label.setFont(MICRO_FONT);
        label.setAlignmentX(Component.RIGHT_ALIGNMENT);
        return label;
    }

    private JComponent createTopRule() {
        JPanel rule = new JPanel(new BorderLayout(8, 0));
        rule.setBackground(LCARS_PANEL);
        rule.setPreferredSize(new Dimension(0, 8));
        rule.add(flatBar(LCARS_VIOLET), BorderLayout.CENTER);
        rule.add(flatBar(LCARS_ORANGE), BorderLayout.EAST);
        return rule;
    }

    private JComponent flatBar(Color color) {
        JPanel bar = new JPanel();
        bar.setBackground(color);
        bar.setPreferredSize(new Dimension(140, 6));
        return bar;
    }

    private JComponent createWorkspace() {
        JPanel workspace = new JPanel(new BorderLayout(12, 0));
        workspace.setBackground(LCARS_BLACK);

        workspace.add(createAccentColumn(), BorderLayout.WEST);
        workspace.add(createConversionDeck(), BorderLayout.CENTER);
        return workspace;
    }

    private JComponent createAccentColumn() {
        JPanel column = new JPanel();
        column.setBackground(LCARS_BLACK);
        column.setPreferredSize(new Dimension(16, 0));
        column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));
        column.add(colorColumn(LCARS_ORANGE, 220));
        column.add(Box.createVerticalStrut(8));
        column.add(colorColumn(LCARS_VIOLET, 150));
        column.add(Box.createVerticalGlue());
        return column;
    }

    private JComponent colorColumn(Color color, int height) {
        JPanel block = new JPanel();
        block.setBackground(color);
        block.setMaximumSize(new Dimension(16, height));
        block.setPreferredSize(new Dimension(16, height));
        return block;
    }

    private JComponent createConversionDeck() {
        JPanel deck = new JPanel(new BorderLayout(0, 12));
        deck.setBackground(LCARS_BLACK);

        JPanel bands = new JPanel(new GridLayout(1, 3, 8, 0));
        bands.setBackground(LCARS_BLACK);
        bands.add(topBand("EARTH DATE", LCARS_ORANGE));
        bands.add(topBand("STARDATE", LCARS_VIOLET));
        bands.add(topBand("VOYAGER", LCARS_BLUE));

        JPanel panels = new JPanel(new GridLayout(1, 2, 12, 0));
        panels.setBackground(LCARS_BLACK);
        panels.add(createConverterPanel(
            "DATE TO STARDATE",
            LCARS_ORANGE,
            "DATE ENTRY",
            "Format: YYYY-MM-DD",
            earthDateField,
            earthToStarField,
            "STARDATE OUTPUT",
            "CONVERT",
            this::convertEarthToStar
        ));
        panels.add(createConverterPanel(
            "STARDATE TO DATE",
            LCARS_VIOLET,
            "STARDATE ENTRY",
            "Format: numeric value, e.g. 48000.00",
            starDateField,
            starToEarthField,
            "EARTH DATE OUTPUT",
            "REVERSE",
            this::convertStarToEarth
        ));

        deck.add(bands, BorderLayout.NORTH);
        deck.add(panels, BorderLayout.CENTER);
        return deck;
    }

    private JComponent topBand(String label, Color color) {
        JPanel band = new RoundedPanel(14, color);
        band.setLayout(new BorderLayout());
        JLabel text = new JLabel(label, SwingConstants.CENTER);
        text.setForeground(Color.BLACK);
        text.setFont(new Font("Dialog", Font.BOLD, 11));
        band.add(text, BorderLayout.CENTER);
        return band;
    }

    private JPanel createConverterPanel(
        String title,
        Color accent,
        String inputLabel,
        String hint,
        JTextField inputField,
        JTextField outputField,
        String outputLabel,
        String buttonLabel,
        Runnable action
    ) {
        JPanel panel = new JPanel(new BorderLayout(0, 12));
        panel.setBackground(LCARS_PANEL);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(accent, 2),
            BorderFactory.createEmptyBorder(16, 16, 16, 16)
        ));

        JPanel top = new JPanel(new BorderLayout(10, 0));
        top.setBackground(LCARS_PANEL);
        top.add(titleChip(title, accent), BorderLayout.WEST);
        top.add(createSwatchRow(), BorderLayout.CENTER);

        JPanel content = new JPanel();
        content.setBackground(LCARS_PANEL);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        content.add(sectionLabel(inputLabel));
        content.add(hintLabel(hint));
        content.add(Box.createVerticalStrut(10));

        configureInputField(inputField, accent);
        inputField.addActionListener(e -> action.run());
        content.add(inputField);

        content.add(Box.createVerticalStrut(18));
        content.add(sectionLabel(outputLabel));
        content.add(Box.createVerticalStrut(8));

        configureOutputField(outputField, accent == LCARS_ORANGE ? LCARS_GOLD : LCARS_BLUE);
        content.add(outputField);
        content.add(Box.createVerticalGlue());

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttonRow.setBackground(LCARS_PANEL);

        JButton primary = createActionButton(buttonLabel, accent);
        primary.addActionListener(e -> action.run());
        buttonRow.add(primary);

        if (accent == LCARS_VIOLET) {
            JButton clear = createActionButton("CLEAR ALL", LCARS_PEACH);
            clear.addActionListener(e -> clearAllFields());
            buttonRow.add(clear);
        }

        content.add(Box.createVerticalStrut(14));
        content.add(buttonRow);

        panel.add(top, BorderLayout.NORTH);
        panel.add(content, BorderLayout.CENTER);
        return panel;
    }

    private JComponent titleChip(String text, Color accent) {
        JPanel chip = new RoundedPanel(18, accent);
        chip.setPreferredSize(new Dimension(150, 38));
        chip.setLayout(new BorderLayout());
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setForeground(Color.BLACK);
        label.setFont(SECTION_FONT);
        chip.add(label, BorderLayout.CENTER);
        return chip;
    }

    private JComponent createSwatchRow() {
        JPanel row = new JPanel(new GridLayout(1, 5, 4, 0));
        row.setBackground(LCARS_PANEL);
        row.add(swatch(LCARS_VIOLET));
        row.add(swatch(LCARS_GOLD));
        row.add(swatch(LCARS_VIOLET));
        row.add(swatch(LCARS_PEACH));
        row.add(swatch(LCARS_BLUE));
        return row;
    }

    private JComponent swatch(Color color) {
        JPanel panel = new RoundedPanel(10, color);
        panel.setPreferredSize(new Dimension(26, 18));
        return panel;
    }

    private JLabel sectionLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(LCARS_TEXT);
        label.setFont(SECTION_FONT);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JLabel hintLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(LCARS_MUTED);
        label.setFont(LABEL_FONT);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private void configureInputField(JTextField field, Color accentColor) {
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        field.setHorizontalAlignment(SwingConstants.CENTER);
        field.setBackground(LCARS_BLACK);
        field.setForeground(accentColor);
        field.setCaretColor(accentColor);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(accentColor, 2),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        field.setFont(BODY_FONT);
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private void configureOutputField(JTextField field, Color accentColor) {
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 52));
        field.setHorizontalAlignment(SwingConstants.CENTER);
        field.setEditable(false);
        field.setBackground(LCARS_BLACK);
        field.setForeground(accentColor);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(accentColor, 2),
            BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));
        field.setFont(OUTPUT_FONT);
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private JButton createActionButton(String text, Color accentColor) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(accentColor);
        button.setForeground(Color.BLACK);
        button.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
        button.setFont(LABEL_FONT);
        return button;
    }

    private JComponent createFooter() {
        JPanel footer = new JPanel(new BorderLayout(10, 0));
        footer.setBackground(LCARS_BLACK);
        footer.setPreferredSize(new Dimension(0, 42));

        JPanel statusChip = new RoundedPanel(18, LCARS_GOLD);
        statusChip.setPreferredSize(new Dimension(130, 42));
        statusChip.setLayout(new BorderLayout());
        JLabel statusText = new JLabel("STATUS", SwingConstants.CENTER);
        statusText.setForeground(Color.BLACK);
        statusText.setFont(LABEL_FONT);
        statusChip.add(statusText, BorderLayout.CENTER);

        JPanel center = new JPanel(new BorderLayout(8, 0));
        center.setBackground(LCARS_BLACK);
        center.add(colorColumn(LCARS_VIOLET, 42), BorderLayout.WEST);

        JPanel readout = new JPanel(new GridLayout(1, 2, 10, 0));
        readout.setBackground(LCARS_BLACK);
        readout.add(consoleReadout(statusLabel));
        readout.add(consoleReadout(todayStardateLabel));
        center.add(readout, BorderLayout.CENTER);

        footer.add(statusChip, BorderLayout.WEST);
        footer.add(center, BorderLayout.CENTER);
        return footer;
    }

    private JComponent consoleReadout(JLabel label) {
        JPanel shell = new JPanel(new BorderLayout());
        shell.setBackground(LCARS_PANEL_ALT);
        shell.setBorder(BorderFactory.createEmptyBorder(0, 14, 0, 14));
        label.setForeground(LCARS_TEXT);
        label.setFont(LABEL_FONT);
        shell.add(label, BorderLayout.CENTER);
        return shell;
    }

    private void updateTodayStardate() {
        double liveStardate = calculator.convert(LocalDateTime.now().toLocalDate());
        todayStardateLabel.setText("TODAY'S STARDATE  " + String.format("%.2f", liveStardate));
    }

    private void convertEarthToStar() {
        String input = earthDateField.getText().trim();

        if (input.isEmpty()) {
            earthToStarField.setText("AWAITING INPUT");
            statusLabel.setText("ENTER AN EARTH DATE USING YYYY-MM-DD");
            return;
        }

        try {
            String stardate = calculator.format(LocalDate.parse(input));
            earthToStarField.setText(stardate);
            statusLabel.setText("DATE TO STARDATE CONVERSION COMPLETE");
        } catch (DateTimeParseException ex) {
            earthToStarField.setText("INVALID DATE");
            statusLabel.setText("INVALID EARTH DATE: USE YYYY-MM-DD");
        }
    }

    private void convertStarToEarth() {
        String input = starDateField.getText().trim();

        if (input.isEmpty()) {
            starToEarthField.setText("AWAITING INPUT");
            statusLabel.setText("ENTER A STARDATE NUMBER");
            return;
        }

        try {
            double stardate = Double.parseDouble(input);
            starToEarthField.setText(calculator.formatReverse(stardate));
            statusLabel.setText("STARDATE TO DATE CONVERSION COMPLETE");
        } catch (NumberFormatException ex) {
            starToEarthField.setText("INVALID STARDATE");
            statusLabel.setText("INVALID STARDATE: USE A NUMBER LIKE 48000.00");
        }
    }

    private void clearAllFields() {
        earthDateField.setText("");
        earthToStarField.setText("AWAITING INPUT");
        starDateField.setText("");
        starToEarthField.setText("AWAITING INPUT");
        statusLabel.setText("READY");
        earthDateField.requestFocusInWindow();
    }

    private static final class RoundedPanel extends JPanel {
        private final int arc;
        private final Color fill;

        private RoundedPanel(int arc, Color fill) {
            this.arc = arc;
            this.fill = fill;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            Graphics2D g2 = (Graphics2D) graphics.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(fill);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
            g2.dispose();
            super.paintComponent(graphics);
        }
    }
}
