package edu.school21.chat.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Message {
    private Long id;
    private User author;
    private Chatroom room;
    private String text;
    private LocalDateTime timestamp;

    public Message(Long id, User author, Chatroom room, String text, LocalDateTime timestamp) {
        this.id = id;
        this.author = author;
        this.room = room;
        this.text = text;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Chatroom getRoom() {
        return room;
    }

    public void setRoom(Chatroom room) {
        this.room = room;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(id, message.id) &&
                Objects.equals(author, message.author) &&
                Objects.equals(room, message.room) &&
                Objects.equals(text, message.text) &&
                Objects.equals(timestamp, message.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, room, text, timestamp);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
        return "Message : {\n" +
                "  id=" + id + ",\n" +
                "  author=" + (author != null ? author.toString() : "null") + ",\n" +
                "  room=" + (room != null ? room.toString() : "null") + ",\n" +
                "  text=\"" + text + "\",\n" +
                "  dateTime=" + (timestamp != null ? timestamp.format(formatter) : "null") + "\n" +
                "}";
    }
}
