
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;

// Make this a thread safe player class
// Implement the player's deck of cards in this class, instead of making it it's own class
// Each player will hold a hand of 4 cards
// 1 card to player 1, 1 to player 2... so on and repeat until 4 cards each
// Drawing and discarding are treated as one atomic action
// At the game end, each player holds exactly 4 cards
// Player needs 4 cards of the same value in their hand
// If player has 4 cards which are all hte same value at the start of the game, they declare it and win (by their thread printing 'Player i wins')
// That player thread should notify other threatds and exit
// Player 1 will want cards with value 1, player 2 will want cards with value 2, and etc
// Player must display a card wtihout their preferred denomination
// Player must not hold onto any non-preferred denomination cards indefinitely. Implement this so the game doesn't stagnate
// 

public class Player implements Runnable { 
    private final int playerId;
    private final int preferredValue;
    private final List<Card> hand = new ArrayList<>(4);
    
    private final Deck leftDeck; //draw
    private final Deck rightDeck; //place

    private final int leftDeckIndex;   // (1-based)
    private final int rightDeckIndex;
    
    private final PrintWriter outputWriter;
    private final GameController controller; // to check for game-over signals
    private final Lock actionLock; //lock for atomic draw+discard


    public Player(int playerId, Deck leftDeck, Deck rightDeck, int LeftDeckIndex, int RightDeckIndex, GameController controller, int leftDeckIndex, int rightDeckIndex, PrintWriter outputWriter, AtomicInteger winnerId, Lock actionLock) throws IOException {
        
        this.playerId = playerId;
        this.preferredValue = 0;
        this.rightDeck = rightDeck;
        this.leftDeck = leftDeck;
        this.rightDeckIndex = rightDeckIndex;
        this.leftDeckIndex = leftDeckIndex;
        this.outputWriter = outputWriter;
        this.controller = controller; 
        this.actionLock = actionLock;

    }

    //*
    // (addCardToHand) Initial dealing, adding one card at a time 
    // hand size should be at 4

    public synchronized void addCardToHand(Card c) {
        if (hand.size() <= 4) throw new IllegalStateException("Hand has 4 cards already.");
        hand.add(c);

    }
    

    // (hasWinningHand) return true, if hand has 4 cards of the same value 
    public boolean hasWinningHand() {
        if (hand.size() != 4) return false;
        int v = hand.get(0).getValue();

        for (Card c: hand) if (c.getValue() != v) return false;
        return true;
    }

    private synchronized Card discardCard() {

    }

    public void run() { //main loop: draw --> discard --> check win --> repeat

    }

    private void logAction(String message) {

    }

}
