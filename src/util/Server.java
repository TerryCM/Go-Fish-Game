package util;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private final ServerSocket serverSocket;
    private ArrayList<Thread> clients; 

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        this.clients = new ArrayList<Thread>();
    }

    public void createServer() {
        try {
            
            while (!serverSocket.isClosed() && clients.size() < 2) {
                // Will be closed in the Client Handler.
                Socket socket = serverSocket.accept();
                System.out.println("A new client has connected!");
                ClientHandler clientHandler = new ClientHandler(socket, clients.size());
                Thread thread = new Thread(clientHandler);
                clients.add(thread);
                thread.start();
                System.out.println(clients);
            }
        } catch (IOException e) {
            closeServerSocket();
        }
    }


    public void closeServerSocket() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(4000);
        Server server = new Server(serverSocket);
        server.createServer();
    }

}
