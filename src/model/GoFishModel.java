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
	}
	
	/**
	 * change who's turn it is
	 */
	public void changeTurn() {
		//if we are at the last player, change to first player
		if (this.whosTurn == numberOfPlayers - 1) {
			this.whosTurn = 0;
		} else {
			this.whosTurn += 1;
		}
	}
	
	/**
	 * returns which players turn it is, 0 being first player.
	 * @return int 0 - 3 depending on how many players there are present
	 */
	public int getWhosTurn() {
		return this.whosTurn;
	}
	
	/**
	 * method to make the current player "go fish" from the deck.
	 * 
	 * this occurs if a player asks for a card and the player being asked
	 * does not have any of that cards rank.
	 * 
	 * player pulls a random card from the deck and brings it to their hand
	 */
	public void playerGoFish() {
		//draw random card from the shuffled deck
		Card fishedCard = deck.draw();
		//add to players hand
		players[whosTurn].addCard(fishedCard);
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
}

