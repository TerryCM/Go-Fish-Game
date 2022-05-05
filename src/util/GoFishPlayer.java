package util;

import java.util.ArrayList;
import java.util.HashMap;

public class GoFishPlayer {
    /**
     * create a player with a player number and an empty hand
     */
    private final int playerNumber;
    private int numberOfBooks;
    private final ArrayList<Card> hand;
    private final String name;
    private HashMap<String, Integer> book;

    public GoFishPlayer(int playerNumber) {
        this.playerNumber = playerNumber;
        hand = new ArrayList<Card>();
        numberOfBooks = 0;
        this.name = "Player " + playerNumber;
        this.book = new HashMap<String, Integer>();
    }

    public GoFishPlayer(int playerNumber, String name) {
        this.playerNumber = playerNumber;
        hand = new ArrayList<Card>();
        numberOfBooks = 0;
        this.name = name;
        this.book = new HashMap<String, Integer>();
    }
    
    public HashMap<String,Integer> getBooks() {
    	return this.book;
    }

    public String getName() {
        return this.name;
    }

    /**
     * @return the player number
     */

    public int getPlayerNumber() {
        return playerNumber;
    }

    /**
     * @return the number of cards in the player's hand
     */
    public int handSize() {
        return hand.size();
    }

    /**
     * add a card to the player's hand
     */
    public void addCard(Card card) {
        hand.add(card);
        if (book.containsKey(card.getRank())) {
            book.put(card.getRank(), book.get(card.getRank()) + 1);
            if (book.get(card.getRank()) == 4) {
                numberOfBooks++;
                removeCards(card.getRank());
            }
        } else {
            book.put(card.getRank(), 1);
        } 
    }

    public int getNumberOfBooks() {
        return numberOfBooks;
    }

    /**
     * removes all cards with a given rank and returns them
     *
     * @return an ArrayList of all cards from a players hand with a given rank
     */
    public ArrayList<Card> removeCards(String rank) {
        ArrayList<Card> cards = new ArrayList<Card>();
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getRank().equals(rank)) {
                cards.add(hand.remove(i));
                i--;
            }
        }
        return cards;
    }

    /**
     * @param card the card to check for
     * @return if a person has a certain card rank or not
     */
    public boolean hasCard(String rank) {
        for (Card c : hand) {
            if (c.getRank().equals(rank)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return a random card from the player's hand
     */

    public Card draw() {
        int index = (int) (Math.random() * hand.size());
        return hand.remove(index);
    }

    public ArrayList<Card> getHand() {
        return this.hand;
    }

    public String toStringHand() {
        String handString = "";
        for (Card c : hand) {
            handString += c.getRank() + " " + c.getSuit() + " ";
        }
        return handString;
    }


}

