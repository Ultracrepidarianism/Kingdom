package ca.ultracrepidarianism.kingdom.utils;

import ca.ultracrepidarianism.kingdom.services.sqlutil.SqlInfo;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;


    static {
        SqlInfo info = new SqlInfo();
        config.setJdbcUrl( info.getUrl() );
        config.setUsername(info.getUsername() );
        config.setPassword( info.getPassword() );
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        ds = new HikariDataSource( config );
    }

    private DataSource() {}

    public static Connection getConnection() throws SQLException{
        return ds.getConnection();
    }
}