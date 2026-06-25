package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerMain {

    private static final int PORT = 9000;

    public static void main(String[] args) {

        ExecutorService pool = Executors.newFixedThreadPool(10);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            System.out.println("SERVER STARTED ON PORT " + PORT);

            while (true) {

                Socket client = serverSocket.accept();

                System.out.println("Client connected: " + client.getInetAddress());

                pool.execute(new ClientHandler(client));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}