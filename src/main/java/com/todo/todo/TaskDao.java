package com.todo.todo;

import javax.sql.DataSource;
import java.sql.SQLException;

import static java.lang.String.format;

//todo implement all DAOs
public class TaskDao {

    public static final String INSERT = "insert into tasks (name) values ('%s')";
    public static final String MARK_COMPLETED = "update tasks set completed_by = %s, " +
            "completed_at=now() where id = %s";
    public static final String REMOVE_COMPLETED = "delete from tasks where completed_by is not null";
    private DataSource ds;

    public TaskDao(DataSource ds) {
        this.ds = ds;
    }

    public void save(String name) throws SQLException {
        ds.getConnection().createStatement().executeUpdate(format(INSERT, name));
    }

    public void markAsDone(Long taskId, Long userId) throws SQLException {
        ds.getConnection().createStatement().executeUpdate(format(MARK_COMPLETED, userId, taskId));
    }

    public void removeCompleted() throws SQLException {
        ds.getConnection().createStatement().executeUpdate(REMOVE_COMPLETED);
    }


}
