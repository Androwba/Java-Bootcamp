package edu.school21.sockets.models;

import java.util.Objects;

public class Message {
    private Long id;
    private String sender;
    private String text;
    private String timestamp;

    public Message() {}

    public Message(String sender, String text) {
        this.sender = sender;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", sender='" + sender + '\'' +
                ", text='" + text + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
