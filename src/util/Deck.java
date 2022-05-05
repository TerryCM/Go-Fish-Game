package util;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    /**
     * create a deck of cards using the Card class
     */

    private ArrayList<Card> cards;

    public Deck() {
        cards = new ArrayList<Card>();
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
        // create 52 cards
        for (String suit : suits) {
            for (String rank : ranks) {
                cards.add(new Card(suit, rank));
            }
        }
        // shuffle the deck
        Collections.shuffle(cards);
    }

    /**
     * @return the number of cards in the deck
     */
    public int size() {
        return cards.size();
    }


    /**
     * @return a random card from the deck
     */
    public Card draw() {
        int index = (int) (Math.random() * cards.size());
        return cards.remove(index);
    }

    /**
     * @return a String representation of the deck
     */
    public String toStringDeck() {
        String listString = "";
        for (Card c : this.cards) {
            listString += c.getRank() + " " + c.getSuit() + ", ";
        }
        return listString;
    }

    /**
     * @param newCards, an ArrayList of cards to replace the deck with
     */
    public void replaceDeck(ArrayList<Card> newCards) {
        this.cards = newCards;
    }

    /**
     * @param newDeck, an ArrayList of cards to add to the deck
     */
    protected void addNewDeck(Deck newDeck) {
        this.cards.addAll(newDeck.cards);
    }
}
