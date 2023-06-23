package ca.ultracrepidarianism.kingdom.database.dal.sql;

import com.zaxxer.hikari.pool.HikariPool;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractHikariDAL {

    private HikariPool pool;

    protected Connection getConnection() throws SQLException {
        return pool.getConnection();
    }

    protected AbstractHikariDAL(HikariPool pool) {

        this.pool = pool;
        createTablesIfNotExist();
        updateTables();
    }

    protected abstract void createTablesIfNotExist();
    protected abstract void updateTables();
}
