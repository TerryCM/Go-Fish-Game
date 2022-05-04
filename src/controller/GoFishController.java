package controller;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.image.ImageView;
import model.GoFishModel;
import util.Card;
import util.Deck;
import util.GoFishAi;
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
					return false;
				}
			}
		return true;
	}

	/**
	 * The ai variant of make guess which does the heavy lifting here instead of in the view.
	 * @param rankAsked
	 * @param playerToAsk
	 * @return true if the player has the card asked for and false otherwise
	 */
	public boolean makeGuessAi(int whosturn) {
		GoFishAi ai = (GoFishAi) model.getPlayers()[whosturn];
		int index = ai.checkOpposingCards();
		int playerToAsk;
		String rankAsked;
		if (index != -1) {
			playerToAsk = ai.getOpponentNum(index);
			rankAsked = ai.getOpposingCard(index);
		} else {
			ArrayList<Card> hand = ai.getHand();
			Random rand = new Random();
			playerToAsk = rand.nextInt(model.getNumPlayers());
			int  rand_int = rand.nextInt(hand.size());
			rankAsked = hand.get(rand_int).getRank();
		}
		
		if(!model.playerAskForCard(playerToAsk, rankAsked)) {
				if(!playerGoFish(rankAsked)) {
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
		return model.playerGoFish(rankAsked);
	}

	public void createDecks() {
		model.startGame();
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
	
	public String getPlayerDeckCount(String i) {
		return model.getPlayerDeckCount(i);
	}

	public String getCardsLeft() {
		// TODO Auto-generated method stub
		return model.getCardsLeft();
	}

	public void saveGame() {
		// TODO Auto-generated method stub
		model.saveGame2();
	}
	
	public ImageView[] getDeckImages() {
		return model.getDeckImages();
	}

	public void setTurnOver(boolean b) {
		// TODO Auto-generated method stub
		model.setTurnOver(b);
	}
	
	public boolean isTurnOver() {
		return model.isTurnOver();
	}

	public int getNumberOfPlayers() {
		// TODO Auto-generated method stub
		return model.getNumPlayers();
	}

	public void changeTurn() {
		model.changeTurn();
	}

}