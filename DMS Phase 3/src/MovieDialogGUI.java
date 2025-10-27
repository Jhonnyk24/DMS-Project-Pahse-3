// ============================================
// MovieDialogGUI.java
// Enhanced dialog for adding/editing movies with colors, fonts, and scariness dialog
// ============================================

import javax.swing.*;
import java.awt.*;

/**
 * Dialog window for creating or editing a Movie.
 * Returns a Movie object if confirmed, otherwise null.
 * Styled with dark background, custom fonts, and colors.
 */
public class MovieDialogGUI extends JDialog {

    private JTextField titleField;
    private JTextField yearField;
    private JTextField directorField;
    private JTextField ratingField;
    private JTextField runtimeField;
    private JTextField votesField;
    private JCheckBox watchedCheckBox;

    private Movie movieResult = null;

    // ----- Constructor -----
    private MovieDialogGUI(Frame parent, Movie movie) {
        super(parent, true);
        setTitle(movie == null ? "Add Movie" : "Edit Movie");
        setSize(400, 350);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(8, 2, 5, 5));
        getContentPane().setBackground(new Color(30, 30, 30));

        // ----- Fields -----
        addLabelAndField("Title:", movie != null ? movie.getTitle() : "");
        addLabelAndField("Year:", movie != null ? String.valueOf(movie.getYear()) : "");
        addLabelAndField("Director:", movie != null ? movie.getDirector() : "");
        addLabelAndField("Rating (0.0-10.0):", movie != null ? String.valueOf(movie.getRating()) : "");
        addLabelAndField("Runtime minutes:", movie != null ? String.valueOf(movie.getRuntimeMinutes()) : "");
        addLabelAndField("Votes:", movie != null ? String.valueOf(movie.getVotes()) : "");

        add(new JLabel("Watched:", SwingConstants.RIGHT));
        watchedCheckBox = new JCheckBox();
        watchedCheckBox.setBackground(new Color(30, 30, 30));
        watchedCheckBox.setForeground(Color.WHITE);
        watchedCheckBox.setSelected(movie != null && movie.isWatched());
        add(watchedCheckBox);

        // ----- Buttons -----
        JButton confirmButton = createButton("OK", new Color(76, 175, 80));
        JButton cancelButton = createButton("Cancel", new Color(244, 67, 54));
        add(confirmButton);
        add(cancelButton);

        confirmButton.addActionListener(e -> onConfirm());
        cancelButton.addActionListener(e -> onCancel());
    }

    private void addLabelAndField(String labelText, String fieldValue) {
        JLabel label = new JLabel(labelText, SwingConstants.RIGHT);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        add(label);

        JTextField field = new JTextField(fieldValue);
        field.setBackground(new Color(45, 45, 45));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        add(field);

        switch (labelText) {
            case "Title:" -> titleField = field;
            case "Year:" -> yearField = field;
            case "Director:" -> directorField = field;
            case "Rating (0.0-10.0):" -> ratingField = field;
            case "Runtime minutes:" -> runtimeField = field;
            case "Votes:" -> votesField = field;
        }
    }

    private JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        return btn;
    }

    private void onConfirm() {
        try {
            String title = titleField.getText().trim();
            int year = Integer.parseInt(yearField.getText().trim());
            String director = directorField.getText().trim();
            double rating = Double.parseDouble(ratingField.getText().trim());
            int runtime = Integer.parseInt(runtimeField.getText().trim());
            int votes = Integer.parseInt(votesField.getText().trim());
            boolean watched = watchedCheckBox.isSelected();

            movieResult = new Movie(title, year, director, rating, runtime, votes, watched);
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "⚠️ Invalid number input. Please check all fields.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "⚠️ " + ex.getMessage());
        }
    }

    private void onCancel() {
        movieResult = null;
        dispose();
    }

    // ----- Public methods -----
    public static Movie showDialog(Frame parent, Movie movie) {
        MovieDialogGUI dialog = new MovieDialogGUI(parent, movie);
        dialog.setVisible(true);
        return dialog.movieResult;
    }

    public static void showScarinessDialog(Frame parent, Movie movie) {
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setBackground(new Color(45, 45, 45));
        textArea.setForeground(Color.WHITE);
        textArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        textArea.setText(movie.prettyPrint() + "\n😱 Scariness Score: " + String.format("%.1f / 10.0", movie.getScariness()));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 120));
        JOptionPane.showMessageDialog(parent, scrollPane, "Movie Scariness", JOptionPane.INFORMATION_MESSAGE);
    }
}
