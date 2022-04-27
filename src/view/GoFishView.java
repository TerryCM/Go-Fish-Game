package view;

import java.io.IOException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import controller.GoFishController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.GoFishModel;

@SuppressWarnings({ "deprecation" })
public class GoFishView extends Application implements Observer {
	
	private String networkName;
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
			root = FXMLLoader.load(getClass().getResource("mainScene.fxml"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
		model = new GoFishModel(playerNum);
		model.addObserver(this);
		controller = new GoFishController(model);
		Parameters parameters = getParameters();
        List<String> params = parameters.getRaw();
		
		//networkName = params.get(0);
    }
	
	public void update(Observable o, Object arg) {

		Label ourDeck = (Label) root.lookup("#ourDeck");
		String updateDeck = controller.getOurCurrentHand();
		ourDeck.setText(updateDeck);

		Label leftDeck = (Label) root.lookup("#leftPlayerDeck");
		String leftUpdate = controller.getPlayerDeckCount(2);
		leftDeck.setText(leftUpdate);

		Label topDeck = (Label) root.lookup("#topPlayerDeck");
		String topUpdate = controller.getPlayerDeckCount(3);
		topDeck.setText(topUpdate);

		Label rightDeck = (Label) root.lookup("#rightPlayerDeck");
		String rightUpdate = controller.getPlayerDeckCount(4);
		rightDeck.setText(rightUpdate);

		Label cardsLeft = (Label) root.lookup("#cardsLeft");
		String cardsLeftUpdate = controller.getCardsLeft();
		cardsLeft.setText(cardsLeftUpdate);
		
	}
	
	private class ButtonClickHandler implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent me) {
			return;
		}
	}
	
}
