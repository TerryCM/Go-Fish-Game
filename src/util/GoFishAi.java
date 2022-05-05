package util;

import java.util.ArrayList;

// use if (player instanceof GoFishAi)
public class GoFishAi extends GoFishPlayer {
    private final ArrayList<String> opponentsRanks;
    private final ArrayList<Integer> opponentsNum;

    public GoFishAi(int playerNumber) {
        super(playerNumber);
        opponentsRanks = new ArrayList<String>();
        opponentsNum = new ArrayList<Integer>();
    }

    /**
     * add a card to the player's hand
     */
    public void addOpposingMove(String rank, Integer opponentNum) {
        opponentsRanks.add(rank);
        opponentsNum.add(opponentNum);
    }

    /**
     * remove a card from the tracked list;
     */
    public void removeOpposingMove(String rank, Integer playerNum) {
        for (int i = 0; i < opponentsRanks.size(); i++) {
            if (rank.equals(opponentsRanks.get(i)) && playerNum.equals(opponentsNum.get(i))) {
                opponentsRanks.remove(i);
                opponentsNum.remove(i);
            }
        }
    }

    public void removeRankTracking(String rank) {
        for (int i = 0; i < opponentsRanks.size(); i++) {
            if (rank.equals(opponentsRanks.get(i))) {
                opponentsRanks.remove(i);
                opponentsNum.remove(i);
            }
        }
    }

    public int checkOpposingCards() {
        for (Card c : getHand()) {
            for (int i = 0; i < opponentsRanks.size(); i++) {
                if (c.getRank().equals(opponentsRanks.get(i))) {
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
        return opponentsRanks.get(index);
    }

    public void removeOpposingCard(int index) {
        opponentsNum.remove(index);
        opponentsRanks.remove(index);
    }

    public ArrayList<String> getOpponentsRanks() {
        return this.opponentsRanks;
    }

    public ArrayList<Integer> getOpponentsNums() {
        return this.opponentsNum;
    }

    public String getTrackedCards() {
        String retval = "";
        for (int i = 0; i < getOpponentsRanks().size(); i++) {
            retval += this.opponentsRanks.get(i) + " " + this.opponentsNum.get(i) + " ";
        }
        return retval;
    }


}
