// ============================================
// MovieGUI.java (Enhanced + Integrated MovieDialogGUI)
// ============================================

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

/**
 * Enhanced Graphical User Interface for managing movies.
 * Uses MovieDialogGUI for adding/editing movies and displaying scariness.
 */
public class MovieGUI extends JFrame {

    private final MovieManager manager;
    private final DefaultTableModel tableModel;
    private final JTable movieTable;

    public MovieGUI(MovieManager manager) {
        super("🎬 Horror Movies Manager (GUI)");
        this.manager = manager;

        setSize(950, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(30, 30, 30));

        // ----- Table Setup -----
        String[] columns = {"Title", "Year", "Director", "Rating", "Runtime", "Votes", "Watched"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        movieTable = new JTable(tableModel);
        movieTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        movieTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        movieTable.setRowHeight(25);
        movieTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 16));
        movieTable.getTableHeader().setBackground(new Color(50, 50, 50));
        movieTable.getTableHeader().setForeground(Color.WHITE);

        movieTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    c.setBackground(new Color(0, 120, 215));
                    c.setForeground(Color.WHITE);
                } else {
                    c.setBackground(row % 2 == 0 ? new Color(45, 45, 45) : new Color(60, 60, 60));
                    c.setForeground(Color.WHITE);
                }
                return c;
            }
        });

        add(new JScrollPane(movieTable), BorderLayout.CENTER);

        // ----- Button Panel -----
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(30, 30, 30));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));

        JButton addButton = createButton("Add Movie", new Color(76, 175, 80));
        JButton editButton = createButton("Edit Movie", new Color(255, 193, 7));
        JButton deleteButton = createButton("Delete Movie", new Color(244, 67, 54));
        JButton uploadButton = createButton("Upload CSV", new Color(33, 150, 243));
        JButton scarinessButton = createButton("Calculate Scariness", new Color(156, 39, 176));

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(uploadButton);
        buttonPanel.add(scarinessButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // ----- Button Actions -----
        addButton.addActionListener(e -> addMovie());
        editButton.addActionListener(e -> editMovie());
        deleteButton.addActionListener(e -> deleteMovie());
        uploadButton.addActionListener(e -> uploadCSV());
        scarinessButton.addActionListener(e -> calculateScariness());

        // Load existing movies
        refreshTable();
    }

    private JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setPreferredSize(new Dimension(160, 35));
        return btn;
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Movie> movies = manager.getAll();
        for (Movie m : movies) {
            tableModel.addRow(new Object[]{
                    m.getTitle(),
                    m.getYear(),
                    m.getDirector(),
                    m.getRating(),
                    m.getRuntimeMinutes(),
                    m.getVotes(),
                    m.isWatched() ? "Yes" : "No"
            });
        }
    }

    // ----- GUI Actions using MovieDialogGUI -----
    private void addMovie() {
        Movie m = MovieDialogGUI.showDialog(this, null);
        if (m != null) {
            manager.addMovie(m);
            refreshTable();
            JOptionPane.showMessageDialog(this, "✅ Movie added successfully!");
        }
    }

    private void editMovie() {
        int row = movieTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "⚠️ Please select a movie to edit.");
            return;
        }
        Movie oldMovie = manager.getAll().get(row);
        Movie updatedMovie = MovieDialogGUI.showDialog(this, oldMovie);
        if (updatedMovie != null) {
            manager.removeMovie(row);
            manager.addMovie(updatedMovie);
            refreshTable();
            JOptionPane.showMessageDialog(this, "✅ Movie updated successfully!");
        }
    }

    private void deleteMovie() {
        int row = movieTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "⚠️ Please select a movie to delete.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this movie?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            manager.removeMovie(row);
            refreshTable();
            JOptionPane.showMessageDialog(this, "🗑️ Movie deleted successfully.");
        }
    }

    private void uploadCSV() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String path = chooser.getSelectedFile().getAbsolutePath();
            MovieManager.UploadReport report = manager.uploadCSV(path);
            refreshTable();
            JOptionPane.showMessageDialog(this, "Upload finished. Inserted: " + report.inserted + ", Errors: " + report.errors.size());
        }
    }

    private void calculateScariness() {
        int row = movieTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "⚠️ Please select a movie to calculate scariness.");
            return;
        }
        Movie m = manager.getAll().get(row);
        MovieDialogGUI.showScarinessDialog(this, m);
    }
}
