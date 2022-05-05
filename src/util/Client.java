package util;

import java.io.*;
import java.net.Socket;

public class Client {

    
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private String playerNum;

    public Client(Socket socket, String num) {
    	this.socket = socket;
        this.playerNum = num;
        try {
        	this.output = new ObjectOutputStream(socket.getOutputStream());
        	this.output.flush();
            this.input = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            closeEverything(socket, input, output);
        }
    }

    public void sendMessage() {
        try {
            if (socket.isConnected()) {
                output.writeObject(new Message("test"));
                output.flush();
            }
        } catch (IOException e) {
            closeEverything(socket, input, output);
        }
    }

    public void listenForMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg;
                while (socket.isConnected()) {
                    try {
                        msg = (Message) input.readObject();
                        System.out.println("Client got: " + msg.toString());
                    } catch (IOException | ClassNotFoundException e) {
                        closeEverything(socket, input, output);
                    }
                }
            }
        }).start();
    }

    public void closeEverything(Socket socket, ObjectInputStream input, ObjectOutputStream output) {
        try {
            if (input != null) {
                input.close();
            }
            if (output != null) {
                output.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("localhost", 4000);

        Client client = new Client(socket, "1");
       
        client.listenForMessage();
        client.sendMessage();
    }
}
