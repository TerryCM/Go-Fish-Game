package view;

import java.io.IOException;
import java.util.*;

import controller.GoFishController;
import javafx.application.Application;
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
import util.Card;
import util.Deck;
import util.GoFishPlayer;

@SuppressWarnings({ "deprecation" })
public class GoFishView extends Application implements Observer {
	
	private GoFishController controller;
	public GoFishModel model;
	private Parent root;
	private Integer playerNum;

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
		
		model = new GoFishModel(playerNum);
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
		for (ImageView i:controller.getDeckImages()) {
			if (i != null) {
				i.setFitHeight(48);
				i.setFitWidth(200);
				i.setPreserveRatio(true);
				ourHBox.getChildren().add(i);
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
		
		Label ourDeck = (Label) root.lookup("#ourDeck");
		String updateDeck = controller.getOurCurrentHand();
		ourDeck.setText(updateDeck);

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
			return;
		}
	}
}
