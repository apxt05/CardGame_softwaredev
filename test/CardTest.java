package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import Card;

public @interface CardTest {
    @Test
    public void testCardValue() {
        Card c = new Card(5);
        assertEquals(5, c.getValue());
        assertEquals("5", c.toString());
    }

    @Test
    public void negativeCardThrows() {
        assertThrows(IllegalArgumentException.class, () -> new Card(-1));
    }
}

