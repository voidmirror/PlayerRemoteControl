package com.voidmirror.playerremotecontrol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static ServerSocket serverSocket;
    private static BufferedReader in;
    private final PlayerController playerController;

    public Server(PlayerController playerController) {
        this.playerController = playerController;
        try {
            serverSocket = new ServerSocket(4077);
            System.out.println("ServerSocket: " + serverSocket.toString());
        } catch (IOException e) {
            System.out.println("### ERROR ###: Server creating error");
            e.printStackTrace();
        }
        start();
    }

    public void start() {
        try {
            while (true) {
                System.out.println("# Waiting for new connection...");
                Socket socket = serverSocket.accept();
                System.out.println("Connected " + socket.toString());
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                while (true) {
                    System.out.println("# Waiting Input signal...");
                    String signal = in.readLine();
                    System.out.println("Input " + signal);
                    if (signal.equals("closeConnection")) {
                        break;
                    } else if (signal.equals("closeServer")) {
                        stopServer(socket);
                        return;
                    } else {
                        playerController.executeSignal(signal);
                    }

                }

                in.close();
                socket.close();

            }
        } catch (IOException e) {
            System.out.println("### ERROR ###: Server error");
            e.printStackTrace();
        }
    }

    public void stopServer(Socket socket) {
        try {
            in.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
