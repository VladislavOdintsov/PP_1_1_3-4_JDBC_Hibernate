package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String URL = "jdbc:mysql://localhost:3306/mydbusers";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Sozdamvideosam2014";
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Соединение установлено.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Соединение не установлено.");
        }
        return connection;
    }

}




