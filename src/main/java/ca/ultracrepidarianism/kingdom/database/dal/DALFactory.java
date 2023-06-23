package ca.ultracrepidarianism.kingdom.database.dal;

import ca.ultracrepidarianism.kingdom.database.dal.sql.SQLDAL;
import ca.ultracrepidarianism.kingdom.database.connections.ConnectionInfo;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariPool;

import java.util.Properties;

public class DALFactory {
    private DAL _dal;

    public DAL getDal() {
        return _dal;
    }

    private HikariDataSource dataSource;

    public DALFactory(final ConnectionInfo connectionInfo) {
        final HikariConfig config = new HikariConfig();

        switch (connectionInfo.connectionType) {
            case MYSQL: {
                config.setDriverClassName("com.mysql.cj.jdbc.Driver");
                config.setJdbcUrl("jdbc:mysql://" + connectionInfo.address + ":" + connectionInfo.port + "/" + connectionInfo.database);
                config.setUsername(connectionInfo.username);
                config.setPassword(connectionInfo.password);
                Properties props = new Properties();
                props.putIfAbsent("cachePrepStmts", "true");
                props.putIfAbsent("prepStmtCacheSize", "250");
                props.putIfAbsent("prepStmtCacheSqlLimit", "2048");
                props.putIfAbsent("useServerPrepStmts", "true");
                props.putIfAbsent("useLocalSessionState", "true");
                props.putIfAbsent("rewriteBatchedStatements", "true");
                props.putIfAbsent("cacheResultSetMetadata", "true");
                props.putIfAbsent("cacheServerConfiguration", "true");
                props.putIfAbsent("elideSetAutoCommits", "true");
                props.putIfAbsent("maintainTimeStats", "false");
                config.setDataSourceProperties(props);
                _dal = setDalFromHikari(config);

            }
            break;
            case MARIADB: {
                config.setDriverClassName("com.mysql.cj.jdbc.Driver");
                config.setJdbcUrl("jdbc:mysql://" + connectionInfo.address + ":" + connectionInfo.port + "/" + connectionInfo.database);
                config.setUsername(connectionInfo.username);
                config.setPassword(connectionInfo.password);
                _dal = setDalFromHikari(config);
            }
            break;
            case POSTGRESQL:
            case SQLLITE:
            case H2:
            case MONGODB:
            default:
                _dal = null;
                break;
        }
    }

    private DAL setDalFromHikari(HikariConfig config) {
        dataSource = new HikariDataSource(config);
        return new SQLDAL(dataSource);
    }
}
