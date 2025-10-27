// ============================================
// Movie.java
// Represents a single movie with validation and CSV support
// ============================================

import java.time.Year;

/**
 * Represents a single movie and contains CSV-parsing, validation, and utility methods.
 */
public class Movie {
    // Movie attributes
    private String title;
    private int year;
    private String director;
    private double rating;         // 0.0 - 10.0
    private int runtimeMinutes;    // > 0
    private int votes;             // >= 0
    private boolean watched;

    // ----- Constructors -----

    /**
     * Full constructor to create a movie with all fields.
     */
    public Movie(String title, int year, String director, double rating, int runtimeMinutes, int votes, boolean watched) {
        this.title = title;
        this.year = year;
        this.director = director;
        this.rating = rating;
        this.runtimeMinutes = runtimeMinutes;
        this.votes = votes;
        this.watched = watched;
    }

    /**
     * Empty constructor (optional, useful for frameworks or default creation).
     */
    public Movie() {}

    // ----- Getters -----
    public String getTitle() { return title; }
    public int getYear() { return year; }
    public String getDirector() { return director; }
    public double getRating() { return rating; }
    public int getRuntimeMinutes() { return runtimeMinutes; }
    public int getVotes() { return votes; }
    public boolean isWatched() { return watched; }

    // ----- Utility Methods -----

    /**
     * Calculates a "scariness" score between 0.0 and 10.0.
     * Based on rating, votes, runtime, and whether the movie has been watched.
     */
    public double getScariness() {
        double score = rating; // base
        score += Math.min(votes / 500000.0, 2); // max +2 from votes
        if (runtimeMinutes > 120) score += 1;   // longer movies are scarier
        if (watched) score -= 1;                // seen movies are less scary
        if (score < 0) score = 0;
        if (score > 10) score = 10;
        return score;
    }

    /**
     * Convert the movie to a CSV string.
     * Format: title,year,director,rating,runtimeMinutes,votes,watched
     */
    @Override
    public String toString() {
        return String.format("%s,%d,%s,%.1f,%d,%d,%s",
                title, year, director, rating, runtimeMinutes, votes, watched);
    }

    /**
     * Pretty string for GUI or CLI display.
     */
    public String prettyPrint() {
        return String.format("🎬 %s (%d) - Dir: %s | Rating: %.1f | %d min | Votes: %d | Watched: %s",
                title, year, director, rating, runtimeMinutes, votes, watched ? "Yes" : "No");
    }

    /**
     * Parses a CSV line into a Movie object.
     * Performs validation and throws IllegalArgumentException on errors.
     *
     * @param line CSV line (7 fields)
     * @return Movie object
     */
    public static Movie fromCSV(String line) throws IllegalArgumentException {
        if (line == null) throw new IllegalArgumentException("Line is null");

        String[] parts = line.split(",", -1); // keep empty fields
        if (parts.length != 7) throw new IllegalArgumentException("Expected 7 fields but found " + parts.length);

        for (int i = 0; i < parts.length; i++) parts[i] = parts[i].trim();

        String title = parts[0];
        if (title.isEmpty()) throw new IllegalArgumentException("Title is empty");

        int year;
        try { year = Integer.parseInt(parts[1]); }
        catch (NumberFormatException e) { throw new IllegalArgumentException("Year is not a valid integer: '" + parts[1] + "'"); }
        int currentYear = Year.now().getValue();
        if (year < 1888 || year > currentYear) throw new IllegalArgumentException("Year must be between 1888 and " + currentYear);

        String director = parts[2];
        if (director.isEmpty()) throw new IllegalArgumentException("Director is empty");

        double rating;
        try { rating = Double.parseDouble(parts[3]); }
        catch (NumberFormatException e) { throw new IllegalArgumentException("Rating is not a valid number: '" + parts[3] + "'"); }
        if (rating < 0.0 || rating > 10.0) throw new IllegalArgumentException("Rating must be between 0.0 and 10.0");

        int runtime;
        try { runtime = Integer.parseInt(parts[4]); }
        catch (NumberFormatException e) { throw new IllegalArgumentException("Runtime is not a valid integer: '" + parts[4] + "'"); }
        if (runtime <= 0) throw new IllegalArgumentException("Runtime must be a positive integer");

        int votes;
        try { votes = Integer.parseInt(parts[5]); }
        catch (NumberFormatException e) { throw new IllegalArgumentException("Votes is not a valid integer: '" + parts[5] + "'"); }
        if (votes < 0) throw new IllegalArgumentException("Votes must be 0 or greater");

        boolean watched;
        String w = parts[6].toLowerCase();
        if (w.equals("true") || w.equals("yes") || w.equals("y") || w.equals("1")) watched = true;
        else if (w.equals("false") || w.equals("no") || w.equals("n") || w.equals("0")) watched = false;
        else throw new IllegalArgumentException("Watched must be true/false, yes/no, or 1/0: '" + parts[6] + "'");

        return new Movie(title, year, director, rating, runtime, votes, watched);
    }
}
