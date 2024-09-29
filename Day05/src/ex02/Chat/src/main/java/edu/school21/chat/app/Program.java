package edu.school21.chat.app;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.HikariCPDataSource;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Program {
    public static void main(String[] args) {
        DataSource dataSource = HikariCPDataSource.getDataSource();

        MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(dataSource);

        User creator = new User(2L, "user", "user", new ArrayList<>(), new ArrayList<>());
        Chatroom room = new Chatroom(3L, "room", creator, new ArrayList<>());
        Message message = new Message(null, creator, room, "Hello!", LocalDateTime.now());

        try {
            messagesRepository.save(message);
            System.out.println("Message ID: " + message.getId());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HikariCPDataSource.closeDataSource();
        }
    }
}
