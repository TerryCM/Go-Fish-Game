package controller;

import model.GoFishModel;
import util.Card;
import util.Deck;
import util.GoFishPlayer;

public class GoFishController {

	private GoFishModel model;

	/**
	 * Constructor for GoFishController
	 * @param model
	 */

	// consider making the controller a singleton
	public GoFishController(GoFishModel model) {
		this.model = model;
	}

	/**
	 *
	 * @param rankAsked
	 * @param playerToAsk
	 * @return true if the player has the card asked for and false otherwise
	 */
	public boolean makeGuess(String rankAsked, int playerToAsk) {
		if(!model.playerAskForCard(playerToAsk, rankAsked)) {
				if(!playerGoFish(rankAsked)) {
					model.changeTurn();
					return false;
				}
			}
		return true;
	}


	/**
	 * method to make the current player "go fish" from the deck.
	 *
	 * this occurs if a player asks for a card and the player being asked
	 * does not have any of that cards rank.
	 *
	 * player pulls a random card from the deck and brings it to their hand
	 */
	public boolean playerGoFish(String rankAsked) {
		//draw random card from the shuffled deck
		Card fishedCard = getDeck().draw();
		//add to players hand
		getPlayers()[getWhosTurn()].addCard(fishedCard);
		return fishedCard.getRank().equals(rankAsked);
	}

	public GoFishPlayer[] getPlayers() {
		return model.getPlayers();
	}
	
	public int getWhosTurn() {
		return model.getWhosTurn();
	}
	
	public Deck getDeck() {
		return model.getDeck();
	}

	public boolean isGameOver() {
		return model.isGameOver();
	}

	public String getOurCurrentHand() {
		// TODO Auto-generated method stub
		return model.getOurCurrentHand();
	}
	
	public String getPlayerDeckCount(int i) {
		return model.getPlayerDeckCount(i);
	}

	public String getCardsLeft() {
		// TODO Auto-generated method stub
		return model.getCardsLeft();
	}

}
