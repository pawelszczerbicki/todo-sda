package com.todo.playground;

import com.todo.playground.mapper.CustomUserMapper;
import org.apache.commons.dbcp2.*;
import org.apache.commons.pool2.impl.AbandonedConfig;
import org.apache.commons.pool2.impl.DefaultEvictionPolicy;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import javax.sql.DataSource;
import java.sql.Connection;

import static java.util.Collections.singletonList;

public class DatasourceConfig {

    private static final String ENDPOINT = "jdbc:postgresql://localhost:5432/todo";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String INIT_SQL = "Select 1;";
    private static final int MAX_CONNECTIONS_PER_DB = 30;
    private static final int MAX_IDLE = MAX_CONNECTIONS_PER_DB;
    private static final int IDLE_CONNECTION_TIMEOUT_MILLIS = 60_000;
    private static final int MAX_WAIT_TIME = 30_000;

    private static AbandonedConfig abandonedConfig() {
        AbandonedConfig abandonedConfig = new AbandonedConfig();
        abandonedConfig.setRemoveAbandonedOnBorrow(true);
        return abandonedConfig;
    }

    private static GenericObjectPoolConfig poolConfig(int maxTotalConnections, int maxIdleConnections) {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(maxTotalConnections);
        config.setMaxIdle(maxIdleConnections);
        config.setMaxWaitMillis(MAX_WAIT_TIME);
        config.setTestOnReturn(true);

        config.setEvictionPolicyClassName(DefaultEvictionPolicy.class.getCanonicalName());
        config.setMinEvictableIdleTimeMillis(IDLE_CONNECTION_TIMEOUT_MILLIS);
        config.setTimeBetweenEvictionRunsMillis(IDLE_CONNECTION_TIMEOUT_MILLIS);
        return config;
    }

    public static void main(String[] args) throws Exception {
        PoolingDataSource dataSource = new DatasourceConfig().advancedDs();
        Connection connection = dataSource.getConnection();
        new CustomUserMapper().toUsers(connection.createStatement().executeQuery("select * from users"))
                .forEach(System.out::println);
        dataSource.close();
    }

    public DataSource dataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl(ENDPOINT);
        ds.setUsername(USER);
        ds.setPassword(PASSWORD);
        return ds;
    }

    /*
    "eviction" occurs when a database connection is unused by the application (idle in the pool) for a long
            enough period of time at which point it's discarded
            
    "abandoned connection" refers to database connection that is still in use by an application after some
            period of time, usually long enough to indicate that the connection is leaking
     */

    /*
    
    Datasource improves application performance as connections are not created/closed within a class,
    they are managed by the application server and can be fetched while at runtime. it provides a facility
    creating a pool of connections helpful for enterprise applications

    Change Connection interface to Datasource interface and get always fresh connection which is maintained by DS
    Get connection, run sql and leave connection.
     */

    public PoolingDataSource advancedDs() {
        //all params are https://commons.apache.org/proper/commons-dbcp/configuration.html

        PoolableConnectionFactory factory = new PoolableConnectionFactory(
                new DriverManagerConnectionFactory(ENDPOINT, USER, PASSWORD), null);
        GenericObjectPool<PoolableConnection> connectionPool = new GenericObjectPool<>(factory);
        connectionPool.setConfig(poolConfig(MAX_CONNECTIONS_PER_DB, MAX_IDLE));
        connectionPool.setAbandonedConfig(abandonedConfig());

        factory.setPool(connectionPool);
        factory.setMaxOpenPreparedStatements(MAX_CONNECTIONS_PER_DB);
        factory.setConnectionInitSql(singletonList(INIT_SQL));

        return new PoolingDataSource<>(connectionPool);
    }
}
