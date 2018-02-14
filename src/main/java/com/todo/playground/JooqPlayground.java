package com.todo.playground;

import com.todo.user.User;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.todo.db.tables.Users.USERS;
import static java.lang.System.currentTimeMillis;

public class JooqPlayground {

    private final DSLContext dsl;

    public JooqPlayground(DSLContext dsl) {
        this.dsl = dsl;
    }

    public static void main(String[] args) {
        JooqPlayground play = new JooqPlayground(DSL.using(new DatasourceConfig().dataSource(), SQLDialect.POSTGRES_9_5));
        play.showSql();
    }

     //https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html
    public void showSql() {
        String sql = dsl.select(DSL.count(USERS.ID), USERS.PASSWORD)
                .from(USERS)
                .where(USERS.LOGIN.isNotNull())
                .groupBy(USERS.PASSWORD)
                .getSQL();



        Map<String, Integer> passwordToCount = dsl.select(USERS.PASSWORD, DSL.count())
                .from(USERS).groupBy(USERS.PASSWORD)
                .fetchMap(USERS.PASSWORD, DSL.count());


        System.out.println(sql);
    }

    public void selectConst() {
        System.out.println(dsl.select(DSL.field("1", Integer.class)).fetchInto(Integer.class));
    }

    public void buildWhere() {
        List<Condition> where = new ArrayList<>();
        where.add(USERS.LOGIN.contains("test").or(USERS.LOGIN.eq("11")));
        where.add(USERS.PASSWORD.isNotNull());

        Optional<User> user = dsl.selectFrom(USERS).where(where).limit(1).fetchOptionalInto(User.class);
    }

    public void testBuildTime() {
        long start = currentTimeMillis();

        dsl.select(DSL.count(), USERS.PASSWORD)
                .from(USERS)
                .where(USERS.LOGIN.isNotNull())
                .groupBy(USERS.PASSWORD)
                .getSQL();

        long time = currentTimeMillis() - start;
        System.out.println(time);
    }
}
