package com.todo;

import com.todo.config.DbConfig;
import com.todo.user.UserDao;
import com.todo.user.UserJdbcTmplDao;
import org.flywaydb.core.Flyway;

import javax.sql.DataSource;
import java.sql.SQLException;

public class DbTestMain {

    private static final String URL = "jdbc:postgresql://localhost:5432/todo";
    private static final String LOGIN = "postgres";
    private static final String PASSWORD = "postgres";

    //TODO get DB login data from ENV Variables or args or external file
    public static void main(String[] args) throws SQLException {
        //TODO change to builder pattern

        //Remember - do not edit flyway files
        DataSource ds = DbConfig.dataSource(URL, LOGIN, PASSWORD);
        Flyway flyway = new Flyway();
        flyway.setDataSource(ds);
        flyway.migrate();


        //Fail migration
        System.out.println("Using DataSource directly");
        System.out.println(new UserDao(ds).getByLogin("John"));

        System.out.println("Using JDBC Template");
        System.out.println(new UserJdbcTmplDao(DbConfig.jdbcTemplate(URL, LOGIN, PASSWORD))
                .getByLogin("John"));

//        System.out.println("\n using jooq");
//        new UserJooqDao(DbConfig.jooq(URL, LOGIN, PASSWORD))
//                .find()
//                .forEach(System.out::println);
    }
}
