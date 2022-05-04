package util;

import java.util.ArrayList;

// use if (player instanceof GoFishAi)
public class GoFishAi extends GoFishPlayer {

    private ArrayList<Card> opponentsCards;
    private ArrayList<Integer> opponentsNum;
    
	public GoFishAi(int playerNumber) {
		super(playerNumber);
		opponentsCards = new ArrayList<Card>();
		opponentsNum = new ArrayList<Integer>();
	}

    /**
     * add a card to the player's hand
     */
    public void addOpposingMove(Card card, Integer opponentNum) {
    	opponentsCards.add(card);
    	opponentsNum.add(opponentNum);
    }
    
    /**
     * remove a card from the tracked list;
     */
    public void removeOpposingMove(Card card, Integer playerNum) {
    	opponentsCards.remove(card);
    	opponentsNum.remove(playerNum);
    }
    
    public int checkOpposingCards() {
        for (Card c : getHand()) {
        	for (int i = 0; i < opponentsCards.size(); i++) {
	            if (c.getRank().equals(opponentsCards.get(i).getRank())) {
	                return i;
	            }
        	}
        }
        return -1;
    }
    
    public Integer getOpponentNum(int index) {
    	return opponentsNum.get(index);
    }
    
    public String getOpposingCard(int index) {
    	return opponentsCards.get(index).getRank();
    }
    
    public void removeOpposingCard(int index) {
    	opponentsNum.remove(index);
    	opponentsCards.remove(index);
    }
}
