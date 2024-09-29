package edu.school21.chat.repositories;

import edu.school21.chat.exceptions.NotSavedSubEntityException;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private final DataSource dataSource;

    public MessagesRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Message> findById(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT m.id, m.text, m.timestamp, u.id as author_id, u.login, u.password, c.id as room_id, c.name " +
                    "FROM messages m " +
                    "JOIN users u ON m.author_id = u.id " +
                    "JOIN chatroom c ON m.room_id = c.id " +
                    "WHERE m.id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                User author = new User(resultSet.getLong("author_id"), resultSet.getString("login"), resultSet.getString("password"), null, null);
                Chatroom room = new Chatroom(resultSet.getLong("room_id"), resultSet.getString("name"), null, null);
                Message message = new Message(
                        resultSet.getLong("id"),
                        author,
                        room,
                        resultSet.getString("text"),
                        resultSet.getTimestamp("timestamp").toLocalDateTime()
                );
                return Optional.of(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void save(Message message) {
        if (message.getAuthor() == null || message.getAuthor().getId() == null ||
                message.getRoom() == null || message.getRoom().getId() == null) {
            throw new NotSavedSubEntityException("Author and Room must have valid IDs");
        }

        try (Connection connection = dataSource.getConnection()) {
            String query = "INSERT INTO messages (author_id, room_id, text, timestamp) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, message.getAuthor().getId());
            statement.setLong(2, message.getRoom().getId());
            statement.setString(3, message.getText());
            statement.setTimestamp(4, java.sql.Timestamp.valueOf(message.getTimestamp()));

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating message failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    message.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating message failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            if ("23503".equals(e.getSQLState())) {
                throw new NotSavedSubEntityException("Foreign key constraint violation: " + e.getMessage());
            } else {
                e.printStackTrace(System.err);
            }
        }
    }
}
