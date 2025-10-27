// ============================================
// MainGUI.java
// Entry point for launching the GUI version
// ============================================

import javax.swing.SwingUtilities;

/**
 * Main class to launch the Horror Movies Manager GUI.
 */
public class MainGUI {
    public static void main(String[] args) {
        // Use the same CSV file as the CLI version for persistence
        MovieManager manager = new MovieManager("movies.csv");

        // Launch GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            MovieGUI gui = new MovieGUI(manager);
            gui.setVisible(true);
        });
    }
}
