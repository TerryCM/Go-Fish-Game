package view;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import controller.GoFishController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.GoFishModel;
import util.Message;

@SuppressWarnings({ "deprecation" })
public class GoFishView extends Application implements Observer {
	
	private static GoFishController controller;
	private static GoFishModel model;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private ArrayList<Thread> threads = new ArrayList<Thread>();
	private String networkName;
	private Stage stage;
	private Scene scene;

	public static void main(String[] args) {
		launch(args);
	}
	
	public void openStreams() throws IOException {
		
        ServerSocket ss = new ServerSocket(4000);
        
          
        // running infinite loop for getting
        // client request
        while (threads.size() < 3) 
        {
            Socket s = null;
              
            try 
            {
                // socket object to receive incoming client requests
                s = ss.accept();
                  
                System.out.println("A new client is connected : " + s);
                  
                // obtaining input and out streams
                
                ObjectOutputStream output = new ObjectOutputStream(s.getOutputStream());
                ObjectInputStream input = new ObjectInputStream(s.getInputStream());
                  
                System.out.println("Assigning new thread for this client");
  
                // create a new thread object
                Thread t = new ClientHandler(s, input, output);
  
                // Invoking the start() method
                t.start();
                threads.add(t);
                  
            }
            catch (Exception e){
                s.close();
                e.printStackTrace();
            }
        }
	}
	
	
	private static GoFishController getController() {
		return GoFishView.controller;
	}
	
	public void changeStage() {
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("test.fxml"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.scene = new Scene(root);
		stage.setScene(scene);
        stage.show();
		
	}
	/**
	 * Method to start the application and create the GUI
	 */
	public void start(Stage stage) {
		this.stage = stage;
		
		Parameters parameters = getParameters();
        List<String> params = parameters.getRaw();
		
		networkName = params.get(0);
		
		VBox vbox = new VBox();
		Button numPlayers = new Button("3 Players");
		numPlayers.setId("3");
		numPlayers.setOnMouseClicked(new ButtonClickHandler());
		
		if (networkName.equals("server")) {
			model = new GoFishModel(4);
			controller = new GoFishController(model);
			model.addObserver(this);
			controller.createPlayerDecks();
			vbox.getChildren().add(numPlayers);
			stage.setTitle("Server");
			Scene scene = new Scene(vbox, 800,600);

	        stage.setScene(scene);
	        stage.show();
	        System.out.println(getController());
		}
		
		if (networkName.equals("client")) {
			stage.setTitle("Client");
			System.out.println(controller);
			// getController().addObserver(this);
			
			Client c = new Client();
			try {
				c.main(null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				c.getOut().writeObject(new Message("model"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.input = c.getIn();
			this.output = c.getOut();
			changeStage();
		}
    }
	
	/**
	 * method to update the GUI when the model is changed
	 * @param o the observable object
	 * @param arg the changes to be made
	 */
	public void update(Observable o, Object arg) {
		Label ourDeck = (Label) this.scene.lookup("#ourDeck");
		ourDeck.setText("test");
	}
	
	private class ButtonClickHandler implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent me) {
			Button but = (Button) me.getSource();
			if (networkName.equals("server")) {
				but.setVisible(false);
				changeStage();
				try {
					openStreams();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public class Client 
	{
		private static ObjectInputStream in;
		private static ObjectOutputStream out;
		
	    public static void main(String[] args) throws IOException 
	    {
	        try
	        {
	        	
	            // establish the connection with server port
	            Socket s = new Socket("localhost", 4000);
	      
	            // obtaining input and out streams
	            in = new ObjectInputStream(s.getInputStream());
	            out = new ObjectOutputStream(s.getOutputStream());
	            out.writeObject(new Message("started"));
	            
	        }catch(Exception e){
	            e.printStackTrace();
	        }
	    }
	    
	    public ObjectInputStream getIn() {
	    	return Client.in;
	    }
	    
	    public ObjectOutputStream getOut() {
	    	return Client.out;
	    }
	}
	
	class ClientHandler extends Thread 
	{
	    final ObjectInputStream in;
	    final ObjectOutputStream out;
	    final Socket s;
	      
	  
	    // Constructor
	    public ClientHandler(Socket s, ObjectInputStream in, ObjectOutputStream out) 
	    {
	        this.s = s;
	        this.in = in;
	        this.out = out;
	    }
	    
	    public ObjectOutputStream getOut() {
	    	return this.out;
	    }
	    
	    public ObjectInputStream getIn() {
	    	return this.in;
	    }
	    
	    @Override
	    public void run(){
			try {
				Message msg;
				do {
					msg = (Message)this.in.readObject();
					System.out.println(networkName);
					System.out.println(msg.toString());
					// controller.handleMessage(msg);
					if (msg.equals("exit")) {
						break;
					}
					
					if (msg.toString().equals("model")) {
						this.out.writeObject(new Message("here"));;
					}
				} while (true);
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			try
	        {
	            // closing resources
	            this.in.close();
	            this.out.close();
	              
	        }catch(IOException e){
	            e.printStackTrace();
	        }
			
		}
	  
	}
	
}
