package util;

import java.util.ArrayList;

public class GoFishPlayer {
    /**
     * create a player with a player number and an empty hand
     */
    private int playerNumber;
    private ArrayList<Card> hand;

    public GoFishPlayer(int playerNumber) {
        this.playerNumber = playerNumber;
        hand = new ArrayList<Card>();
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
    }
    
    /**
     * removes all cards with a given rank and returns them
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
     * @return if a person has a certain card rank or not
     * @param card the card to check for
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
}

