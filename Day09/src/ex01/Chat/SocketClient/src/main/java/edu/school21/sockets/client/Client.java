package edu.school21.sockets.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private static final String RED = "\u001B[31m";
    private static final String RESET = "\u001B[0m";

    public void start(String host, int port) {
        try (Socket socket = new Socket(host, port)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));

            System.out.println(in.readLine());

            while (true) {
                System.out.print("Enter command (signUp or signIn): ");
                String command = consoleIn.readLine().trim();

                if ("signUp".equalsIgnoreCase(command) || "signIn".equalsIgnoreCase(command)) {
                    out.println(command);
                    break;
                } else {
                    System.out.println(RED + "Invalid command. Please enter 'signUp' or 'signIn'." + RESET);
                }
            }
            System.out.print(in.readLine());
            out.println(consoleIn.readLine());
            System.out.print(in.readLine());
            out.println(consoleIn.readLine());
            String serverResponse = in.readLine();
            System.out.println(serverResponse);

            if (serverResponse.equals("Start messaging")) {
                Thread readerThread = getThread(in, socket);
                String userMessage;
                while ((userMessage = consoleIn.readLine()) != null) {
                    out.println(userMessage);
                    if (userMessage.equalsIgnoreCase("Exit")) {
                        break;
                    }
                }
                socket.close();
                readerThread.join();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static Thread getThread(BufferedReader in, Socket socket) {
        Thread readerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String serverMessage;
                    while ((serverMessage = in.readLine()) != null) {
                        System.out.println(serverMessage);
                    }
                } catch (IOException e) {
                    if (!socket.isClosed()) {
                        e.printStackTrace();
                    }
                }
            }
        });
        readerThread.start();
        return readerThread;
    }
}
