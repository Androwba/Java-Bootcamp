package edu.school21.app;

import edu.school21.models.AllTypesTest;
import edu.school21.models.User;
import edu.school21.processor.OrmManager;
import edu.school21.util.HikariCPDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Program {
    public static void main(String[] args) {
        OrmManager ormManager = new OrmManager();
        ormManager.initialize();

        User user = new User();
        user.setFirstName("Androw");
        user.setLastName("Bayard");
        user.setAge(30);

        ormManager.save(user);

        User foundUser = ormManager.findById(user.getId(), User.class);
        System.out.println(foundUser != null ? foundUser.getFirstName() : "User not found");

        user.setFirstName("Alex");
        ormManager.update(user);

        foundUser = ormManager.findById(user.getId(), User.class);
        System.out.println(foundUser != null ? foundUser.getFirstName() : "User not found");

        Long nonExistingUserId = user.getId() + 1;
        User nonExistingUser = ormManager.findById(nonExistingUserId, User.class);
        System.out.println(nonExistingUser != null ? nonExistingUser.getFirstName() : "Non-existing user not found");

        AllTypesTest allTypesTest = new AllTypesTest();
        allTypesTest.setStringField("Example String");
        allTypesTest.setIntField(42);
        allTypesTest.setDoubleField(3.14);
        allTypesTest.setBooleanField(true);
        allTypesTest.setLongField(123456789L);

        ormManager.save(allTypesTest);

        allTypesTest.setStringField("Hello");
        ormManager.update(allTypesTest);

        AllTypesTest foundAllTypesModel = ormManager.findById(allTypesTest.getId(), AllTypesTest.class);
        System.out.println(foundAllTypesModel != null ? foundAllTypesModel.getStringField() : "AllTypesModel not found");

        selectAndPrintAllRows("simple_user");
        selectAndPrintAllRows("all_types_table");

        ormManager.shutdown();
    }

    private static void selectAndPrintAllRows(String tableName) {
        try (Connection connection = HikariCPDataSource.getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName)) {

            int columnCount = resultSet.getMetaData().getColumnCount();
            System.out.println("Table: " + tableName);
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(resultSet.getMetaData().getColumnName(i) + ": " + resultSet.getObject(i) + " ");
                }
                System.out.println();
            }
            System.out.println();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
