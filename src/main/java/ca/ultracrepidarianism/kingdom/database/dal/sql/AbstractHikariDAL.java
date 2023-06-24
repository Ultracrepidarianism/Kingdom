package ca.ultracrepidarianism.kingdom.database.dal.sql;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractHikariDAL {

    protected HikariDataSource dataSource;

    protected Connection cntx = null;

    protected AbstractHikariDAL(final HikariDataSource dataSource) {
        this.dataSource = dataSource;
        createTablesIfNotExist();
        updateTables();
    }

    protected abstract void createTablesIfNotExist();
    protected abstract void updateTables();
}
