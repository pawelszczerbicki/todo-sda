package com.todo.playground.mapper;

import com.todo.user.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomUserMapper {
    public static final int ID = 1;
    public static final int LOGIN = 2;
    public static final int PASSWORD = 3;

    public List<User> toUsers(ResultSet rs) throws SQLException {
        List<User> users = new ArrayList<>();
        while (rs.next())
            users.add(new User(rs.getInt(ID), rs.getString(LOGIN), rs.getString(PASSWORD)));
        return users;
    }

    public User toUser(ResultSet rs) throws SQLException {
        return toUsers(rs).get(0);
    }
}
