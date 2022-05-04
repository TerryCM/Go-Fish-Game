package view;

import java.io.IOException;
import java.util.*;

import controller.GoFishController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import model.GoFishModel;
import util.*;

@SuppressWarnings({ "deprecation" })
public class GoFishView extends Application implements Observer {
	
	private GoFishController controller;
	public GoFishModel model;
	private Parent root;
	private Integer playerNum;
	private boolean ais;
	private int startingHandSize;

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage stage) {
		Alert a = new Alert(AlertType.NONE);
		a.setTitle("Choose number of players");
		ButtonType b2 = new ButtonType("2", ButtonData.OK_DONE);
		a.getDialogPane().getButtonTypes().add(b2);
		ButtonType b3 = new ButtonType("3", ButtonData.OK_DONE);
		a.getDialogPane().getButtonTypes().add(b3);
		ButtonType b4 = new ButtonType("4", ButtonData.OK_DONE);
		a.getDialogPane().getButtonTypes().add(b4);
		a.showAndWait().ifPresent(response -> {
		    playerNum = Integer.valueOf(response.getText());
		});
		
		Alert a2 = new Alert(AlertType.NONE);
		a2.setTitle("Would you like to battle AIs?");
		ButtonType by = new ButtonType("Yes", ButtonData.OK_DONE);
		a2.getDialogPane().getButtonTypes().add(by);
		ButtonType bn = new ButtonType("No", ButtonData.OK_DONE);
		a2.getDialogPane().getButtonTypes().add(bn);
		a2.showAndWait().ifPresent(response -> {
			if ("No".equals(response.getText())) {
				ais = false;
			} else if ("Yes".equals(response.getText())) {
				ais = true;
			}
		 });
		
		Alert a3 = new Alert(AlertType.NONE);
		a3.setTitle("What starting hand size would you like?");
		ButtonType b5 = new ButtonType("5", ButtonData.OK_DONE);
		a3.getDialogPane().getButtonTypes().add(b5);
		ButtonType b7 = new ButtonType("7", ButtonData.OK_DONE);
		a3.getDialogPane().getButtonTypes().add(b7);
		ButtonType b9 = new ButtonType("9", ButtonData.OK_DONE);
		a3.getDialogPane().getButtonTypes().add(b9);
		a3.showAndWait().ifPresent(response -> {
			startingHandSize = Integer.valueOf(response.getText());
		 });
		try {
			root = FXMLLoader.load(getClass().getResource("mainScene2.fxml"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Scene scene = new Scene(root);
		stage.setResizable(false);
		stage.resizableProperty().setValue(Boolean.FALSE);
		stage.setScene(scene);
		stage.show();

		// Replace this values with the ones retrieved from the user
		boolean multiDeck = true;
		int numDecks = 1;
		model = new GoFishModel(playerNum, ais, multiDeck, numDecks, startingHandSize);
		model.addObserver(this);
		controller = new GoFishController(model);
		controller.createDecks();
		
		// Parameters parameters = getParameters();
        // List<String> params = parameters.getRaw();
		
		//networkName = params.get(0);
    }
	
	public void update(Observable o, Object arg) {
		
		TextFlow tf = (TextFlow) root.lookup("#textFlow");
		tf.getChildren().clear();
		
		HBox ourHBox = (HBox) root.lookup("#ourHbox");
		ourHBox.getChildren().clear();
		if (!ais || controller.getWhosTurn() == 0) {
			for (ImageView i:controller.getDeckImages()) {
				if (i != null) {
					i.setFitHeight(48);
					i.setFitWidth(200);
					i.setPreserveRatio(true);
					ourHBox.getChildren().add(i);
				}
	
			}
		}
		
		Button nextTurn = (Button) root.lookup("#nextTurn");
		nextTurn.setOnMouseClicked(new NextTurnButtonClickHandler());
		
		Button leftAsk = (Button) root.lookup("#askLeft");
		leftAsk.setOnMouseClicked(new ButtonClickHandler());
		
		Button topAsk = (Button) root.lookup("#askTop");
		topAsk.setOnMouseClicked(new ButtonClickHandler());
		
		Button rightAsk = (Button) root.lookup("#askRight");
		rightAsk.setOnMouseClicked(new ButtonClickHandler());
		
		MenuBar menu = (MenuBar) root.lookup("#menuBar");
		
		menu.getMenus().get(0).getItems().get(0).setOnAction(new LoadGameHandler());
		menu.getMenus().get(0).getItems().get(1).setOnAction(new SaveGameHandler());
		
		
		
		if (!ais || controller.getWhosTurn() == 0) {
			Label ourDeck = (Label) root.lookup("#ourDeck");
			String updateDeck = controller.getOurCurrentHand();
			ourDeck.setText(updateDeck);
		}

		Label leftDeck = (Label) root.lookup("#leftPlayerDeck");
		String leftUpdate = controller.getPlayerDeckCount("left");
		leftDeck.setText(leftUpdate);

		Label topDeck = (Label) root.lookup("#topPlayerDeck");
		String topUpdate = controller.getPlayerDeckCount("top");
		topDeck.setText(topUpdate);

		Label rightDeck = (Label) root.lookup("#rightPlayerDeck");
		String rightUpdate = controller.getPlayerDeckCount("right");
		rightDeck.setText(rightUpdate);

		Label cardsLeft = (Label) root.lookup("#cardsLeft");
		String cardsLeftUpdate = controller.getCardsLeft();
		cardsLeft.setText(cardsLeftUpdate);
		
		Label ourStack = (Label) root.lookup("#ourStack");
		String ourStackUpdate = controller.getPlayerBookCount("ours");
		ourStack.setText(ourStackUpdate);
		
		Label rightStack = (Label) root.lookup("#rightPlayerStack");
		String rightStackUpdate = controller.getPlayerBookCount("right");
		rightStack.setText(rightStackUpdate);
		
		Label topStack = (Label) root.lookup("#topPlayerStack");
		String topStackUpdate = controller.getPlayerBookCount("top");
		topStack.setText(topStackUpdate);
		
		Label leftStack = (Label) root.lookup("#leftPlayerStack");
		String leftStackUpdate = controller.getPlayerBookCount("left");
		leftStack.setText(leftStackUpdate);
		
		Label ourName = (Label) root.lookup("#ourName");
		String ourNameUpdate = controller.getPlayerName("ours");
		ourName.setText(ourNameUpdate);
		
		Label rightName = (Label) root.lookup("#rightName");
		String rightNameUpdate = controller.getPlayerName("right");
		rightName.setText(rightNameUpdate);
		
		Label topName = (Label) root.lookup("#topName");
		String topNameUpdate = controller.getPlayerName("top");
		topName.setText(topNameUpdate);
		
		Label leftName = (Label) root.lookup("#leftName");
		String leftNameUpdate = controller.getPlayerName("left");
		leftName.setText(leftNameUpdate);

		
	}
	
	private class ButtonClickHandler implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent me) {
			if (!controller.isTurnOver()) {
				String playerAsked = ((Button) me.getSource()).getId();
				
				int[] playerNum = new int[4];
				playerNum[0] = 0;
				if (playerAsked.equals("askRight")) {
					playerNum[0] = (controller.getWhosTurn() + 3) % controller.getNumberOfPlayers();
				} else if (playerAsked.equals("askTop")) {
					playerNum[0] = (controller.getWhosTurn() + 2) % controller.getNumberOfPlayers();
				} else if (playerAsked.equals("askLeft")) {
					playerNum[0] = (controller.getWhosTurn() + 1) % controller.getNumberOfPlayers();
				}

				Alert a = new Alert(AlertType.NONE);
				a.setTitle("Choose a card to ask for:");
				int whosturn = controller.getWhosTurn();
				for (Card c:controller.getPlayers()[whosturn].getHand()) {
					a.getDialogPane().getButtonTypes().add(new ButtonType(c.getRank(), ButtonData.OK_DONE));
				}
				boolean[] hasRank = new boolean[2];
				a.showAndWait().ifPresent(response -> {
				    hasRank[0] = controller.makeGuess(response.getText(), playerNum[0]);
				 // lets ai's track moves
					for (GoFishPlayer p: controller.getPlayers()) {
						if (p instanceof GoFishAi && p.getPlayerNumber() != whosturn) {
							GoFishAi tempai = (GoFishAi) p;
							tempai.addOpposingMove(response.getText(), whosturn);
							if (hasRank[0]) {
								tempai.removeOpposingMove(response.getText(), playerNum[0]);
							}
						}
					}
				 });
				
				TextFlow tf = (TextFlow) root.lookup("#textFlow");
				Text text1;
				if (hasRank[0]) {
					// check if the gam
					if(controller.isGameOver()){
						// check number of books for every player.
						GoFishPlayer winner = null;
						for (int i = 0; i < controller.getNumberOfPlayers(); i++) {
							if (controller.getPlayers()[i].getNumberOfBooks() > controller.getPlayers()[whosturn].getNumberOfBooks()) {
								winner = controller.getPlayers()[i];
							}
						}

						text1 = new Text("Game Over! " + winner.getName() + " wins!");


					}else{
						text1 = new Text("Player has that rank. Adding to your hand. Ask again!");
					}


				} else {
					text1 = new Text("Player does not have that rank. Gone Fishing for you! Click next turn!");
					Button nextTurn = (Button) root.lookup("#nextTurn");
					nextTurn.setVisible(true);
					controller.setTurnOver(true);
				}
			    text1.setFill(Color.RED);
			    text1.setFont(Font.font("Helvetica", FontWeight.BOLD, 12));
			    tf.getChildren().add(text1);
				
			}
			
			return;
		}
	}
	
	private class NextTurnButtonClickHandler implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent me) {
			controller.changeTurn();
			controller.setTurnOver(false);
			Button nextTurn = (Button) root.lookup("#nextTurn");
			nextTurn.setVisible(false);
			if (controller.getPlayers()[controller.getWhosTurn()] instanceof GoFishAi) {
				manageAiTurn();
			}
			return;
		}
	}
	
	private class LoadGameHandler implements EventHandler<ActionEvent> {
		
		@Override
		public void handle(ActionEvent ae) {
			controller.loadGame();
		}
	}
	
