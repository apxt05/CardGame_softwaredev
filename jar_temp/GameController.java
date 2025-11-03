// Coordinates game state across threads (detects and announces winner, signals all threads to stop)

import java.util.ArrayList;
import java.util.List;

public class GameController {
    private volatile boolean gameOver = false;
    private int winningPlayerId = 0;
    
    public synchronized boolean isGameOver() {
        return gameOver;
    }
    public synchronized void declareWinner(int playerId) {
        if (!gameOver) {
            gameOver = true;
            winningPlayerId = playerId;
        }
    }
    public synchronized int getWinnerId() {
        return winningPlayerId;
    }
    public void setWinner(int playerId) {
        declareWinner(playerId);
    }

    
    public void startGame() {
        List<Player> players = new ArrayList<>();

        // Initialize players, decks, and other game components here
        // Start player threads here
        for (Player player : players) {
            new Thread(player).start();

            while (!this.isGameOver()) {
                try {
                    Thread.sleep(100); // Give players time to run
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}