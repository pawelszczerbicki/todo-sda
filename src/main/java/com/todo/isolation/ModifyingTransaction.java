package com.todo.isolation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ModifyingTransaction {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        try (Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/todo",
                "postgres", "postgres")) {

                con.setAutoCommit(false);
                con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);

                con.createStatement().executeUpdate("insert into users(login, password) values ('xxxxx', 'test')");
                con.createStatement().executeUpdate("update users set login = 'new login'");

                con.rollback();
        }
    }
}
