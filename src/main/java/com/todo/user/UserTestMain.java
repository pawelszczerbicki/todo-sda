package com.todo.user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UserTestMain {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        try (Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/todo",
                "postgres", "postgres")) {

            System.out.println(new UserDao(con).getByLogin("isolation"));
        }
    }
}
