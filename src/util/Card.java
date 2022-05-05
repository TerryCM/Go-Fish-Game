package util;

public class Card {
    private final String suit;
    private final String rank;

    /**
     * create a card with a suit and a rank
     */
    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }

    /*
     * get the suit of the card
     */
    public String getSuit() {
        return suit;
    }

    /*
     * @return the rank of the card
     */
    public String getRank() {
        return rank;
    }

    /**
     * return the card as a string
     */
    public String toString() {
        return rank + " of " + suit;
    }

    /**
     * used for comparing cards based off their rank
     *
     * @param other other card to compare to
     * @return boolean whether the cards are the same or not based on rank.
     */
    public boolean equalsOnRank(Card other) {
        return this.rank.equals(other.rank);
    }

}

