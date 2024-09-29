package edu.school21.sockets.app;

import edu.school21.sockets.config.SocketsApplicationConfig;
import edu.school21.sockets.server.Server;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1 || !args[0].startsWith("--port=")) {
            System.err.println("Usage: java -jar target/SocketServer-1.0-SNAPSHOT-jar-with-dependencies.jar --port=<port>");
            System.exit(1);
        }

        int port = Integer.parseInt(args[0].substring("--port=".length()));
        ApplicationContext context = new AnnotationConfigApplicationContext(SocketsApplicationConfig.class);
        DataSource dataSource = context.getBean(DataSource.class);

        createTable(dataSource);

        Server server = context.getBean(Server.class);
        server.start(port);
    }

    private static void createTable(DataSource dataSource) {
        String createTableSql =
                "CREATE TABLE IF NOT EXISTS users (" +
                "id SERIAL PRIMARY KEY, " +
                "username VARCHAR(255) NOT NULL UNIQUE, " +
                "password VARCHAR(255) NOT NULL)";
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(createTableSql);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to drop and create table 'users'.");
        }
    }
}