private class SaveGameHandler implements EventHandler<ActionEvent> {
		
		@Override
		public void handle(ActionEvent ae) {
			controller.saveGame();
		}
	}

	public void manageAiTurn() {
		int whosturn = controller.getWhosTurn();
		boolean hasRank;
		
		GoFishAi ai = (GoFishAi) controller.getPlayers()[whosturn];
		int index = ai.checkOpposingCards();
		int playerToAsk;
		String rankAsked;
		if (index != -1) {
			playerToAsk = ai.getOpponentNum(index);
			rankAsked = ai.getOpposingCard(index);
			ai.removeOpposingCard(index);
		} else {
			ArrayList<Card> hand = ai.getHand();
			Random rand = new Random();
			do {
			playerToAsk = rand.nextInt(controller.getNumberOfPlayers());
			} while (playerToAsk == whosturn);
			int  rand_int = rand.nextInt(hand.size());
			rankAsked = hand.get(rand_int).getRank();
		}
		
		hasRank = controller.makeGuess(rankAsked, playerToAsk);
		
		// lets ai's track moves
		for (GoFishPlayer p: controller.getPlayers()) {
			if (p instanceof GoFishAi && p.getPlayerNumber() != whosturn) {
				GoFishAi tempai = (GoFishAi) p;
				tempai.addOpposingMove(rankAsked, whosturn);
				if (hasRank) {
					tempai.removeOpposingMove(rankAsked, playerToAsk);
				}
			}
		}
		TextFlow tf = (TextFlow) root.lookup("#textFlow");
		Text text1;
		if (hasRank) {
			// check if the game is over
			if(controller.isGameOver()){
				// check number of books for every player.
				GoFishPlayer winner = null;
				for (int i = 0; i < controller.getNumberOfPlayers(); i++) {
					if (controller.getPlayers()[i].getNumberOfBooks() > controller.getPlayers()[whosturn].getNumberOfBooks()) {
						winner = controller.getPlayers()[i];
					}
				}

				text1 = new Text("Game Over! " + winner.getName() + " wins!");


			}else{
				text1 = new Text(" Got " + rankAsked + " from player " + (playerToAsk+1) + ".");
				manageAiTurn();
			}


		} else {
			text1 = new Text("Player " + (whosturn+1) + "'s turn. Player " +(playerToAsk+1) + " didn't have " + rankAsked + ". Click next turn!");
			Button nextTurn = (Button) root.lookup("#nextTurn");
			nextTurn.setVisible(true);
			controller.setTurnOver(true);
		}
	    text1.setFill(Color.RED);
	    text1.setFont(Font.font("Helvetica", FontWeight.BOLD, 12));
	    tf.getChildren().add(text1);
	}
}
