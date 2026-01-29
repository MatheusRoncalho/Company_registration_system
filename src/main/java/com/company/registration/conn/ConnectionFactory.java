package com.company.registration.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/registration_system";
        String user = "root";
        String password = "root";

        return DriverManager.getConnection(url, user, password);
    }
}
