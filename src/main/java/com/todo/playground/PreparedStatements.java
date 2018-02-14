package com.todo.playground;

import com.todo.playground.mapper.CustomUserMapper;
import com.todo.user.User;

import java.sql.*;
import java.util.List;

public class PreparedStatements {

    private Connection connection;
    private CustomUserMapper mapper;

    public PreparedStatements(Connection connection) {
        this.connection = connection;
        this.mapper = new CustomUserMapper();
    }

    public List<User> findUserLike(String username) throws SQLException {
        PreparedStatement ps = connection
                .prepareStatement("select * from users where login = ?");
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        return mapper.toUsers(rs);
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        try (Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/todo",
                "postgres", "postgres")) {
            new PreparedStatements(con).findUserLike("some").forEach(System.out::println);

        }

    }

}
