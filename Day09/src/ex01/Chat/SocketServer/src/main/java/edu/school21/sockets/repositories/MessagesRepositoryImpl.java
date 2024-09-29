package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Message;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MessagesRepositoryImpl implements CrudRepository<Message> {
    private final JdbcTemplate jdbcTemplate;
    private final BeanPropertyRowMapper<Message> messageRowMapper = new BeanPropertyRowMapper<>(Message.class);

    public MessagesRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Message message) {
        jdbcTemplate.update("INSERT INTO messages (sender, text) VALUES (?, ?)",
                message.getSender(), message.getText());
    }

    @Override
    public void update(Message message) {
        jdbcTemplate.update("UPDATE messages SET sender = ?, text = ? WHERE id = ?",
                message.getSender(), message.getText(), message.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM messages WHERE id = ?", id);
    }

    @Override
    public List<Message> findAll() {
        return jdbcTemplate.query("SELECT * FROM messages", messageRowMapper);
    }

    @Override
    public Optional<Message> findById(Long id) {
        List<Message> messages = jdbcTemplate.query("SELECT * FROM messages WHERE id = ?",
                messageRowMapper, id);
        return messages.stream().findFirst();
    }
}
