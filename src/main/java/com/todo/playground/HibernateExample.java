package com.todo.playground;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.util.List;

public class HibernateExample {
    public static void main(String[] args) {
        SessionFactory sessionFactory = new HibernateExample().sessionFactory();
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.beginTransaction();
        System.out.println(currentSession.getCacheMode().toString());
        List<UserHibernate> list = currentSession.createQuery("from UserHibernate").list();
        list.forEach(System.out::println);

    }

    public SessionFactory sessionFactory() {
        
        return new Configuration()
                .addAnnotatedClass(UserHibernate.class)
                .addPackage("com.todo")
                .setProperty("hibernate.connection.driver_class", "org.postgresql.Driver")
                .setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/todo_hibernate")
                .setProperty("hibernate.connection.username", "postgres")
                .setProperty("hibernate.connection.password", "postgres")
                .setProperty(Environment.DIALECT, "org.hibernate.dialect.PostgreSQL9Dialect")
                .setProperty("hibernate.current_session_context_class", "thread")
                .setProperty("hibernate.show_sql", "true")
                .setProperty(Environment.HBM2DDL_AUTO, "update")
                .setProperty("hibernate.archive.autodetection", "class")
                .buildSessionFactory();
    }
}
