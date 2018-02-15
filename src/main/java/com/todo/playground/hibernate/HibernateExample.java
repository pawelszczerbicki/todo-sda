package com.todo.playground.hibernate;

import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.List;

import static java.lang.System.currentTimeMillis;
import static org.hibernate.criterion.Order.asc;
import static org.hibernate.criterion.Projections.projectionList;
import static org.hibernate.criterion.Projections.property;
import static org.hibernate.criterion.Restrictions.*;

@SuppressWarnings("unchecked")
public class HibernateExample {
    private Session session;

    public HibernateExample(Session session) {
        this.session = session;
    }

    public static void main(String[] args) {
        long startCode = currentTimeMillis();
        Session session = HibernateConfig.sessionFactory().getCurrentSession();
        session.beginTransaction();


        HibernateExample hibernate = new HibernateExample(session);

        long startQuery = currentTimeMillis();
        hibernate.criteriaById();
        session.flush();
        session.clear();
        session.getTransaction().commit();
        long finishQuery = currentTimeMillis();
        long finishCode = currentTimeMillis();

        System.out.println("Code time: " + (finishCode - startCode));
        System.out.println("Query time: " + (finishQuery - startQuery));
    }

    public void newCriteriaExample() {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<UserHibernate> query = builder.createQuery(UserHibernate.class);
        Root<UserHibernate> root = query.from(UserHibernate.class);
        query.select(root);
        session.createQuery(query).list().forEach(System.out::println);
    }

    public void criteriaListExample() {
        session.createCriteria(UserHibernate.class).list()
                .forEach(System.out::println);
    }

    public void criteriaWhereListExample() {
        session.createCriteria(UserHibernate.class)
                .add(or(like("login", "some"), isNotNull("id")))
                .list().forEach(System.out::println);
    }

    //TODO show lazy loading with debugger
    public void criteriaJoin() {
        List list = session.createCriteria(UserHibernate.class)
                .createAlias("account", "account").list();
        list.forEach(System.out::println);
        ;
    }

    public void criteriaGroupBy() {
        session.createCriteria(UserHibernate.class)
                .add(like("login", "some"))
                .addOrder(asc("login"))
                .setFirstResult(0)
                .setMaxResults(10)
                .setProjection(projectionList().add(property("login")))
                .list().forEach(System.out::println);
    }

    public void criteriaById() {
        //TODO show with id as int
        UserHibernate id = session.byId(UserHibernate.class).load(1l);
        System.out.println(id);
    }

    public void save() {
        UserHibernate user = new UserHibernate();
        user.login = "hibernate save";
        user.password = "hibernate password";
        user.account = new AccountHibernate("hibernate account");
        user.address = new AddressHibernate("hibernate address");
        user.projects = Arrays.asList(new ProjectHibernate("h project 1"), new ProjectHibernate("h proj 2"));
        session.save(user);
    }


    public void hmbExample() {
        List<UserHibernate> list = session.getNamedQuery("users").setParameter("id", 0)
                .setResultTransformer(new UserResultTransformer()).list();
        list.forEach(System.out::println);
    }


    //TODO show how many SQLs are fired
    public void hqlPagination() {
        session.createQuery("from UserHibernate u where u.id > :id order by u.id")
                .setParameter("id", 0l)
                .setMaxResults(10)
                .setFirstResult(0).list().forEach(System.out::println);
    }
}
