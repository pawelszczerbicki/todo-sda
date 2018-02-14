package com.todo.user;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UserDao {

    public static final String BY_LOGIN = "select * from users where login = ?";
    public static final String FIND_ALL = "select * from users";
    public static final String SAVE = "insert into users(login, password) values(?, ?)";
    private DataSource ds;
    private UserMapper mapper;

    public UserDao(DataSource ds) {
        this.ds = ds;
        this.mapper = new UserMapper();
    }

    //Get returns single result,
    //Find return multiply results
    public User getByLogin(String login) throws SQLException {
        PreparedStatement ps = ds.getConnection().prepareStatement(BY_LOGIN);
        ps.setString(1, login);
        return mapper.getSingle(ps.executeQuery());
    }

    public List<User> find() throws SQLException {
        return mapper.getList(ds.getConnection().prepareStatement(FIND_ALL).executeQuery());
    }

    //CREATE, UPDATE, DELETE - use executeUpdate();
    public void save(String login, String password) throws SQLException {
        PreparedStatement ps = ds.getConnection().prepareStatement(SAVE);
        ps.setString(1, login);
        ps.setString(2, password);
        ps.executeUpdate();
    }
}
