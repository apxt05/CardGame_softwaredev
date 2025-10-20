// This is the EXECUTABLE CLASS
// Main method requests via the command line the number of players, and on receiving this, the location of a valid input pack
// A valid input pack is a plain text file, where each row contains a single non-negative integer value, and has 8n rows.
// Load and validate pack
// Create decks and players
// Deal cards and distribute decks (round robin)
// Start player threads
// Wait for game termination
// Write final output files 

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;


public class CardGame {
  
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = 0;
        List<Integer> rawPack = new ArrayList<>();

    // Get number of players from user
    while (true) {
        System.out.print("Enter number of players (n): ");
                String input = scanner.nextLine().trim();
        try {
            n = Integer.parseInt(input);
            if (n <= 0) {
                System.out.println("Number of players must be a positive integer.");
                continue; 
            
        }

    

            // Load and validate pack
            System.out.print("Enter path to pack file: ");
                String path = scanner.nextLine().trim();
                
            if (path.isEmpty()) {
                System.out.println("Please enter path.");
                continue;
            }

            File packFile = new File(path);
            if (!packFile.exists() || !packFile.isFile()) {
                System.out.println("Pack file not found. Please enter a valid file path.");
                continue;
            }

            rawPack = readPackFileStrict(packFile);
                if (rawPack == null || rawPack.size() != 8 * n) {
                    System.out.println("Pack file invalid: must contain exactly " + (8 * n) + " non-negative integers.");
                    continue;
                }

                break; // input is valid

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a positive integer for number of players.");
            }
        }

        scanner.close();        
    

    // creating the deck
    List<Deck> decks = new ArrayList<>();


    for (int i = 1; i <= n; i++) {
        Queue<Card> q = new LinkedList<>();
        decks.add(new Deck(q, i, i)); 
    }

    GameController controller = new GameController();
    ReentrantLock actionLock = new ReentrantLock();
    AtomicInteger winnerId = new AtomicInteger(0);

    }


    // Reads the pack file strictly, returning null on any invalidity
    private static List<Integer> readPackFileStrict(File f) {
        List<Integer> list = new ArrayList<>();
        try (Scanner sc = new Scanner(f)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) return null;
                int value = Integer.parseInt(line);
                if (value < 0) return null;
                list.add(value);
            }
        } catch (Exception e) {
            return null;
        }
        return list;
    }

}




    