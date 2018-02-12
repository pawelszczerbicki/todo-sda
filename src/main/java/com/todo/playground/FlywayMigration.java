package com.todo.playground;

import org.flywaydb.core.Flyway;

public class FlywayMigration {

    public static void main(String[] args) {
         Flyway flyway = new Flyway();
         flyway.setDataSource(new DatasourceConfig().dataSource());
         flyway.migrate();
    }
}
