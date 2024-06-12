package com.learning.crud_application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB_Connection {
    static String user = "";
    static String password = "";
    static String url = "jdbc:mysql://localhost/crud_app_javaFX";
    static String driver = "com.mysql.cj.jdbc.Driver";

    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return con;
    }
}
