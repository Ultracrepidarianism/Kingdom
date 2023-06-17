package ca.ultracrepidarianism.kingdom.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

public class DataSource {

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    private static DSLContext context;

    static {
        config.setJdbcUrl( "jdbc_url" );
        config.setUsername( "database_username" );
        config.setPassword( "database_password" );
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        ds = new HikariDataSource( config );
        context = DSL.using(ds, SQLDialect.MYSQL);
    }

    private DataSource() {}

    public static DSLContext getContext() {
        return context;
    }
}