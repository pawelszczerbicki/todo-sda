package com.todo.user;

import com.todo.user.mapper.UserTemplateMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserJdbcTmplDao {
    public static final String BY_LOGIN = "select * from users where login = ?";
    public static final String FIND_ALL = "select * from users";
    public static final String SAVE = "insert into users(login, password) values(?, ?)";

    private final JdbcTemplate tmpl;

    public UserJdbcTmplDao(JdbcTemplate jdbcTemplate) {
        this.tmpl = jdbcTemplate;
    }

    public User getByLogin(String login) {
         return tmpl.queryForObject(BY_LOGIN, new Mapper(), login);
    }

    public List<User> find() {
        return tmpl.query(FIND_ALL, new UserTemplateMapper());
    }

    public void save(String login, String password) {
        tmpl.update(SAVE, login, password);
    }

    class Mapper implements RowMapper<User>{

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User(rs.getInt("id"), rs.getString("login"),
                    rs.getString("password"));
        }
    }
}
