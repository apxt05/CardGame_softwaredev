// Coordinates game state across threads (detects and announces winner, signals all threads to stop)

public class GameController {
    private volatile boolean gameOver = false;
    private int winningPlayerId;

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
}