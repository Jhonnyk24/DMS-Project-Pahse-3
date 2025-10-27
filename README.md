# 🎬 Horror Movies Manager

A **CSV-persisted, CLI-driven, and GUI Horror Movies Manager** written in Java.  
Keep a database of your favorite horror films, add, edit, delete records, and compute the scariness score of each film.

---

## 🚀 Features

- **CSV Persistence**: Movies are stored in `movies.csv`.  
- **Command-Line Interface (CLI)**: List, add, edit, or delete movies quickly from the terminal.  
- **Graphical User Interface (GUI)**:  
  - Dark background with custom fonts and colors  
  - Add/Edit movies via dialog boxes  
  - Display scariness score (long titles handled) in a scrollable text area  
- **Input Validation**: Ensures all fields are valid (year, rating, runtime, votes, watched).  
- **Scariness Score**: Calculates a score (0–10) based on rating, votes, runtime, and watched status.  

---

## 📂 CSV Format

The program uses a CSV file (`movies.csv`) with **7 columns**:

title,year,director,rating,runtimeMinutes,votes,watched

markdown
Copiar código

**Example row:**

Get Out,2017,Jordan Peele,7.0,104,100,true

markdown
Copiar código

**Field descriptions:**

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
Use the Java classes MovieGUI.java and MovieDialogGUI.java.

Run the GUI main class:

bash
Copiar código
java MovieGUI
