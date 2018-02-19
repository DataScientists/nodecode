package net.datascientists.utilities;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.logging.log4j.core.appender.db.jdbc.ConnectionSource;

public class JdbcConnectionSource implements ConnectionSource
{

    private DataSource dataSource;

    public JdbcConnectionSource(String url, String userName, String password, String validationQuery)
    {
      Properties properties = new Properties();
      properties.setProperty("user", userName);
      properties.setProperty("password", password);

      GenericObjectPool pool = new GenericObjectPool();
      DriverManagerConnectionFactory cf = new DriverManagerConnectionFactory(url, properties);
      new PoolableConnectionFactory(cf, pool, null, validationQuery, 3, false, false, Connection.TRANSACTION_READ_COMMITTED);
      this.dataSource = new PoolingDataSource(pool);
    }

    @Override
    public Connection getConnection() throws SQLException
    {
      return dataSource.getConnection();
    }

}
