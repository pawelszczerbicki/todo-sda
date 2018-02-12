package com.todo.playground;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        try (Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/todo",
                "postgres", "postgres")) {
            new PreparedStatements(con).findUserLike("some").forEach(System.out::println);

        }

    }

}
