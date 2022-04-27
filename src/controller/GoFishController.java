package controller;

import model.GoFishModel;
import util.Card;
import util.Deck;
import util.GoFishPlayer;

import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

public class GoFishController implements Observer {

	private GoFishModel model;

	/**
	 * Constructor for GoFishController
	 * @param model
	 */

	// consider making the controller a singleton
	public GoFishController(GoFishModel model) {
		this.model = model;
		startGame();
	}

	/**
	 * Starts the game
	 */
	private void startGame() {
		// assuming that number of players is >= 3
		// drawn cards for each player
		for (int i = 0; i < getPlayers().length; i++) {
			for (int j = 0; j < 7; j++) {
				getPlayers()[i].addCard(getDeck().draw());
			}
		}
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


	/**
	 *
	 * @return
	 */
	public boolean isGameOver() {
		return model.isGameOver();
	}


	/**
	 * This method is called whenever the observed object is changed. An
	 * application calls an {@code Observable} object's
	 * {@code notifyObservers} method to have all the object's
	 * observers notified of the change.
	 *
	 * @param o   the observable object.
	 * @param arg an argument passed to the {@code notifyObservers}
	 */
	@Override
	public void update(Observable o, Object arg) {

	}
}
