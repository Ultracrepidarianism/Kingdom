package ca.ultracrepidarianism.kingdom.database.dal.sql;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractHikariDAL {

    private HikariDataSource dataSource;

    protected Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    protected AbstractHikariDAL(HikariDataSource dataSource) {

        this.dataSource = dataSource;
        createTablesIfNotExist();
        updateTables();
    }

    protected abstract void createTablesIfNotExist();
    protected abstract void updateTables();
}
