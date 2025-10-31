// Each deck will have cards drawn from a PACK which has 8n cards
// Definition: An ordered, thread-safe container (FIFO - stack) holding card denominations from the shared 8n pack. Players draw from the top and discard from the bottom of the decks
// Player i draws from deck i and discards into deck i+1
// Drawing and discarding are treated as one atomic action
// At the end of a game, each deck's final contents are written to deckX_output.txt

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Deck {
    private final Queue<Card> cards;
    private final int deckId;

    public Deck(Queue<Card> cards, int deckId) {
        this.cards = cards;
        this.deckId = deckId;
    }

    public synchronized Card drawCard() {
        return cards.poll();
    }
    public synchronized void addCard(Card c) {
        if (c != null) cards.offer(c);
    }

    
    
    public List<Card> getContents() { //for writing to deck(X)_output.txt
        return List.copyOf(cards);
    }    

    public int getDeckId() {
        return this.deckId;
    }

 

    public void writeOutput(String filename) {
        List<Card> snapshot;
        synchronized (this) {
            snapshot = new ArrayList<>(cards);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < snapshot.size(); i++) {
            if (i > 0) sb.append(" ");
            sb.append(snapshot.get(i).getValue());
        }
            
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
                writer.println(sb.toString());
            } catch (IOException e) {
                System.out.println("Error writing deck output to " + filename);
            }
        }   
}
