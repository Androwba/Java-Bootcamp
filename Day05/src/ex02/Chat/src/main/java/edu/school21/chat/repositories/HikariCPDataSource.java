package edu.school21.chat.repositories;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;

public class HikariCPDataSource {
    private static HikariDataSource dataSource;

    public static DataSource getDataSource() {
        if (dataSource == null) {
            dataSource = new HikariDataSource();
            dataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/chat");
            dataSource.setUsername("postgres");
            dataSource.setPassword("1234");
        }
        return dataSource;
    }

    public static void closeDataSource() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}
