package com.todo.playground;

import com.todo.playground.mapper.TemplateUserMapper;
import com.todo.user.User;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class JdbcTemplateSpring {
    public static void main(String[] args) {

        JdbcTemplate jdbcTemplate = new JdbcTemplate(new DatasourceConfig().dataSource());
        List<User> users = jdbcTemplate.query("select id, login, password from users " +
                        "where login = ?",
                new TemplateUserMapper(), "John");
        
        users.forEach(System.out::println);
    }
}
