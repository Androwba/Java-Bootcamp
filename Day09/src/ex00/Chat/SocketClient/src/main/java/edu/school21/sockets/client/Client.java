package edu.school21.sockets.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public void start(String host, int port) {
        try (Socket socket = new Socket(host, port)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));

            System.out.println(in.readLine());

            String command = consoleIn.readLine().trim();
            if (command.isEmpty() || !"signUp".equalsIgnoreCase(command)) {
                System.out.println("Did you mean 'signUp'? Proceeding with 'signUp'");
                command = "signUp";
            }
            out.println(command);

            System.out.print(in.readLine());
            out.println(consoleIn.readLine());
            System.out.print(in.readLine());
            out.println(consoleIn.readLine());
            System.out.println(in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
