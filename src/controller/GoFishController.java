package controller;

import model.GoFishModel;
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
	
}
