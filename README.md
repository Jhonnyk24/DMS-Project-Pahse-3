# 🎬 Horror Movies Manager

A **Java-based Horror Movies Manager** with CSV persistence, CLI, and GUI.  
Track your favorite horror movies, add/edit/delete entries, and calculate a "scariness score" for each movie.  

---

## 🚀 Features

- **CSV Persistence**: All movies are saved in `movies.csv`.  
- **Command-Line Interface (CLI)**: List, add, edit, or delete movies quickly from the terminal.  
- **Graphical User Interface (GUI)**:  
  - Dark theme with custom fonts and colors  
  - Add/Edit movies through dialog windows  
  - Display scariness score in a scrollable text area (handles long titles)  
- **Input Validation**: Ensures all fields are correctly entered (year, rating, runtime, votes, watched).  
- **Scariness Score**: Calculates a score (0–10) based on rating, votes, runtime, and watched status.  

---

## 📂 CSV Format

The program uses a CSV file (`movies.csv`) with **7 fields**:

title,year,director,rating,runtimeMinutes,votes,watched

markdown
Copiar código

**Example row:**

Get Out,2017,Jordan Peele,7.0,104,100,true

markdown
Copiar código

- `title` → Movie title  
- `year` → Year of release (1888 - current year)  
- `director` → Director name  
- `rating` → IMDb-like rating (0.0 - 10.0)  
- `runtimeMinutes` → Movie length in minutes (>0)  
- `votes` → Number of votes (>=0)  
- `watched` → true/false  

---

## 🖥️ How to Run

### CLI Version
1. Compile all Java files:  
   ```bash
   javac *.java
Run the program:

bash
Copiar código
java Main
GUI Version
Use the MovieGUI.java and MovieDialogGUI.java classes.

Run the GUI main class:

bash
Copiar código
java MovieGUI
