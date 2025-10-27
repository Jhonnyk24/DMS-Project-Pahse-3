// ============================================
// MovieDialog.java
// Dialog for adding or editing a movie
// ============================================

import javax.swing.*;
import java.awt.*;

/**
 * Dialog window for creating or editing a Movie.
 * Returns a Movie object if confirmed, otherwise null.
 */
public class MovieDialog extends JDialog {

    private JTextField titleField;
    private JTextField yearField;
    private JTextField directorField;
    private JTextField ratingField;
    private JTextField runtimeField;
    private JTextField votesField;
    private JCheckBox watchedCheckBox;

    private Movie movieResult = null; // Movie to return

    // ----- Constructor -----
    private MovieDialog(Frame parent, Movie movie) {
        super(parent, true);
        setTitle(movie == null ? "Add Movie" : "Edit Movie");
        setSize(400, 350);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(8, 2, 5, 5));

        // ----- Fields -----
        add(new JLabel("Title:"));
        titleField = new JTextField(movie != null ? movie.getTitle() : "");
        add(titleField);

        add(new JLabel("Year:"));
        yearField = new JTextField(movie != null ? String.valueOf(movie.getYear()) : "");
        add(yearField);

        add(new JLabel("Director:"));
        directorField = new JTextField(movie != null ? movie.getDirector() : "");
        add(directorField);

        add(new JLabel("Rating (0.0-10.0):"));
        ratingField = new JTextField(movie != null ? String.valueOf(movie.getRating()) : "");
        add(ratingField);

        add(new JLabel("Runtime minutes:"));
        runtimeField = new JTextField(movie != null ? String.valueOf(movie.getRuntimeMinutes()) : "");
        add(runtimeField);

        add(new JLabel("Votes:"));
        votesField = new JTextField(movie != null ? String.valueOf(movie.getVotes()) : "");
        add(votesField);

        add(new JLabel("Watched:"));
        watchedCheckBox = new JCheckBox();
        watchedCheckBox.setSelected(movie != null && movie.isWatched());
        add(watchedCheckBox);

        // ----- Buttons -----
        JButton confirmButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");
        add(confirmButton);
        add(cancelButton);

        // ----- Button Actions -----
        confirmButton.addActionListener(e -> onConfirm());
        cancelButton.addActionListener(e -> onCancel());
    }

    // ----- Confirm and Cancel Actions -----
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

    // ----- Static method to show dialog -----
    public static Movie showDialog(Frame parent, Movie movie) {
        MovieDialog dialog = new MovieDialog(parent, movie);
        dialog.setVisible(true);
        return dialog.movieResult;
    }
}
