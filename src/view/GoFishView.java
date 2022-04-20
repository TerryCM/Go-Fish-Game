package view;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Observable;
import java.util.Observer;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import util.Message;

@SuppressWarnings("deprecation")
public class GoFishView extends Application implements Observer{
	
	/**
	 * representations of an input and output stream
	 */
	private ObjectOutputStream output;
	private ObjectInputStream input;

	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * Method to start the application and create the GUI
	 */
	public void start(Stage stage) {
		
        Scene scene = new Scene(new VBox(), 800, 600);
        /* EventHandler<KeyEvent> handler = new SceneKeyHandle(); 
           scene.setOnKeyReleased(handler); */
        
        Thread netThread = new NetworkingThread();
        netThread.start();
        
        stage.setScene(scene);
        stage.show();
    }
	
	/**
	 * method to update the GUI when the model is changed
	 * @param o the observable object
	 * @param arg the changes to be made
	 */
	public void update(Observable o, Object arg) {
		
	}
	
	/**
	 * Method to handle the key pressed event
	 */
	private class SceneKeyHandle implements EventHandler<KeyEvent> {
		@Override
		public void handle(KeyEvent ke) {
			return;
		}
	}
	
	/**
	 * method to handle the network input
	 */
	public class NetworkingThread extends Thread {
		
		public void run() {
			try {
				Message msg;
				do {
					msg = (Message)input.readObject();
					
				} while (true);
				
				/* input.close(); */
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
		}
	}

}
