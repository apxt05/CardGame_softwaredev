// Each deck will have cards drawn from a PACK which has 8n cards
// Definition: An ordered, thread-safe container (FIFO - stack) holding card denominations from the shared 8n pack. Players draw from the top and discard from the bottom of the decks
// Player i draws from deck i and discards into deck i+1
// Drawing and discarding are treated as one atomic action
// At the end of a game, each deck's final contents are written to deckX_output.txt

import java.util.List;
import java.util.Queue;

public class Deck {
    private final int deckId;
    private final Queue<Card> cards;
    private final int id;

    public Deck(Queue<Card> cards, int deckId, int id) {
        this.cards = cards;
        this.deckId = deckId;
        this.id = id;
    }

    public synchronized Card drawCard() {
        return cards.poll();
    }
    public synchronized void addCard(Card card) {
        cards.offer(card);
    }
    public List<Card> getContents() { //for writing to deck(X)_output.txt
        return List.copyOf(cards);
    }    

    public int getId() {
        return id;
    }

    public String getDeckId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDeckId'");
    }

    public void writeOutput(String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'writeOutput'");
    }
}
