package com.todo.user;

import java.sql.SQLException;
import java.util.List;

//todo implement all methods in service
//TODO add completed history
public class UserService {

    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> find() throws SQLException {
        return userDao.find();
    }

    public void save(User u) throws SQLException {
        String hashed = hash(u.getPassword());
        userDao.save(u.getLogin(), hashed);
    }

    private String hash(String password){
         return password.toUpperCase();
    }
}
