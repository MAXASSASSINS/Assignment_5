package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.TimeZone;

public class MyPostgresConnection {

    private static volatile Connection connection;

    public static Connection getConnection(){
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
        if(connection == null) {
            synchronized (MyPostgresConnection.class) {
                if (connection == null) {
                    String ip = System.getenv("POSTGRES_IP");
                    String username = System.getenv("POSTGRES_USERNAME");
                    String password = System.getenv("POSTGRES_PASSWORD");
                    String db = System.getenv("POSTGRES_DATABASE");
                    String jdbcUrl = "jdbc:postgresql://" + ip + ":5432/" + db;

                    try {
                        connection = DriverManager.getConnection(jdbcUrl, username, password);
                        System.out.println("Connected to the PostgreSQL database!");
                    } catch (SQLException e) {
                        System.err.format("SQL State: %s\n", e.getSQLState());
                        System.err.format("Error Code: %s\n", e.getErrorCode());
                        System.err.format("Message: %s\n", e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        }

        return connection;
    }
}
