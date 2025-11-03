package test;

import Card;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Card_Test {
    @Test
    void testCardValueAfterInitialized(){
        assertEquals(2, new Card(2).number) ;
    }

    @Test
    void testToString(){
        assertEquals("Card: 3", new Card(3).toString());
    }
}