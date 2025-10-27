// ============================================
// MovieManager.java
// Handles the in-memory movie list and CSV persistence
// ============================================

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages a list of movies in memory and handles saving/loading to CSV.
 */
public class MovieManager {

    private final String filePath;  // CSV file path
    private List<Movie> movies = new ArrayList<>();

    /**
     * Simple report returned by uploadCSV so the GUI/CLI can display results.
     */
    public static class UploadReport {
        public final int inserted;
        public final List<String> errors;

        public UploadReport(int inserted, List<String> errors) {
            this.inserted = inserted;
            this.errors = errors;
        }
    }

    // ----- Constructor -----

    /**
     * Initializes the manager with a CSV file path and loads existing movies.
     *
     * @param filePath path to the CSV file
     */
    public MovieManager(String filePath) {
        this.filePath = filePath;
        loadMovies();
    }

    // ----- CSV Persistence -----

    /**
     * Loads movies from the CSV file.
     * Skips invalid lines and prints warnings to console.
     */
    public void loadMovies() {
        movies.clear();
        File f = new File(filePath);
        if (!f.exists()) return; // no file yet

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            int lineNum = 0;
            while ((line = br.readLine()) != null) {
                lineNum++;
                line = line.trim();
                if (line.isEmpty()) continue;

                // skip header
                if (lineNum == 1 && line.toLowerCase().contains("title")) continue;

                try {
                    movies.add(Movie.fromCSV(line));
                } catch (IllegalArgumentException ex) {
                    System.out.println("Skipping invalid CSV line " + lineNum + ": " + ex.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file '" + filePath + "': " + e.getMessage());
        }
    }

    /**
     * Saves the current list of movies to the CSV file.
     */
    public void saveMovies() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("title,year,director,rating,runtimeMinutes,votes,watched");
            bw.newLine();
            for (Movie m : movies) {
                bw.write(m.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving to file '" + filePath + "': " + e.getMessage());
        }
    }

    // ----- Movie Operations -----

    /**
     * Returns a copy of all movies in memory.
     */
    public List<Movie> getAll() {
        return new ArrayList<>(movies);
    }

    /**
     * Adds a new movie and saves to CSV.
     */
    public void addMovie(Movie m) {
        movies.add(m);
        saveMovies();
    }

    /**
     * Removes a movie at the specified index and saves to CSV.
     *
     * @param index index of movie to remove
     * @return true if removed, false if index invalid
     */
    public boolean removeMovie(int index) {
        if (index >= 0 && index < movies.size()) {
            movies.remove(index);
            saveMovies();
            return true;
        } else return false;
    }

    /**
     * Uploads movies from a CSV file and returns a report of inserted movies and errors.
     *
     * @param csvPath path to CSV file
     * @return UploadReport with number of inserted movies and errors
     */
    public UploadReport uploadCSV(String csvPath) {
        int inserted = 0;
        List<String> errors = new ArrayList<>();
        File f = new File(csvPath);
        if (!f.exists()) {
            errors.add("File not found: " + csvPath);
            return new UploadReport(0, errors);
        }

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            int lineNum = 0;
            while ((line = br.readLine()) != null) {
                lineNum++;
                line = line.trim();
                if (line.isEmpty()) continue;
                if (lineNum == 1 && line.toLowerCase().contains("title")) continue;

                try {
                    Movie m = Movie.fromCSV(line);
                    movies.add(m);
                    inserted++;
                } catch (IllegalArgumentException ex) {
                    errors.add("Line " + lineNum + ": " + ex.getMessage());
                }
            }
            if (inserted > 0) saveMovies();
        } catch (IOException e) {
            errors.add("I/O error while reading the file: " + e.getMessage());
        }

        return new UploadReport(inserted, errors);
    }
}
