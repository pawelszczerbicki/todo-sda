package com.todo.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//TODO create interface 
public class UserMapper {
    //TODO change to aliases
    private static final int ID = 1;
    private static final int LOGIN = 2;
    private static final int PASSWORD = 3;

    public User getSingle(ResultSet res) throws SQLException {
        List<User> users = getList(res);
        if (users.size() != 1) throw new IllegalStateException();
        return users.get(0);
    }

    public List<User> getList(ResultSet res) throws SQLException {
        List<User> users = new ArrayList<>();
        while (res.next())
            users.add(new User(res.getInt(ID), res.getString(LOGIN), res.getString(PASSWORD)));
        return users;
    }

}
