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
            case MYSQL -> {
                config.setDriverClassName("com.mysql.cj.jdbc.Driver");
                config.setJdbcUrl("jdbc:mysql://" + connectionInfo.address + ":" + connectionInfo.port + "/" + connectionInfo.database);
                config.setUsername(connectionInfo.username);
                config.setPassword(connectionInfo.password);
                config.addDataSourceProperty("maximumPoolSize", "20");
                config.addDataSourceProperty("minimumIdle", "5");
                config.addDataSourceProperty("connectionTimeout", "5000");
                config.addDataSourceProperty("idleTimeout", "600000");
                config.addDataSourceProperty("maxLifetime", "1800000");
                _dal = setDalFromHikari(config);

            }
            case MARIADB -> {
                config.setDriverClassName("com.mysql.cj.jdbc.Driver");
                config.setJdbcUrl("jdbc:mysql://" + connectionInfo.address + ":" + connectionInfo.port + "/" + connectionInfo.database);
                config.setUsername(connectionInfo.username);
                config.setPassword(connectionInfo.password);
                _dal = setDalFromHikari(config);
            }
            default -> _dal = null;
        }
    }

    private DAL setDalFromHikari(final HikariConfig config) {
        dataSource = new HikariDataSource(config);
        return new SQLDAL(dataSource);
    }
}
