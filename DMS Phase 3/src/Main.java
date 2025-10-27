// ============================================
// Main.java
// Entry point of the program
// ============================================

/**
 * Program entry point.
 * Launches the MovieGUI with persistence via "movies.csv".
 */
public class Main {
    public static void main(String[] args) {
        // Initialize the MovieManager (handles CSV loading/saving)
        MovieManager manager = new MovieManager("movies.csv");

        // Launch the GUI
        MovieGUI gui = new MovieGUI(manager);
        gui.setVisible(true);
    }
}
