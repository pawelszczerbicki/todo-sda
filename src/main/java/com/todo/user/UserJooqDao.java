package com.todo.user;

import com.todo.db.tables.records.UsersRecord;
import com.todo.util.Filter;
import com.todo.util.Page;
import com.todo.util.Verb;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.SelectWithTiesAfterOffsetStep;
import org.jooq.impl.DSL;

import java.util.*;

import static com.todo.db.tables.Users.USERS;

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

    public List<User> find(Page p, List<Filter> filter) {
          List<Condition> where = new ArrayList<>();
//        filter.stream().map(f -> nameToField.get(f.getField()).eq(f.getValue()));
        for (Filter f : filter) {
            Field field = nameToField.get(f.getField());
            Condition c = field.eq(f.getValue());
            where.add(c);
        }

        SelectWithTiesAfterOffsetStep<UsersRecord> limit = dsl.selectFrom(USERS)
                .where(where)
                .offset(p.getOffset())
                .limit(p.getSize());
        System.out.println(limit.getSQL());
        return limit
                .fetchInto(User.class);
    }

    private Condition getCondition (Verb verb, String name){
        nameToField.get(name);
         ....
    }

    public static void main(String[] args) {
        List<Filter> filter = Arrays.asList(new Filter("id", "1"),
                new Filter("login", "test"));
        List<Condition> where = new ArrayList<>();
        for (Filter f : filter) {
            Field field = nameToField.get(f.getField());
            Condition c = field.eq(f.getValue());
            where.add(c);
        }

        DSL.selectFrom(USERS)
                .where(where);

    }

    public User getByLogin(String login) {
        return dsl.selectFrom(USERS).where(USERS.LOGIN.eq(login)).fetchAnyInto(User.class);
    }

    public void save(User u) {
        dsl.newRecord(USERS, u).store();
    }

}
