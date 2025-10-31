
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
    
    private final Deck leftDeck; // Draw
    private final Deck rightDeck; // Place

    private final int leftDeckIndex;   // (1-based)
    private final int rightDeckIndex;
    
    private final PrintWriter outputWriter;
    private final GameController controller; // To check for game-over signals
    private final Lock actionLock; // Lock for atomic draw + discard


    public Player(int playerId, Deck leftDeck, Deck rightDeck, int LeftDeckIndex, int RightDeckIndex, GameController controller, int leftDeckIndex, int rightDeckIndex, PrintWriter outputWriter, AtomicInteger winnerId, Lock actionLock) throws IOException {
        // Normalize and assign fields. preferredValue is playerId (1-based)
        this.playerId = playerId;
        this.preferredValue = playerId;
        this.leftDeck = leftDeck;
        this.rightDeck = rightDeck;
        this.leftDeckIndex = leftDeckIndex;
        this.rightDeckIndex = rightDeckIndex;
        this.outputWriter = outputWriter;
        this.controller = controller;
        this.actionLock = actionLock;

        // winnerId is not currently stored here; controller handles winner state
    }

    //*
    // (addCardToHand) Initial dealing, adding one card at a time 
    // Hand size should be at 4

    public synchronized void addCardToHand(Card c) {
        if (c == null) throw new IllegalArgumentException("Card cannot be null");
        if (hand.size() >= 4) throw new IllegalStateException("Hand already has 4 cards");
        hand.add(c);
    }
    

    // (hasWinningHand) Return true, if hand has 4 cards of the same value 
    public boolean hasWinningHand() {
        if (hand.size() != 4) return false;
        int v = hand.get(0).getValue();

        for (Card c: hand){

         if (c.getValue() != v) 
         return false;
    }
        return true;
    }

    // this is to choose which card is to be discarded after lastDrawn is added to hand, non preffered card is preferred 
    private synchronized Card chooseDiscard(Card lastDrawn) {
        for (int i = 0; i < hand.size(); i++) {
            Card c = hand.get(i);
            if (c.getValue() != preferredValue) {
                return hand.remove(i);
            }
        }
        //IF no non preffered card is found, discard last drawn or same value card
        for (int i = hand.size() - 1; i >= 0; i--) {
            Card c = hand.get(i);
            if (c == lastDrawn || c.getValue() == lastDrawn.getValue()) {
                return hand.remove(i);
            }
        }
        return hand.isEmpty() ? null : hand.remove(hand.size() - 1);
    }
        


    private void logAction(String message) {
        if (outputWriter != null) {
            outputWriter.println(message);
            outputWriter.flush();
        }
    }

    private synchronized String handToString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hand.size(); i++) {
            if (i > 0) sb.append(" ");
            sb.append(hand.get(i).getValue());
        }
        return sb.toString();
    }
    
    
    
    

    @Override
    public void run() {
    try {
        // Log initial hand once
        logAction("player " + playerId + " initial hand " + handToString());

        synchronized (this) {
            if (hasWinningHand()) {
                logAction("player " + playerId + " wins");
                controller.declareWinner(playerId);
                return;
            }
        }

        // Main loop
        while (!controller.isGameOver()) {
            actionLock.lock();
            // place discarded card into right deck (outside synchronized block)

            try {
                if (controller.isGameOver()) break;

                Card drawn = leftDeck.drawCard();

                if (drawn == null) {
                    // nothing to draw right now; yield to let others run
                    Thread.yield();
                } else {
                    Card toDiscard;
            
                    // Add drawn to hand and choose discard while holding the hand lock
                    synchronized (this) {
                        hand.add(drawn); // important — add the drawn card into the hand first
                        toDiscard = chooseDiscard(drawn); // prefer non-preferred cards
                        // defensive: ensure hand size <= 4
                        if (toDiscard == null && hand.size() > 4) {
                            toDiscard = hand.remove(hand.size() - 1);
                        }
                    } // end synchronized(this)
                    if (toDiscard != null) {
                        rightDeck.addCard(toDiscard);
                        logAction("Player " + playerId + " draws " + drawn.getValue()
                              + " and discards " + toDiscard.getValue()
                              + " | hand now: " + handToString());
                    } else {
                        // defensive logging (shouldn't normally happen)
                        logAction("Player " + playerId + " draws " + drawn.getValue()
                              + " and discards none | hand now: " + handToString());
             }
            }
        } finally {
            actionLock.unlock();
        }

            // After action, check for a win
            synchronized (this) {
                if (hasWinningHand()) {
                    logAction("player " + playerId + " wins");
                    controller.declareWinner(playerId);
                    // Removed the break statement as it is not needed here
                }
            }

            // small sleep to avoid busy spin
            try { Thread.sleep(1); } catch (InterruptedException e) { Thread.currentThread().interrupt(); break; }
        }
    } finally {
        // ensure writer closed when thread ends (flushes remaining lines)
        if (outputWriter != null) outputWriter.close();
    }


            // After action, check if player has winning hand
            synchronized (this) {
                if (hasWinningHand()) {
                    logAction("Player " + playerId + " wins");
                    controller.declareWinner(playerId);
                }
            }
        }
        
}
