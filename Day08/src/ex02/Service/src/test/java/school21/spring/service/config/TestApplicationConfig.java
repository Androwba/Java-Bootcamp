package school21.spring.service.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import javax.sql.DataSource;
import school21.spring.service.repositories.UsersRepositoryJdbcImpl;
import school21.spring.service.repositories.UsersRepositoryJdbcTemplateImpl;
import school21.spring.service.services.UsersService;
import school21.spring.service.services.UsersServiceImpl;

@Configuration
@ComponentScan("school21.spring.service")
public class TestApplicationConfig {

    @Bean("testDataSource")
    DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("schema.sql")
                .build();
    }

    @Bean("usersServiceJdbc")
    UsersService usersServiceJdbc(@Qualifier("testDataSource") DataSource dataSource) {
        return new UsersServiceImpl(new UsersRepositoryJdbcImpl(dataSource));
    }

    @Bean("usersServiceJdbcTemplate")
    UsersService usersServiceJdbcTemplate(@Qualifier("testDataSource") DataSource dataSource) {
        return new UsersServiceImpl(new UsersRepositoryJdbcTemplateImpl(dataSource));
    }
}
