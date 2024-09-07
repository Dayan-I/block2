package ru.vagapov.userapi.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    // реализуйте настройку соединения с БД

    public Connection getConnection(){
        Connection connection;
        try{ Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "root");
            System.out.println("Connection is OK");

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Connection ERROR");
            throw new RuntimeException(e);

        }
        return connection;
    }
}
