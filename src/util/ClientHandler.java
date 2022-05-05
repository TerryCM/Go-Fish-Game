package util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private int playerNumber;

    // Creating the client handler from the socket the server passes.
    public ClientHandler(Socket socket, int playerNumber) {
        try {
            this.socket = socket;
            this.input = new ObjectInputStream(socket.getInputStream());
            this.output = new ObjectOutputStream(socket.getOutputStream());
            this.playerNumber = playerNumber;
        } catch (IOException e) {
            closeEverything(socket, input, output);
        }
    }

    @Override
    public void run() {
        Message msg;
        while (socket.isConnected()) {
            try {
                msg = (Message) this.input.readObject();
                System.out.println("Client Handler got " + msg.toString());
                sendToServer(msg);
            } catch (IOException e) {
                closeEverything(socket, this.input, this.output);
                break;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendToServer(Message msg) {
        try {
            output.writeObject(msg);
            output.flush();
        } catch (IOException e) {
            closeEverything(socket, input, output);
        }
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
}
