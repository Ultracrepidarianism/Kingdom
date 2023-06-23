package ca.ultracrepidarianism.kingdom.database.dal.sql;

import ca.ultracrepidarianism.kingdom.database.dal.DAL;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariPool;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class SQLDAL extends AbstractHikariDAL implements DAL {
    public SQLDAL(HikariDataSource hikariDS) {
        super(hikariDS);
    }

    @Override
    public String insert(String table, Map<String, String> properties) throws SQLException {
        StringBuilder query = new StringBuilder("INSERT INTO `").append(table).append("` ");
        StringBuilder columns = new StringBuilder("(");
        StringBuilder values = new StringBuilder("VALUES (");
        for (Map.Entry<String,String> entry : properties.entrySet()) {
            columns.append("`").append(entry.getKey()).append("`").append(",");
            values.append("'").append(entry.getValue()).append("'").append(",");
        }
        columns.replace(columns.length() - 1, columns.length(), ")");
        values.replace(values.length() - 1, values.length(), ")");
        query.append(columns).append(values);
        Statement s = getConnection().createStatement();
        s.executeUpdate(query.toString(),Statement.RETURN_GENERATED_KEYS);
        ResultSet result = s.getGeneratedKeys();
        String test = null;
        if(result.next())
            test = result.getString(1);
        s.close();
        return test;
    }

    @Override
    public void update(String table, Map<String, String> properties, String where) throws SQLException {
        StringBuilder query = new StringBuilder("UPDATE `" + table).append("` SET ");
        for (Map.Entry<String,String> entry : properties.entrySet()) {
            query.append(entry.getKey()).append(" = ").append(entry.getValue()).append(", ");
        }
        if (!where.isEmpty() && !where.isBlank())
            query.append("WHERE ").append(where);

        getConnection().prepareStatement(query.toString()).executeQuery();
    }

    @Override
    public void delete(String table, String id) throws SQLException {
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

    @Override
    public void createTablesIfNotExist() {
        try{
            Statement statement = getConnection().createStatement();
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

        }catch (SQLException e){
            //auugh
        }

    }

    @Override
    public void updateTables(){

    }
}
