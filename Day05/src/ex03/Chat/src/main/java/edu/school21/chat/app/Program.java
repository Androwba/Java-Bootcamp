package edu.school21.chat.app;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.exceptions.NotSavedSubEntityException;
import edu.school21.chat.repositories.HikariCPDataSource;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import java.util.Optional;
import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Program {
    public static void main(String[] args) {
        DataSource dataSource = HikariCPDataSource.getDataSource();
        MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(dataSource);

        Optional<Message> messageOptional = messagesRepository.findById(1L);
        if (messageOptional.isPresent()) {
            Message message = messageOptional.get();
            message.setText("Updated text");
            message.setTimestamp(null);
//          User nonExistingAuthor = new User(999L, "nonexistent", "nonexistent", new ArrayList<>(), new ArrayList<>());
//          message.setAuthor(nonExistingAuthor);
            try {
                messagesRepository.update(message);
                System.out.println("Message updated successfully. ID: " + message.getId());
            } catch (NotSavedSubEntityException e) {
                System.err.println("Caught NotSavedSubEntityException: " + e.getMessage());
            }
        } else {
            System.out.println("Message not found");
        }
        HikariCPDataSource.closeDataSource();
    }
}
