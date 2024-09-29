package edu.school21.sockets.app;

import edu.school21.sockets.config.SocketsApplicationConfig;
import edu.school21.sockets.server.Server;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1 || !args[0].startsWith("--port=")) {
            System.err.println("Usage: java -jar target/SocketServer-1.0-SNAPSHOT-jar-with-dependencies.jar --port=<port>");
            System.exit(1);
        }

        int port = Integer.parseInt(args[0].substring("--port=".length()));
        ApplicationContext context = new AnnotationConfigApplicationContext(SocketsApplicationConfig.class);
        DataSource dataSource = context.getBean(DataSource.class);

        createTables(dataSource);

        Server server = context.getBean(Server.class);
        server.start(8081);
    }

    private static void createTables(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             BufferedReader reader = new BufferedReader(new InputStreamReader(
                     Objects.requireNonNull(Main.class.getResourceAsStream("/schema.sql"))))) {

            String sql = reader.lines().collect(Collectors.joining("\n"));
            statement.execute(sql);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            System.err.println("Failed to create tables.");
        }
    }
}
