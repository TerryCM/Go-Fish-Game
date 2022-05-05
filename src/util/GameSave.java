package util;

import java.io.Serializable;

public class GameSave implements Serializable {

	private int whosTurn;
	private boolean gameOver;
	private GoFishPlayer[] players;
	private Deck deck;
	
	public GameSave(int whosTurn, boolean gameOver, GoFishPlayer[] players, Deck deck) {
		// TODO Auto-generated constructor stub
		this.whosTurn = whosTurn;
		this.gameOver = gameOver;
		this.players = players;
		this.deck = deck;
	}

	public GoFishPlayer[] getPlayers() {
		return this.players;
	}
}
