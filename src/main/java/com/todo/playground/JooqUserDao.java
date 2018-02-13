package com.todo.playground;

import com.todo.user.User;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.util.List;

import static com.todo.db.tables.Users.USERS;
import static org.jooq.SQLDialect.POSTGRES_9_5;

public class JooqUserDao {

    private final DSLContext dsl;

    public JooqUserDao(DSLContext dsl) {
        this.dsl = dsl;
    }

    public List<User> find() {
        return dsl.selectFrom(USERS).fetchInto(User.class);
    }

    public static void main(String[] args) {
        DSLContext dslContext = DSL.using(new DatasourceConfig().dataSource(), POSTGRES_9_5);
        new JooqUserDao(dslContext).find().forEach(System.out::println);
    }
}
