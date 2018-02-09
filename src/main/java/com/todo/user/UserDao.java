package com.todo.user;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

public class UserDao {
    public static final int ID = 1;
    public static final int LOGIN = 2;
    public static final int PASSWORD = 3;
    private Connection connection;

    public UserDao(Connection connection) {
        this.connection = connection;
    }

    public User getByLogin(String login) throws SQLException {
        ResultSet res = connection.createStatement().executeQuery(
                String.format("select * from users where login = '%s'", login)
        );
        if(!res.next()) throw new NoSuchElementException();
        //TODO should use aliases
        return new User(res.getInt(ID), res.getString(LOGIN), res.getString(PASSWORD));
    }
}
