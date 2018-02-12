package com.todo.playground;

import com.todo.playground.mapper.CustomUserMapper;
import com.todo.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class PreparedStatements {

    private Connection connection;
    private CustomUserMapper mapper;

    public PreparedStatements(Connection connection) {
        this.connection = connection;
        this.mapper = new CustomUserMapper();
    }

    public List<User> findUserLike(String username) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select * from users where login = ?");
        ps.setString(1, username);
        return mapper.toUsers(ps.executeQuery());
    }
}
