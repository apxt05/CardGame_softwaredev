// Thread safe, needs to be synchronised
// Reusable - same class works for player and decks
// Robust - Handles missing or inaccessible files as error

import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;


public class Logger {
    private final String filename;
    private final PrintWriter writer;

    // Constructor
    public Logger(String filename) {
        this.filename = filename;
        PrintWriter tempWriter = null;
        try {
            tempWriter = new PrintWriter(new FileWriter(filename, false));
        }
        catch (IOException e) {
            System.err.println("Error creating log file: " + filename);
            e.printStackTrace();
        }
        this.writer = tempWriter;
    }


    // Writes a line to the file (thread-safe)
    public synchronized void log(String message) {
        if (writer != null) {
            writer.println(message);
            writer.flush(); // immediate write
        }
    }

    // Closes the writer safely
    public void close() {
        if (writer != null) {
            writer.close();
        }
    }

    // Returns the file name
    public String getFileName() {
        return filename;
    }
}