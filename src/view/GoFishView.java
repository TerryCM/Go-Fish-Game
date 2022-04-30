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
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.GoFishModel;

@SuppressWarnings({ "deprecation" })
public class GoFishView extends Application implements Observer {
	
	private String networkName;
	private GoFishController controller;
	static GoFishModel model;
	private Parent root;
	
	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage stage) {
		
		try {
			root = FXMLLoader.load(getClass().getResource("mainScene2.fxml"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Scene scene = new Scene(root);
		stage.setResizable(false);
		stage.resizableProperty().setValue(Boolean.FALSE);
		//stage.initStyle(StageStyle.UTILITY);
		stage.setScene(scene);
		stage.show();
		
		model = new GoFishModel(4);
		model.addObserver(this);
		controller = new GoFishController(model);
		controller.createPlayerDecks();
		Parameters parameters = getParameters();
        List<String> params = parameters.getRaw();
		
		networkName = params.get(0);
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
			System.out.println("Clicked by "+ me.getSource());
		}
	}
	
}
