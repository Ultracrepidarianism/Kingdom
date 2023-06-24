package ca.ultracrepidarianism.kingdom.database.dal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public interface DAL {

    //Returns Id of newlyCreated Object as string

    boolean getContext();
    Connection getConnection() throws SQLException;
    String insert(String table, Map<String, String> properties) throws SQLException;

    void update(String table, Map<String, String> properties, String where) throws SQLException;

    void delete(String table, String id) throws SQLException;

    ResultSet get(String table, String column, String value) throws SQLException;

    ResultSet filteredGet(String table, String where) throws SQLException;

    void createTablesIfNotExist() throws SQLException;

}
