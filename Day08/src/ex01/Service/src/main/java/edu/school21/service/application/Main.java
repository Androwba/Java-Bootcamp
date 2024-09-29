package edu.school21.service.application;

import edu.school21.service.models.User;
import edu.school21.service.repositories.UsersRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");

        DataSource dataSource = context.getBean("driverManagerDataSource", DataSource.class);
        createTable(dataSource);

        UsersRepository usersRepository = context.getBean("usersRepositoryJdbc", UsersRepository.class);
        testRepository(usersRepository, "Jdbc Implementation");

        DataSource dataSourceTemplate = context.getBean("hikariDataSource", DataSource.class);
        createTable(dataSourceTemplate);

        UsersRepository usersRepositoryTemplate = context.getBean("usersRepositoryJdbcTemplate", UsersRepository.class);
        testRepository(usersRepositoryTemplate, "JdbcTemplate Implementation");

        ((ConfigurableApplicationContext) context).close();
    }

    private static void testRepository(UsersRepository usersRepository, String implementation) {
        System.out.println("Testing findAll method with " + implementation + ":");
        testSaveMethod(usersRepository);
        System.out.println(usersRepository.findAll());
        System.out.println("------------------------------------");
        testUpdateMethod(usersRepository);
        System.out.println("Updated users:");
        System.out.println(usersRepository.findAll());
        testFindByIdMethod(usersRepository);
        testFindByEmailMethod(usersRepository);
        testDeleteMethod(usersRepository);
        System.out.println("findAll after deletion:");
        System.out.println(usersRepository.findAll());
    }

    private static void createTable(DataSource dataSource) {
        String createTableSql = "DROP TABLE IF EXISTS users; " +
                "CREATE TABLE users (" +
                "id SERIAL PRIMARY KEY, " +
                "email VARCHAR(255) NOT NULL UNIQUE)";
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(createTableSql);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to drop and create table 'users'.");
        }
    }

    private static void testSaveMethod(UsersRepository usersRepository) {
        usersRepository.save(new User(null, "userWithId1@mail.com"));
        usersRepository.save(new User(null, "userWithId2@mail.com"));
        usersRepository.save(new User(null, "userWithID3@mail.com"));
    }

    private static void testUpdateMethod(UsersRepository usersRepository) {
        usersRepository.update(new User(1L, "updated_userWithId1@mail.com"));
        usersRepository.update(new User(2L, "updated_userWithId2@mail.com"));
        usersRepository.update(new User(3L, "updated_userWithId3@mail.com"));
    }

    private static void testDeleteMethod(UsersRepository usersRepository) {
        usersRepository.delete(1L);
        usersRepository.delete(2L);
        usersRepository.delete(3L);
    }

    private static void testFindByIdMethod(UsersRepository usersRepository) {
        Optional<User> user1 = usersRepository.findById(1L);
        Optional<User> user2 = usersRepository.findById(2L);
        Optional<User> user3 = usersRepository.findById(3L);

        System.out.println("Find by ID results:");
        user1.ifPresent(new java.util.function.Consumer<User>() {
            @Override
            public void accept(User user) {
                System.out.println(user);
            }
        });
        user2.ifPresent(user -> System.out.println(user));

        user3.ifPresent(System.out::println);
    }

    private static void testFindByEmailMethod(UsersRepository usersRepository) {
        Optional<User> user1 = usersRepository.findByEmail("updated_userWithId1@mail.com");
        Optional<User> user2 = usersRepository.findByEmail("updated_userWithId2@mail.com");
        Optional<User> user3 = usersRepository.findByEmail("updated_userWithId3@mail.com");

        System.out.println("Find by Email results:");
        user1.ifPresent(System.out::println);
        user2.ifPresent(System.out::println);
        user3.ifPresent(System.out::println);
    }
}
