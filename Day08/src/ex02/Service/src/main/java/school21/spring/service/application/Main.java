package school21.spring.service.application;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import school21.spring.service.config.ApplicationConfig;
import school21.spring.service.services.UsersService;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        DataSource dataSource = context.getBean("hikariDataSource", DataSource.class);
        createTable(dataSource);

        UsersService usersService = context.getBean(UsersService.class);
        String tempPassword = usersService.signUp("newuser@example.com");
        System.out.println("Temporary password: " + tempPassword);

        ((AnnotationConfigApplicationContext) context).close();
    }

    private static void createTable(DataSource dataSource) {
        String createTableSql = "DROP TABLE IF EXISTS users; " +
                "CREATE TABLE users (" +
                "id SERIAL PRIMARY KEY, " +
                "email VARCHAR(255) NOT NULL UNIQUE, " +
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
