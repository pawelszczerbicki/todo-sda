package com.todo.user;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static java.lang.String.format;

public class UserDao {

    public static final String BY_LOGIN = "select * from users where login = '%s'";
    public static final String FIND_ALL = "select * from users";
    public static final String SAVE = "insert into users(login, password) values('%s', '%s')";
    private DataSource ds;
    private UserMapper mapper;

    public UserDao(DataSource ds) {
        this.ds = ds;
        this.mapper = new UserMapper();
    }

    //Get returns single result,
    //Find return multiply results
    public User getByLogin(String login) throws SQLException {
        ResultSet res = ds.getConnection().createStatement().executeQuery(format(BY_LOGIN, login));
        return mapper.getSingle(res);
    }

    public List<User> find() throws SQLException {
        return mapper.getList(ds.getConnection().createStatement().executeQuery(FIND_ALL));
    }

    //CREATE, UPDATE, DELETE - use executeUpdate();
    public void save(String login, String password) throws SQLException {
        ds.getConnection().createStatement().executeUpdate(format(SAVE, login, password));
    }
}
