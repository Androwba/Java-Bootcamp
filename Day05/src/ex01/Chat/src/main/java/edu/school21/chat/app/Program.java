package edu.school21.chat.app;

import edu.school21.chat.models.Message;
import edu.school21.chat.repositories.HikariCPDataSource;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import java.util.Optional;
import java.util.Scanner;
import javax.sql.DataSource;

public class Program {
    public static void main(String[] args) {
        DataSource dataSource = null;
        try {
            dataSource = HikariCPDataSource.getDataSource();
            Scanner scanner = new Scanner(System.in);

            MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(dataSource);

            System.out.println("Enter a message ID:");
            Long id = scanner.nextLong();

            Optional<Message> message = messagesRepository.findById(id);
            if (message.isPresent()) {
                System.out.println(message.get());
            } else {
                System.out.println("Message not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HikariCPDataSource.closeDataSource();
        }
    }
}
