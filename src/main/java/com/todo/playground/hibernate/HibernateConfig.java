package com.todo.playground.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import static org.hibernate.cfg.AvailableSettings.*;

public class HibernateConfig {

    public static SessionFactory sessionFactory() {
        return new Configuration()
                .addAnnotatedClass(UserHibernate.class)
                .addAnnotatedClass(AccountHibernate.class)
                .addAnnotatedClass(ProjectHibernate.class)
                .addAnnotatedClass(AddressHibernate.class)
                .addResource("mapping/users.hbm.xml")
                .setProperty(Environment.DRIVER, "org.postgresql.Driver")
                .setProperty(URL, "jdbc:postgresql://localhost:5432/todo_hibernate")
                .setProperty(USER, "postgres")
                .setProperty(PASS, "postgres")
                .setProperty(DIALECT, "org.hibernate.dialect.PostgreSQL9Dialect")
                .setProperty(CURRENT_SESSION_CONTEXT_CLASS, "thread")
                .setProperty(SHOW_SQL, Boolean.TRUE.toString())
                .setProperty(HBM2DDL_AUTO, "update")
                .buildSessionFactory();
    }
}
