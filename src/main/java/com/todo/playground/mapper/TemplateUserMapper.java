package com.todo.playground.mapper;

import com.todo.user.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TemplateUserMapper implements RowMapper<User> {

    public static final String ID = "id";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(rs.getInt(ID), rs.getString(LOGIN), rs.getString(PASSWORD));
    }
}
