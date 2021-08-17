package com.example.trainingspringboot.config;

import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnection;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.logging.log4j.core.appender.db.jdbc.ConnectionSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcConnectionSource implements ConnectionSource
{
    private DataSource dataSource;

    public JdbcConnectionSource(String url, String userName, String password, String validationQuery)
    {
        Properties properties = new Properties();
        properties.setProperty("user", userName);
        properties.setProperty("password", password);

        GenericObjectPool<PoolableConnection> pool = new GenericObjectPool<>();
        DriverManagerConnectionFactory cf = new DriverManagerConnectionFactory(url, properties);
        new PoolableConnectionFactory(cf, pool, null, validationQuery, 3, false, false, Connection.TRANSACTION_READ_COMMITTED);
        this.dataSource = new PoolingDataSource(pool);
    }

    @Override
    public Connection getConnection() throws SQLException
    {
        return dataSource.getConnection();
    }

    @Override
    public State getState() {
        return null;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isStarted() {
        return false;
    }

    @Override
    public boolean isStopped() {
        return false;
    }
}