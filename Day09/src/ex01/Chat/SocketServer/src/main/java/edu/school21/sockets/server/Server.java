package edu.school21.sockets.server;

import edu.school21.sockets.services.UsersService;
import edu.school21.sockets.repositories.MessagesRepositoryImpl;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

@Component
public class Server {
    private final UsersService usersService;
    private final MessagesRepositoryImpl messagesRepository;
    private final Set<ClientHandler> clientHandlers = new HashSet<>();

    public Server(UsersService usersService, MessagesRepositoryImpl messagesRepository) {
        this.usersService = usersService;
        this.messagesRepository = messagesRepository;
    }

    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);
            while (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket, usersService, messagesRepository, clientHandlers);
                clientHandlers.add(clientHandler);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
