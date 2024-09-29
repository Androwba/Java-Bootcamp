package edu.school21.sockets.server;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.repositories.MessagesRepositoryImpl;
import edu.school21.sockets.services.UsersService;
import edu.school21.sockets.exceptions.InvalidCredentialsException;
import edu.school21.sockets.exceptions.UserAlreadyExistsException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Set;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final UsersService usersService;
    private final MessagesRepositoryImpl messagesRepository;
    private final Set<ClientHandler> clientHandlers;
    private BufferedReader in;
    private PrintWriter out;
    private String username;
    private boolean isConnectionClosed = false;

    public ClientHandler(Socket clientSocket, UsersService usersService, MessagesRepositoryImpl messagesRepository, Set<ClientHandler> clientHandlers) {
        this.clientSocket = clientSocket;
        this.usersService = usersService;
        this.messagesRepository = messagesRepository;
        this.clientHandlers = clientHandlers;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            out.println("Hello from Server!");

            String command = in.readLine().trim();
            System.out.println("Received command: " + command);
            if ("signUp".equalsIgnoreCase(command)) {
                signUp();
            } else if ("signIn".equalsIgnoreCase(command)) {
                signIn();
            } else {
                out.println("Unknown command. Closing connection.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            clientHandlers.remove(this);
            closeConnection();
        }
    }

    private void signUp() throws IOException {
        out.println("Enter username: ");
        String username = in.readLine().trim();
        out.println("Enter password: ");
        String password = in.readLine().trim();

        if (username.isEmpty() || password.isEmpty()) {
            out.println("Neither the Username nor Password can be empty.");
            System.out.println("Registration failed: Username or password was empty.");
            return;
        }

        try {
            usersService.signUp(username, password);
            out.println("Registration successful! You can now sign in.");
            System.out.println("User registered: " + username);
        } catch (UserAlreadyExistsException e) {
            out.println(e.getMessage());
            System.out.println("Registration failed: " + e.getMessage());
        }
    }

    private void signIn() throws IOException {
        out.println("Enter username: ");
        String username = in.readLine().trim();
        out.println("Enter password: ");
        String password = in.readLine().trim();

        try {
            usersService.signIn(username, password);
            this.username = username;
            out.println("Start messaging");
            System.out.println("User signed in: " + username);
            handleMessaging();
        } catch (InvalidCredentialsException e) {
            out.println(e.getMessage());
            System.out.println("Sign-in failed: " + e.getMessage());
        }
    }

    private void handleMessaging() {
        String message;
        try {
            while ((message = in.readLine()) != null) {
                if (message.equalsIgnoreCase("Exit")) {
                    out.println("You have left the chat.");
                    break;
                }
                Message msg = new Message(username, message);
                messagesRepository.save(msg);
                broadcastMessage(msg);
            }
        } catch (IOException e) {
            if (!clientSocket.isClosed()) {
                e.printStackTrace();
            }
        }
    }

    private void broadcastMessage(Message message) {
        for (ClientHandler client : clientHandlers) {
            if (client != this) {
                client.out.println(message.getSender() + ": " + message.getText());
            } else {
                client.out.println("You: " + message.getText());
            }
        }
        System.out.println("Broadcasted message from " + message.getSender() + ": " + message.getText());
    }

    private synchronized void closeConnection() {
        if (isConnectionClosed) {
            return;
        }
        isConnectionClosed = true;
        try {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
            }
            if (username != null) {
                System.out.println("Connection closed for user: " + username);
                broadcastMessage(new Message("Server", username + " has left the chat."));
            } else {
                System.out.println("Connection closed for a new user.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
