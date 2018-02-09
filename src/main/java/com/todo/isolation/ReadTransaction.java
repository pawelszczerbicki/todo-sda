package com.todo.isolation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReadTransaction {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        try (Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/todo",
                "postgres", "postgres")) {

            con.setAutoCommit(false);
            con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);

            selectAllUsersAndPrint(con);
            selectAllUsersAndPrint(con);


            con.rollback();

        }
    }

    private static void selectAllUsersAndPrint(Connection con) throws SQLException {
        ResultSet res = con.createStatement().executeQuery("select * from users;");
        while (res.next()) {
            System.out.println(res.getString(2) +" " + res.getString(3));
        }
    }
}
