package com.todo.playground.mapper;

import com.todo.user.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TemplateUserMapper implements RowMapper<User> {

    public static final int ID = 1;
    public static final int LOGIN = 2;
    public static final int PASSWORD = 3;

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(rs.getInt(ID), rs.getString(LOGIN), rs.getString(PASSWORD));
    }
}
