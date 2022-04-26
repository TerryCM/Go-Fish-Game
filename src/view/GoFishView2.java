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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.GoFishModel;

@SuppressWarnings({ "deprecation" })
public class GoFishView2 extends Application implements Observer {
	
	private String networkName;
	private GoFishController controller;
	static GoFishModel model;
	
	public GoFishView2 () {
		if (model == null) {
			model = new GoFishModel(4);
		}
	}
	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage stage) {
		Parameters parameters = getParameters();
        List<String> params = parameters.getRaw();
		
		networkName = params.get(0);
		
		if (networkName.equals("server")) {
			model.startGame();
		}
		System.out.println(model.getPlayers()[0].getHand());
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("test.fxml"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Scene scene = new Scene(root);
		stage.setScene(scene);
        stage.show();
    }
	
	public void update(Observable o, Object arg) {
		
	}
	
	private class ButtonClickHandler implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent me) {
			return;
		}
	}
	
}
