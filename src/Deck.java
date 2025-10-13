// Each deck will have cards drawn from a PACK which has 8n cards
// Definition: An ordered, thread-safe container (FIFO - stack) holding card denominations from the shared 8n pack. Players draw from the top and discard from the bottom of the decks
// Player i draws from deck i and discards into deck i+1
// Drawing and discarding are treated as one atomic action
// At the end of a game, each deck's final contents are written to deckX_output.txt

public class Deck {
    private final int deckId;
    private final Queue<Card> cards;
    private final int id;

    public synchronized Card drawCard() {

    }
    public synchronized void addCard(Card card) {

    }
    public List<Card> getContents() { //for writing to deck(X)_output.txt

    }    

    public int getId() {
        this.id = id;
    }
}
