package com.todo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        try (Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/todo",
                "postgres", "postgres")) {
            try {
                con.setAutoCommit(false);
                con.createStatement().executeUpdate("insert into users(login, password) values ('xxxxx', 'test')");
                selectAllUsersAndPrint(con);

                con.createStatement().executeUpdate("update users set login = null where 1=1");

                con.rollback();

            } catch (SQLException e) {
                e.printStackTrace();
                con.rollback();
                selectAllUsersAndPrint(con);

            }
        }
    }

    private static void selectAllUsersAndPrint(Connection con) throws SQLException {
        ResultSet res = con.createStatement().executeQuery("select * from users;");
        while(res.next()){
            System.out.println(res.getString(2));
        }
    }
}










