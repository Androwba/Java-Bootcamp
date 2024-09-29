package edu.school21.processor;

import edu.school21.util.HikariCPDataSource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OrmManager {

    public void initialize() {
        try (BufferedReader reader = new BufferedReader(new FileReader("./target/classes/schema.sql"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                executeSql(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void executeSql(String sql) {
        System.out.println(sql);
        try (Connection connection = HikariCPDataSource.getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void save(Object entity) {
        Class<?> clazz = entity.getClass();
        String tableName = clazz.getAnnotation(edu.school21.annotations.OrmEntity.class).table();
        StringBuilder sql = new StringBuilder("INSERT INTO " + tableName + " (");
        StringBuilder values = new StringBuilder("VALUES (");
        Field idField = null;
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(edu.school21.annotations.OrmColumnId.class)) {
                idField = field;
            } else if (field.isAnnotationPresent(edu.school21.annotations.OrmColumn.class)) {
                edu.school21.annotations.OrmColumn ormColumn = field.getAnnotation(edu.school21.annotations.OrmColumn.class);
                sql.append(ormColumn.name()).append(", ");
                try {
                    values.append("'").append(field.get(entity)).append("', ");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        sql.setLength(sql.length() - 2);
        values.setLength(values.length() - 2);
        sql.append(") ").append(values).append(");");

        try (Connection connection = HikariCPDataSource.getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            System.out.println(sql.toString());

            if (idField != null) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        idField.setAccessible(true);
                        idField.set(entity, generatedKeys.getLong(1));
                    }
                }
            }
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void update(Object entity) {
        Class<?> clazz = entity.getClass();
        String tableName = clazz.getAnnotation(edu.school21.annotations.OrmEntity.class).table();
        StringBuilder sql = new StringBuilder("UPDATE " + tableName + " SET ");
        Field idField = null;
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(edu.school21.annotations.OrmColumn.class)) {
                edu.school21.annotations.OrmColumn ormColumn = field.getAnnotation(edu.school21.annotations.OrmColumn.class);
                try {
                    Object value = field.get(entity);
                    if (value != null) {
                        sql.append(ormColumn.name()).append(" = '").append(value).append("', ");
                    } else {
                        sql.append(ormColumn.name()).append(" = NULL, ");
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else if (field.isAnnotationPresent(edu.school21.annotations.OrmColumnId.class)) {
                idField = field;
            }
        }
        sql.setLength(sql.length() - 2);

        if (idField == null) {
            throw new IllegalArgumentException("The class " + clazz.getSimpleName() + " does not have a field annotated with @OrmColumnId");
        }
        try {
            idField.setAccessible(true);
            sql.append(" WHERE id = '").append(idField.get(entity)).append("';");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        executeSql(sql.toString());
    }

    public <T> T findById(Long id, Class<T> aClass) {
        String tableName = aClass.getAnnotation(edu.school21.annotations.OrmEntity.class).table();
        StringBuilder sql = new StringBuilder("SELECT * FROM " + tableName + " WHERE id = " + id + ";");

        try (Connection connection = HikariCPDataSource.getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql.toString())) {

            if (resultSet.next()) {
                T entity = aClass.getDeclaredConstructor().newInstance();
                Field[] fields = aClass.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    if (field.isAnnotationPresent(edu.school21.annotations.OrmColumn.class)) {
                        edu.school21.annotations.OrmColumn ormColumn = field.getAnnotation(edu.school21.annotations.OrmColumn.class);
                        field.set(entity, resultSet.getObject(ormColumn.name()));
                    } else if (field.isAnnotationPresent(edu.school21.annotations.OrmColumnId.class)) {
                        field.set(entity, resultSet.getObject("id"));
                    }
                }
                return entity;
            }
        } catch (SQLException | ReflectiveOperationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void shutdown() {
        HikariCPDataSource.shutdown();
    }
}
