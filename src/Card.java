// Purpose - Represents a single card as an integer value

public final class Card { //make this class thread-safe - therefore no setters
    private final int value;

    // Constructs and ensures the card is a positive value
    public Card(int value) {
        if (value<0) {
            throw new IllegalArgumentException("Card number must be a positive integer");
        }
        this.value = value;
    }

    // Method to return the face value of the card
    public int getValue() {
        return value;
    }

    // Method to provide a readable string representation of the card
    @Override
    public String toString() {
        return String.valueOf(value);
    }

    // Compares this card with another for equality
    // obj is the object to compare
    // return true if both cards have the same value
    @Override
    public boolean equals (Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Card)) return false;
        Card other = (Card) obj;
        return this.value == other.value;
     }

    // Returns a hash code consistent with equals()
     @Override
     public int hashCode() {
        return Integer.hashCode(value);
     }
}
