package com.todo.playground;

import com.todo.user.User;
import com.todo.util.Filter;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.util.*;

import static com.todo.db.tables.Users.USERS;
import static java.lang.System.currentTimeMillis;
import static java.util.Arrays.asList;

public class JooqPlayground {

    private static final HashMap<String, Field> nameToField = new HashMap<String, Field>() {{
        put("id", USERS.ID);
        put("login", USERS.LOGIN);
    }};
    private final DSLContext dsl;

    public JooqPlayground(DSLContext dsl) {
        this.dsl = dsl;
    }

    public static void main(String[] args) {
        long startCode = currentTimeMillis();

        JooqPlayground play = new JooqPlayground(DSL.using(new DatasourceConfig().dataSource(), SQLDialect.POSTGRES_9_5));

        long startQuery = currentTimeMillis();
        play.findAll();
        long finishQuery = currentTimeMillis();

        long finishCode = currentTimeMillis();

        System.out.println("Code time: " + (finishCode - startCode));
        System.out.println("Query time: " + (finishQuery - startQuery));

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


    public void filterBuilderExample() {
        List<Filter> filter = asList(new Filter("id", "1"), new Filter("login", "test"));
        List<Condition> where = new ArrayList<>();
        for (Filter f : filter) {
            Field field = nameToField.get(f.getField());
            Condition c = field.eq(f.getValue());
            where.add(c);
        }
        System.out.println(DSL.selectFrom(USERS).where(where).getSQL());

    }

    public void findAll() {
        dsl.selectFrom(USERS).fetchInto(User.class).forEach(System.out::println);
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
