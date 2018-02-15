package com.todo.playground.hibernate;

import org.hibernate.transform.ResultTransformer;

import java.util.List;

public class UserResultTransformer implements ResultTransformer {
    @Override
    public Object transformTuple(Object[] tuple, String[] aliases) {
        UserHibernate user = new UserHibernate();
        user.id = (long) tuple[0];
        user.login = (String) tuple[1];
        return user;
    }

    @Override
    public List transformList(List collection) {
        return collection;
    }
}
