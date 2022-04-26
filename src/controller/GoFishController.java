package controller;

import java.util.ArrayList;

import javafx.scene.control.Label;
import model.GoFishModel;
import util.Card;
import view.GoFishView;

public class GoFishController {
	
	private GoFishModel model;
	
	/**
	 * Constructor for GoFishController
	 * @param model
	 */
	public GoFishController(GoFishModel model) {
		this.model = model;
	}

	public void addObserver(GoFishView goFishView) {
		this.model.addObserver(goFishView);
	}

	public void createPlayerDecks() {
		this.model.startGame();
	}

	public String getOurCurrentHand() {
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
