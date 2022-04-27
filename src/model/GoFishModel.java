package model;

import java.util.ArrayList;
import java.util.Observable;

import util.GoFishPlayer;
import util.Card;
import util.Deck;


@SuppressWarnings("deprecation")
public class GoFishModel extends Observable{
	/**
	 * keep track of which players turn it is, start at 0.
	 */
	private int whosTurn;
	/**
	 * keep track of if the game is over or not, start as false
	 */
	private boolean gameOver;
	/**
	 * an array of all players in the game
	 */
	private GoFishPlayer[] players;
	/**
	 * how many players are present
	 */
	private int numberOfPlayers;
	/**
	 * deck of cards being used for the game
	 */
	private Deck deck;

	/**
	 * constructor for the model
	 */
	public GoFishModel(int numberOfPlayers) {
		this.whosTurn = 0;
		this.gameOver = false;
		this.numberOfPlayers = numberOfPlayers;
		this.players = new GoFishPlayer[this.numberOfPlayers];
		this.deck = new Deck();
		//creating the players
		for(int i = 0; i < numberOfPlayers; i++) {
			players[i] = new GoFishPlayer(i);
		}
	}
	
	/**
	 * change who's turn it is
	 */
	public void changeTurn() {
		//if we are at the last player, change to first player
		this.whosTurn = (this.whosTurn + 1) % this.numberOfPlayers;
	}
	
	/**
	 * returns which players turn it is, 0 being first player.
	 * @return int 0 - 3 depending on how many players there are present
	 */
	public int getWhosTurn() {
		return this.whosTurn;
	}
	

	
	/**
	 * method that controls a player asking for a card from another player
	 * 
	 * if the other player has a card with that rank, the card is taken from their hand
	 * and given to another player.
	 * 
	 * returns true if the player had the card, and takes it.
	 * false if the player did not have the card.
	 */
	public boolean playerAskForCard(int playerToAsk, String rankOfCard) {
		// does the player asked have any of that rank of card?
		if (players[playerToAsk].hasCard(rankOfCard)) {
			//removing all of the card asked
			ArrayList<Card> stolenCards = players[playerToAsk].removeCards(rankOfCard);
			for (Card c : stolenCards) {
				//adding them to the hand of the player who asked
				players[whosTurn].addCard(c);
			}
			//cards were taken
			return true;
		} else {
			//cards not taken, need to make the current player go fish.
			return false;

		}
	}
	
	
	/**
	 * return true if the game is over, false otherwise
	 * @return boolean if game is over
	 */
	public boolean isGameOver() {
		return this.gameOver;
	}
	
	/**
	 * method to save a game in its current state
	 */
	public void saveGame() {
		return;
	}
	
	/**
	 * method to load a game given a save game file.
	 */
	public void loadGame() {
		return;
	}

	/**
	 *
	 * @return players array
	 */
	public GoFishPlayer[] getPlayers() {
		return players;
	}

	public Deck getDeck() {
		return deck;
	}

}

