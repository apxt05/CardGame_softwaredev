public final class Card {
    private final int value;

    //ensure the card is positive value
    public Card(int value) {
        if (value<0) {
            throw new IllegalArgumentException("Card number must be a positive integer");
        }
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

