package edu.school21.sockets.server;

import edu.school21.sockets.services.UsersService;
import org.springframework.stereotype.Component;
import edu.school21.sockets.exceptions.UserAlreadyExistsException;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

@Component
public class Server {

    private final UsersService usersService;

    public Server(UsersService usersService) {
        this.usersService = usersService;
    }

    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);
            try (Socket clientSocket = serverSocket.accept()) {
                System.out.println("Client connected");
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                out.println("Hello from Server!");

                String command = in.readLine();
                if ("signUp".equalsIgnoreCase(command)) {
                    out.println("Enter username:");
                    String username = in.readLine();
                    out.println("Enter password:");
                    String password = in.readLine();

                    try {
                        usersService.signUp(username, password);
                        out.println("Successful!");
                    } catch (UserAlreadyExistsException e) {
                        out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
