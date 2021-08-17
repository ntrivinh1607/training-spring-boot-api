package com.example.trainingspringboot.config;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.appender.db.jdbc.ColumnConfig;
import org.apache.logging.log4j.core.appender.db.jdbc.JdbcAppender;
import org.apache.logging.log4j.core.filter.ThresholdFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class LogConfig {
    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @PostConstruct
    public void onStartUp()
    {
        // Create a new connectionSource build from the Spring properties
        JdbcConnectionSource connectionSource = new JdbcConnectionSource(url, username, password, "");

        // This is the mapping between the columns in the table and what to insert in it.
        ColumnConfig[] columnConfigs = new ColumnConfig[5];
        columnConfigs[0] =  ColumnConfig.createColumnConfig(null, "APPLICATION", "ACCESS", null, null, "false", null);
        columnConfigs[1] =  ColumnConfig.createColumnConfig(null, "LOG_DATE", null, null, "true", null, null);
        columnConfigs[2] =  ColumnConfig.createColumnConfig(null, "LOGGER", "%logger", null, null, "false", null);
        columnConfigs[3] =  ColumnConfig.createColumnConfig(null, "LOG_LEVEL", "%level", null, null, "false", null);
        columnConfigs[4] =  ColumnConfig.createColumnConfig(null, "MESSAGE", "%message", null, null, "false", null);

        // filter for the appender to keep level log
        ThresholdFilter filter = ThresholdFilter.createFilter(Level.DEBUG, null, null);

        // The creation of the new database appender passing:
        // - the name of the appender
        // - ignore exceptions encountered when appending events are logged
        // - the filter created previously
        // - the connectionSource,
        // - log buffer size,
        // - the name of the table
        // - the config of the columns.
        JdbcAppender appender = JdbcAppender.createAppender("DB", "true", filter, connectionSource, "1", "LOGS", columnConfigs);

        // start the appender, and this is it...
        appender.start();
        ((Logger) LogManager.getRootLogger()).addAppender(appender);
    }
}