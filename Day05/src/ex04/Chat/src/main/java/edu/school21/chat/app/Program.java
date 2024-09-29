package edu.school21.chat.app;

import edu.school21.chat.models.User;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.repositories.UsersRepository;
import edu.school21.chat.repositories.UsersRepositoryJdbcImpl;
import edu.school21.chat.repositories.HikariCPDataSource;

import javax.sql.DataSource;
import java.util.List;

public class Program {
    public static void main(String[] args) {
        DataSource dataSource = HikariCPDataSource.getDataSource();
        UsersRepository usersRepository = new UsersRepositoryJdbcImpl(dataSource);
        int page = 0;
        int size = 5;
        List<User> users = usersRepository.findAll(page, size);
        for (User user : users) {
            System.out.println("User: " + user.getId() + ", " + user.getLogin());
            System.out.println("Created Rooms:");

            for (Chatroom room : user.getCreatedRooms()) {
                System.out.println("  - " + room.getId() + ": " + room.getName());
            }
            System.out.println("Chatrooms:");

            for (Chatroom room : user.getChatrooms()) {
                System.out.println("  - " + room.getId() + ": " + room.getName());
            }
            System.out.println();
        }
        HikariCPDataSource.closeDataSource();
    }
}
