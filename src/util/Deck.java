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
}
