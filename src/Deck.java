import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class Deck {
    private final BlockingDeque<Card> cards = new LinkedBlockingDeque<>();
}
