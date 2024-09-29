package school21.spring.service.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import school21.spring.service.config.TestApplicationConfig;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UsersServiceImplTest {

    private ApplicationContext context;

    @BeforeEach
    public void setUp() {
        context = new AnnotationConfigApplicationContext(TestApplicationConfig.class);
    }

    @Test
    public void testH2DatabaseConnection() {
        DataSource dataSource = context.getBean("testDataSource", DataSource.class);
        assertNotNull(dataSource);
        try (Connection connection = dataSource.getConnection()) {
            assertNotNull(connection);
            assertTrue(connection.isValid(1));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new AssertionError("Connection to H2 database failed.", e);
        }
    }

    @Test
    public void testSignUpWithJdbc() {
        UsersService usersService = context.getBean("usersServiceJdbc", UsersService.class);
        String tempPassword = usersService.signUp("testuser@mail.com");
        assertNotNull(tempPassword);
    }

    @Test
    public void testSignUpWithJdbcTemplate() {
        UsersService usersService = context.getBean("usersServiceJdbcTemplate", UsersService.class);
        String tempPassword = usersService.signUp("anothertestuser@example.com");
        assertNotNull(tempPassword);
    }

    @ParameterizedTest
    @ValueSource(strings = {"testuser1@mail.com", "testuser2@mail.com", "testuser3@mail.com"})
    public void testSignUpWithMultipleEmailsJdbc(String email) {
        UsersService usersService = context.getBean("usersServiceJdbc", UsersService.class);
        String tempPassword = usersService.signUp(email);
        assertNotNull(tempPassword);
    }

    @ParameterizedTest
    @ValueSource(strings = {"anothertestuser1@mail.com", "anothertestuser2@mail.com", "anothertestuser3@mail.com"})
    public void testSignUpWithMultipleEmailsJdbcTemplate(String email) {
        UsersService usersService = context.getBean("usersServiceJdbcTemplate", UsersService.class);
        String tempPassword = usersService.signUp(email);
        assertNotNull(tempPassword);
    }
}
