package com.todo.user;

import com.todo.util.Filter;
import com.todo.util.Page;
import com.todo.util.Verb;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.todo.db.tables.Users.USERS;
import static java.util.stream.Collectors.toList;

public class UserJooqDao {

    private static Map<String, Field> nameToField;

    static {
        nameToField = new HashMap<>();
        nameToField.put("id", USERS.ID);
        nameToField.put("login", USERS.LOGIN);
        nameToField.put("createdAt", USERS.CREATED_AT);
    }

    private final DSLContext dsl;

    public UserJooqDao(DSLContext dsl) {
        this.dsl = dsl;
    }

    @SuppressWarnings("uncheckded")
    public List<User> find(Page p, List<Filter> filter) {
        List<Condition> where = filter.stream().map(f -> nameToField.get(f.getField()).eq(f.getValue())).collect(toList());
        return dsl.selectFrom(USERS)
                .where(where)
                .offset(p.getOffset())
                .limit(p.getSize())
                .fetchInto(User.class);
    }

    //TODO allow any condition to be found in DB
    private Condition getCondition(Verb verb, String name) {
//        nameToField.get(name);
        return null;
    }

    public User getByLogin(String login) {
        return dsl.selectFrom(USERS).where(USERS.LOGIN.eq(login)).fetchAnyInto(User.class);
    }

    public void save(User u) {
        dsl.newRecord(USERS, u).store();
    }

}
