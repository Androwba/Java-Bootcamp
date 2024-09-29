package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UsersRepositoryJdbcImpl implements UsersRepository {
    private final DataSource dataSource;

    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<User> findAll(int page, int size) {
        String query = """
            WITH users_paged AS (
                SELECT *
                FROM users
                ORDER BY id
                LIMIT ? OFFSET ?
            ),
            created_rooms AS (
                SELECT u.id AS user_id, c.id AS room_id, c.name
                FROM users_paged u
                LEFT JOIN chatroom c ON u.id = c.owner_id
            ),
            participant_rooms AS (
                SELECT u.id AS user_id, c.id AS room_id, c.name 
                FROM users_paged u
                LEFT JOIN chatroom_members cm ON u.id = cm.user_id
                LEFT JOIN chatroom c ON cm.chatroom_id = c.id
            )
            SELECT
                u.id, u.login, u.password,
                cr.room_id AS created_room_id, cr.name AS created_room_name, 
                pr.room_id AS participant_room_id, pr.name AS participant_room_name
            FROM users_paged u
            LEFT JOIN created_rooms cr ON u.id = cr.user_id 
            LEFT JOIN participant_rooms pr ON u.id = pr.user_id 
            ORDER BY u.id;
        """;
        List<User> users = new ArrayList<>();
        Map<Long, User> userMap = new HashMap<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, size);
            statement.setInt(2, page * size);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                long userId = resultSet.getLong("id");
                User user = userMap.getOrDefault(userId, new User(userId, resultSet.getString("login"), resultSet.getString("password"), new ArrayList<>(), new ArrayList<>()));
                userMap.putIfAbsent(userId, user);

                long createdRoomId = resultSet.getLong("created_room_id");
                if (createdRoomId > 0) {
                    user.getCreatedRooms().add(new Chatroom(createdRoomId, resultSet.getString("created_room_name"), null, null));
                }

                long participantRoomId = resultSet.getLong("participant_room_id");
                if (participantRoomId > 0) {
                    user.getChatrooms().add(new Chatroom(participantRoomId, resultSet.getString("participant_room_name"), null, null));
                }
            }
            users.addAll(userMap.values());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
