// This is the EXECUTABLE CLASS
// Main method requests via the command line the number of players, and on receiving this, the location of a valid input pack
// A valid input pack is a plain text file, where each row contains a single non-negative integer value, and has 8n rows.
// Load and validate pack
// Create decks and players
// Deal cards and distribute decks (round robin)
// Start player threads
// Wait for game termination
// Write final output files 

import java.util.List;

public class CardGame {
    private final List<Player> players;
    private final List<Deck> decks;
    private final List<Card> pack;

    public CardGame(List<Player> players, List<Deck> decks, List<Card> pack) {
        this.players = players;
        this.decks = decks;
        this.pack = pack;
    }

    public static void main(String[] args) {
        // Example usage to avoid unused field warning
        CardGame game = new CardGame(List.of(), List.of(), List.of());
        System.out.println("Number of players: " + game.players.size());
    }

    

}
