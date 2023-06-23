package ca.ultracrepidarianism.kingdom.database.dal.sql;

import ca.ultracrepidarianism.kingdom.database.dal.DAL;
import com.zaxxer.hikari.pool.HikariPool;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class SQLDAL extends AbstractHikariDAL implements DAL {
    public SQLDAL(HikariPool hikariPool) {
        super(hikariPool);
    }

    @Override
    public void insert(String table, Map<String, String> properties) throws SQLException {
        StringBuilder query = new StringBuilder("INSERT INTO `").append(table).append("`");
        StringBuilder columns = new StringBuilder("(");
        StringBuilder values = new StringBuilder("VALUES (");
        for (String key : properties.keySet()) {
            columns.append("`").append(key).append("`").append(",");
            values.append("`").append(properties.get(key)).append("`").append(",");
        }
        columns.replace(columns.length() - 1, columns.length() - 1, ")");
        values.replace(values.length() - 1, values.length() - 1, ")");
        query.append(columns).append(values);
        getConnection().prepareStatement(query.toString()).executeQuery();
    }

    @Override
    public <T> void update(String table, Map<String, String> properties, String where) throws SQLException {
        StringBuilder query = new StringBuilder("UPDATE `" + table).append("` SET ");
        for (String key : properties.keySet()) {
            query.append(key).append(" = ").append(properties.get(key)).append(", ");
        }
        if (!where.isEmpty() && !where.isBlank())
            query.append("WHERE ").append(where);

        getConnection().prepareStatement(query.toString()).executeQuery();
    }

    @Override
    public <T> void delete(String table, String id) throws SQLException {
        getConnection().prepareStatement("DELETE FROM `" + table + "` WHERE id=`" + id + "`").executeQuery();
    }

    @Override
    public ResultSet get(String table, String column, String value) throws SQLException {
        return getConnection().prepareStatement("SELECT * FROM `" + table + "` WHERE " + column + "='" + value + "'").executeQuery();

    }

    @Override
    public ResultSet filteredGet(String table, String where) throws SQLException {
        return getConnection().prepareStatement("SELECT * FROM `" + table + "` WHERE " + where).executeQuery();
    }
}
