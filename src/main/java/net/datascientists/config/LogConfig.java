package net.datascientists.config;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.appender.db.ColumnMapping;
import org.apache.logging.log4j.core.appender.db.jdbc.ColumnConfig;
import org.apache.logging.log4j.core.appender.db.jdbc.JdbcAppender;
import org.apache.logging.log4j.core.filter.ThresholdFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;

import net.datascientists.utilities.JdbcConnectionSource;

@Configuration
@Order(3)
@PropertySource("classpath:db.properties")
public class LogConfig
{
    @Autowired
    private Environment env;

    @PostConstruct
    public void onStartUp()
    {
      String url = env.getProperty("spring.datasource.url");
      String userName = env.getProperty("spring.datasource.username");
      String password = env.getProperty("spring.datasource.password");
      String validationQuery = env.getProperty("spring.datasource.validation-query");
      
      // Create a new connectionSource build from the Spring properties
      JdbcConnectionSource connectionSource = new JdbcConnectionSource(url, userName, password, validationQuery);

        // This is the mapping between the columns in the table and what to insert in it.
        ColumnConfig[] columnConfigs = new ColumnConfig[5];
        columnConfigs[0] = ColumnConfig.newBuilder().setConfiguration(null)
                                                    .setName("APPLICATION")
                                                    .setPattern("ACCESS")
                                                    .setLiteral(null)
                                                    .setEventTimestamp(false)
                                                    .setUnicode(false)
                                                    .setClob(false).build();
        columnConfigs[1] = ColumnConfig.newBuilder().setConfiguration(null)
            .setName("LOG_DATE")
            .setPattern(null)
            .setLiteral(null)
            .setEventTimestamp(true)
            .setUnicode(false)
            .setClob(false).build();
        columnConfigs[2] = ColumnConfig.newBuilder().setConfiguration(null)
            .setName("LOGGER")
            .setPattern("%logger")
            .setLiteral(null)
            .setEventTimestamp(false)
            .setUnicode(false)
            .setClob(false).build();
        columnConfigs[3] = ColumnConfig.newBuilder().setConfiguration(null)
            .setName("LOG_LEVEL")
            .setPattern("%level")
            .setLiteral(null)
            .setEventTimestamp(false)
            .setUnicode(false)
            .setClob(false).build();
        columnConfigs[4] = ColumnConfig.newBuilder().setConfiguration(null)
            .setName("MESSAGE")
            .setPattern("%message")
            .setLiteral(null)
            .setEventTimestamp(false)
            .setUnicode(false)
            .setClob(false).build();
        
        
        
        ThresholdFilter filter = ThresholdFilter.createFilter(Level.ERROR, null, null);
        JdbcAppender appender = JdbcAppender.newBuilder()
            .withName("ERRORLOG")
            .setTableName("Logs")
            .setConnectionSource(connectionSource)
            .withIgnoreExceptions(true)
            .withFilter(filter)
            .setBufferSize(1)
            .setColumnMappings(new ColumnMapping[]{})
            .setColumnConfigs(columnConfigs).build();

        // start the appender, and this is it...
        appender.start();
        ((Logger) LogManager.getRootLogger()).addAppender(appender);
    }
}
