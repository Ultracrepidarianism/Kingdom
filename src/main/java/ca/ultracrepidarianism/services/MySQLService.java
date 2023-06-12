package ca.ultracrepidarianism.services;

import ca.ultracrepidarianism.Kingdom;
import ca.ultracrepidarianism.model.KDChunk;
import ca.ultracrepidarianism.model.KDClaim;
import ca.ultracrepidarianism.services.sqlutil.SqlTemplate;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ca.ultracrepidarianism.model.KDPlayer;
import ca.ultracrepidarianism.model.KDTown;
import ca.ultracrepidarianism.services.sqlutil.SqlInfo;

import java.sql.*;
import java.util.List;
import java.util.logging.Level;

public class MySQLService extends Database {
    private Connection connection;
    private final SqlInfo sqlInfo = new SqlInfo();
    private final SqlTemplate sqlTemplate = new SqlTemplate();

    protected MySQLService() {
        initializeDatabase();
    }

    /**
     * Get or create the database connection
     *
     * @return Database Connection
     */
    public Connection getConnection() {
        if (connection != null) {
            return connection;
        }

        final String url = "jdbc:mysql://" + sqlInfo.getIP() + ":" + sqlInfo.getPort() + "/" + sqlInfo.getDatabase() +
                "?verifyServerCertificate=false&useSSL=true&requireSSL=true";
        try {
            connection = DriverManager.getConnection(url, sqlInfo.getUsername(), sqlInfo.getPassword());
        } catch (SQLException e) {
            Kingdom.getPlugin(Kingdom.class).getLogger().info("Connection error!\n" + e.getMessage());
        }

        return connection;
    }

    /**
     * Creates user's information if it doesn't exist.
     *
     * @param uuid User UUID
     */
    @Override
    public void createPlayer(String uuid) {

    }

    /**
     * Creates a new town with an owner if it doesn't already exist.
     *
     * @param ply      Bukkit Player entity.
     * @param townName Name of town to create.
     */
    @Override
    public void createTown(Player ply, String townName) {
        Connection connection = getConnection();
        String query = "INSERT INTO "+ sqlInfo.getTablePrefix() + "";
        query += """
            
        """;

        try {
            connection.prepareStatement(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create a chunk claim for the player's town.
     *
     * @param plyKD     Kingdom's entity for Players part of a Town.
     * @param chunk     chunk to be claimed
     */
    @Override
    public void createClaim(KDPlayer plyKD, KDChunk chunk) {

    }

    /**
     * Finds a Town by it's name in the database.
     *
     * @param townName Name of the town you want to obtain.
     * @return Town entity if found in database else null
     */
    @Override
    public KDTown getTown(String townName) {
        return null;
    }

    /**
     * Find Kingdom's Player entity from Bukkit's
     *
     * @param ply Bukkit Player entity.
     * @return Kingdom's Player entity.
     */
    @Override
    public KDPlayer getPlayer(Player ply) {
        return null;
    }

    /**
     * Get the town a player is part of.
     *
     * @param uuid UUID of player you want to obtain the town from.
     * @return Player's Town
     */
    @Override
    public KDTown getTownfromPlayer(String uuid) {
        Connection connection = getConnection();

        StringBuilder query = new StringBuilder("select * from ");
        query.append(" player ");
        query.append(" where ");

        try {
            connection.prepareStatement("""
                        SELECT * FROM kingdom_town kdt
                        where kdt.
                    """);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Adds a player to the town.
     *
     * @param uuid     UUID of player you want to add to the town.
     * @param townName Name of down you want to add the Player to.
     */
    @Override
    public void setPlayerTown(String uuid, String townName) {

    }

    @Override
    public void removePlayerTown(String uuid) {

    }

    /**
     * Loads PlayerKD information from Storage engine to RAM.
     *
     * @param ply Bukkit player entity.
     */
    @Override
    public void addPlayerToMap(Player ply) {

    }

    /**
     * Unloads player from RAM to Storage engine.
     *
     * @param ply
     */
    @Override
    public void removePlayerfromMap(Player ply) {

    }

    /**
     * @param displayName
     * @param townName
     */
    @Override
    public void addPendingInvite(String displayName, String townName) {

    }

    @Override
    public List<String> getPendingInvites(String uuid) {
        return null;
    }

    @Override
    public void removePendingInvite(String uuid, String townName) {

    }

    public boolean checkDb() {
        JavaPlugin.getPlugin(Kingdom.class).getLogger().log(Level.FINE, " Performing database checkup.");

        Connection connection = getConnection();
        for (String table : new SqlTemplate().getTables()) {
            if (!checkTableExist(table)) {
                JavaPlugin.getPlugin(Kingdom.class).getLogger().warning("Table " + table + " doesn't exist, attempting to create it.");
                if (!generateTable(table)) {
                    JavaPlugin.getPlugin(Kingdom.class).getLogger().log(Level.SEVERE, "Table " + table + " could not be created.");
                    return false;
                }
            }
        }

        return true;
    }

    public boolean checkTableExist(String table) {
        try {
            DatabaseMetaData dbm = getConnection().getMetaData();
            ResultSet tables = dbm.getTables(null, null, sqlInfo.getTablePrefix() + table, null);
            if (tables.next()) {
                tables.close();
                return true;
            } else {
                tables.close();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean generateTable(String table) {
        PreparedStatement stmt = null;
        SqlTemplate temp = new SqlTemplate();
        final Connection connection = getConnection();
        try {
            switch (table.toLowerCase()) {
                case "claim":
                    stmt = connection.prepareStatement(temp.getClaim());
                    break;
                case "town":
                    stmt = connection.prepareStatement(temp.getTown());
                    break;
                case "player":
                    stmt = connection.prepareStatement(temp.getPlayer());
                    break;
                case "townuser":
                    stmt = connection.prepareStatement(temp.getTownPlayer());
                    break;
                default:
                    JavaPlugin.getPlugin(Kingdom.class).getLogger().severe(table + " is not a valid table");
                    return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JavaPlugin.getPlugin(Kingdom.class).getLogger().severe("Table " + table + " failed to prepare for generation.");
            return false;
        }

        try {
            stmt.execute();
            JavaPlugin.getPlugin(Kingdom.class).getLogger().info("Table "+table+" created sucessfully");
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            JavaPlugin.getPlugin(Kingdom.class).getLogger().log(Level.SEVERE, "Table " + table + " failed to generate.");
            return false;
        }
    }

    /**
     * Initialize the database
     *
     * @return Whether the database properly initialized or not
     */
    private boolean initializeDatabase() {
        for (String table : sqlTemplate.getTables()) {
            if (checkTableExist(table)) {
                continue;
            }

            JavaPlugin.getPlugin(Kingdom.class).getLogger().warning("Table " + table + " doesn't exist, attempting to create it.");
            if (!generateTable(table)) {
                JavaPlugin.getPlugin(Kingdom.class).getLogger().log(Level.SEVERE, "Table " + table + " could not be created.");
                return false;
            }
        }

        return true;
    }

    @Override
    public KDClaim getClaimFromChunk(KDChunk c) {
        return null;
    }
}
