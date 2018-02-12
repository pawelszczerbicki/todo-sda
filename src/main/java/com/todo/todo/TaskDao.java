package com.todo.todo;

import java.sql.Connection;
import java.sql.SQLException;

import static java.lang.String.format;

//todo implement all DAOs
public class TaskDao {

    public static final String INSERT = "insert into tasks (name) values ('%s')";
    public static final String MARK_COMPLETED = "update tasks set completed_by = %s, " +
            "completed_at=now() where id = %s";
    public static final String REMOVE_COMPLETED = "delete from tasks where completed_by is not null";
    private Connection con;

    public TaskDao(Connection con) {
        this.con = con;
    }

    public void save(String name) throws SQLException {
        con.createStatement().executeUpdate(format(INSERT, name));
    }

    public void markAsDone(Long taskId, Long userId) throws SQLException {
        con.createStatement().executeUpdate(format(MARK_COMPLETED, userId, taskId));
    }

    public void removeCompleted() throws SQLException {
        con.createStatement().executeUpdate(REMOVE_COMPLETED);
    }


}
