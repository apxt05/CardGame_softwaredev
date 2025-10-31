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
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;



public class CardGame {
    private List<Player> players;
    private List<Deck> decks;
    private GameController controller;


  
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
        //scanner done
        scanner.close();        
    

    // creating the deck
    List<Deck> decks = new ArrayList<>();


    for (int i = 1; i <= n; i++) {
        decks.add(new Deck(new LinkedList<>(), i));
    }

    GameController controller = new GameController(); // to handle gameover
    ReentrantLock actionLock = new ReentrantLock();
    AtomicInteger winnerId = new AtomicInteger(0);

    // creating the players

    List<Player> players = new ArrayList<>();

    // deck is in ring topology
    for (int i = 1; i <= n; i++) {
        try {
            PrintWriter outputWriter = new PrintWriter(new FileWriter("player" + i + "_output.txt")); // output file 
            int leftDeckIndex = i; //player draws from left deck
            int RightDeckIndex = (i % n) + 1; // player discards here
            
            //create player object
            players.add(new Player(i, decks.get(leftDeckIndex - 1), decks.get(RightDeckIndex - 1), leftDeckIndex, RightDeckIndex, controller, leftDeckIndex, RightDeckIndex, outputWriter, winnerId, actionLock));
      
            //error dealing
        } catch (IOException e) {
            System.out.println("Error in creating output file for player " + i);
            return;         
        }

    }

    // Dealing cards round robin
    int cardIndex = 0;
    // 4 rounds, each player gets one card per round
    for (int round = 0; round < 4; round++) {
        for (int i = 0; i < n; i++) {
            int cardValue = rawPack.get(cardIndex++);
            players.get(i).addCardToHand(new Card(cardValue));
        }
    }

    // Fill the decks from the remaining pack cards (round-robin)
    for (int i = 0; cardIndex < rawPack.size(); i++) {
        int deckIdx = i % n; // 0-based index of deck
        int cardValue = rawPack.get(cardIndex++);
        decks.get(deckIdx).addCard(new Card(cardValue));
}

    // start threads for player
    List<Thread> threads = new ArrayList<>();
    for (Player p : players) {
        Thread t = new Thread(p);
        threads.add(t);
        t.start();
    }

    

    // Wait for threads
    for (Thread t : threads) {
        try { t.join(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }


    //write deck outputs
    for (Deck d : decks) {
        d.writeOutput("deck" + d.getDeckId() + "_output.txt");
    }

    System.out.println("Game end");
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




    