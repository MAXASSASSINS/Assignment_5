package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NameTimestamp {

    public void insert(String name, long value){
        Connection connection = MyPostgresConnection.getConnection();
        String sql = "INSERT INTO user_schema.\"user\" (name, value) VALUES (?, ?) ";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setLong(2, value);

            int count = preparedStatement.executeUpdate();
            System.out.println("Rows affected: " + count);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(String name, long value){
        Connection connection = MyPostgresConnection.getConnection();
        String sql = "INSERT INTO user_schema.\"user\" (name, value) " +
                "VALUES (?, ?) " +
                "ON CONFLICT (name) DO UPDATE SET value = EXCLUDED.value " +
                "WHERE EXCLUDED.value > user_schema.\"user\".value";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setLong(2, value);

            int count = preparedStatement.executeUpdate();
            System.out.println("Rows affected: " + count);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
