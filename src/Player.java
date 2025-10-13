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

public class Player{ 
    private final int playerId;
    private final int preferredValue;
    private final List<Card> hand;
    private final Deck leftDeck;
    private final Deck rightDeck;
    private final PrintWriter outputWriter;
    private final GameController controller; // to check for game-over signals

    public void run() { //main loop: draw --> discard --> check win --> repeat

    }
    public boolean hasWinningHand() {

    }
    private void discardNonPreferredCard() {

    }
    private void logAction(String message) {

    }

}
