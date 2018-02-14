package com.todo.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Collections;

public class DbConfig {

    private static final int MAX_TOTAL = 20;
    private static final String INIT_SQL = "select 1;";

    //TODO understand "all" connection pool options
    public static DataSource dataSource(String url, String login, String password) {
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl(url);
        ds.setUsername(login);
        ds.setPassword(password);
        ds.setMaxTotal(MAX_TOTAL);
        ds.setMaxOpenPreparedStatements(500);
        ds.setConnectionInitSqls(Collections.singletonList(INIT_SQL));
        return ds;
    }

    public static JdbcTemplate jdbcTemplate(String url, String login, String pwd) {
        return new JdbcTemplate(dataSource(url, login, pwd));
    }

    public static DSLContext jooq(String url, String login, String password) {
        return DSL.using(dataSource(url, login, password), SQLDialect.POSTGRES_9_5);
    }

    public SessionFactory sessionFactory() {
       return new Configuration()
                .setProperty("hibernate.connection.driver_class", "org.postgresql.Driver")
                .setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/todo")
                .setProperty("hibernate.connection.username", "postgres")
                .setProperty("hibernate.connection.password", "postgres")
                .setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL9Dialect")
                .buildSessionFactory();
    }

}
