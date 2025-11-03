package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.LinkedList;
import java.util.Queue;

public class DeckTest {
    @Test
    public void testAddAndDraw() {
        Queue<Card> q = new LinkedList<>();
        Deck d = new Deck(q, 1);
        Card a = new Card(1);
        Card b = new Card(2);
        d.addCard(a);
        d.addCard(b);
        assertEquals(1, d.drawCard().getValue());
        assertEquals(2, d.drawCard().getValue());
        assertNull(d.drawCard());
    }

    @Test
    public void testGetContentsSnapshot() {
        Queue<Card> q = new LinkedList<>();
        Deck d = new Deck(q, 2);
        d.addCard(new Card(3));
        d.addCard(new Card(4));
        assertEquals(2, d.getContents().size());
    }
}
 {
    
}


import javax.smartcardio.Card;