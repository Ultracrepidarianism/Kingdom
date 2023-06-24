package ca.ultracrepidarianism.kingdom.database.dal.sql;

import ca.ultracrepidarianism.kingdom.database.dal.DAL;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class SQLDAL extends AbstractHikariDAL implements DAL {
    public SQLDAL(HikariDataSource hikariDS) {
        super(hikariDS);
    }

    @Override
    public Connection getConnection() throws SQLException {
        if(getContext())
            return cntx;

        return null;
    }

    public boolean getContext() {

        try {
            if (cntx == null || cntx.isClosed() || !cntx.isValid(1)) {

                if (cntx != null && !cntx.isClosed()) {

                    try {

                        cntx.close();

                    } catch (SQLException e) {
                        /*
                         * We're disposing of an old stale connection just be nice to the GC as well as
                         * mysql, so ignore the error as there's nothing we can do if it fails
                         */
                    }
                    cntx = null;
                }

                cntx = dataSource.getConnection();

                return cntx != null && !cntx.isClosed();
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public String insert(final String table, final Map<String, String> properties) throws SQLException {
        final StringBuilder query = new StringBuilder("INSERT INTO `").append(table).append("` ");
        final StringBuilder columns = new StringBuilder("(");
        final StringBuilder values = new StringBuilder("VALUES (");

        for (final Map.Entry<String, String> entry : properties.entrySet()) {
            columns.append("`").append(entry.getKey()).append("`").append(",");
            values.append("'").append(entry.getValue()).append("'").append(",");
        }

        columns.replace(columns.length() - 1, columns.length(), ")");
        values.replace(values.length() - 1, values.length(), ")");

        query.append(columns).append(values);

        try (final Statement statement = getConnection().createStatement()) {
            statement.executeUpdate(query.toString(), Statement.RETURN_GENERATED_KEYS);

            final ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                return result.getString(1);
            }

            return null;
        }
    }

    @Override
    public void update(String table, Map<String, String> properties, String where) throws SQLException {
        StringBuilder query = new StringBuilder("UPDATE `" + table).append("` SET ");
        for (Map.Entry<String,String> entry : properties.entrySet()) {
            query.append(entry.getKey()).append(" = ").append(entry.getValue()).append(", ");
        }
        if (!where.isEmpty() && !where.isBlank()) {
            query.append("WHERE ").append(where);
        }

        getConnection().prepareStatement(query.toString()).executeQuery();
    }

    @Override
    public void delete(String table, String id) throws SQLException {
        getConnection().prepareStatement("DELETE FROM " + table + " WHERE id='" + id + "'").executeQuery();
    }

    @Override
    public ResultSet get(String table, String column, String value) throws SQLException {
            return getConnection().prepareStatement("SELECT * FROM " + table + " WHERE " + column + "='" + value + "'").executeQuery();

    }

    @Override
    public ResultSet filteredGet(String table, String where) throws SQLException {
            return getConnection().prepareStatement("SELECT * FROM " + table + " WHERE " + where).executeQuery();
    }

    @Override
    public void createTablesIfNotExist() {
        try (final Statement statement = getConnection().createStatement()) {
            statement.execute("SET FOREIGN_KEY_CHECKS = 0");
            statement.execute("""
                CREATE TABLE IF NOT EXISTS claims (
                  id bigint unsigned NOT NULL AUTO_INCREMENT,
                  x int NOT NULL,
                  z int NOT NULL,
                  world varchar(255) NOT NULL,
                  kingdomId bigint unsigned NOT NULL,
                  PRIMARY KEY (id),
                  UNIQUE KEY world_x_z (world,x,z),
                  KEY FK_KINGDOM_CLAIM_idx (kingdomId),
                  CONSTRAINT FK_KINGDOM_CLAIM FOREIGN KEY (kingdomId) REFERENCES kingdoms (id) ON DELETE CASCADE
                ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
                """);
            statement.execute("""
                CREATE TABLE IF NOT EXISTS kingdoms (
                  id bigint unsigned NOT NULL AUTO_INCREMENT,
                  name varchar(255) DEFAULT NULL,
                  ownerId varchar(255) DEFAULT NULL,
                  PRIMARY KEY (id),
                  UNIQUE KEY ownerId_UNIQUE (ownerId),
                  UNIQUE KEY name (name)
                ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
                """);
            statement.execute("""
                CREATE TABLE IF NOT EXISTS players (
                  id varchar(255) NOT NULL,
                  permissionLevel varchar(255) DEFAULT NULL,
                  kingdomId bigint unsigned DEFAULT NULL,
                  PRIMARY KEY (id),
                  KEY FK_KINGDOM_PLAYER_idx (kingdomId),
                  CONSTRAINT FK_KINGDOM_PLAYER FOREIGN KEY (kingdomId) REFERENCES kingdoms (id) ON DELETE CASCADE
                ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
            """);
            statement.execute("SET FOREIGN_KEY_CHECKS = 0");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateTables(){

    }
}
