package org.github.nullexceptionarg.services;

import org.apache.commons.lang.NotImplementedException;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.github.nullexceptionarg.Kingdom;
import org.github.nullexceptionarg.model.PlayerKD;
import org.github.nullexceptionarg.model.Town;

import java.sql.*;
import java.util.List;
import java.util.logging.Level;

public class MySQLService implements IDatabase {


    public MySQLService(){}

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

    }

    /**
     * Create a chunk claim for the player's town.
     *
     * @param plyKD     Kingdom's entity for Players part of a Town.
     * @param claimName Claim name generated with the Player's world and coordinates.
     */
    @Override
    public void createClaim(PlayerKD plyKD, String claimName) {

    }

    /**
     * Finds a Town by it's name in the database.
     *
     * @param townName Name of the town you want to obtain.
     * @return Town entity if found in database else null
     */
    @Override
    public Town getTown(String townName) {
        return null;
    }

    /**
     * Find Kingdom's Player entity from Bukkit's
     *
     * @param ply Bukkit Player entity.
     * @return Kingdom's Player entity.
     */
    @Override
    public PlayerKD getPlayer(Player ply) {
        return null;
    }

    /**
     * Get the town a player is part of.
     *
     * @param uuid UUID of player you want to obtain the town from.
     * @return Player's Town
     */
    @Override
    public Town getTownfromPlayer(String uuid) {
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

    //Region MySQL Utilities

    private static Connection connectDB() {
        Connection con = null;
        try {
            SqlInfo sql = new SqlInfo();
            String url = "jdbc:mysql://" + sql.IP + ":" + sql.Port + "/" + sql.Database +
                    "?verifyServerCertificate=false&useSSL=true&requireSSL=true";
            con = DriverManager.getConnection(url, sql.username, sql.password);

            return con;
        } catch (SQLException e) {
            Kingdom.getPlugin(Kingdom.class).getLogger().info("Connection error!\n" + e.getMessage());
            e.printStackTrace();
            System.out.println("SQL might not be set up correctly please be sure to modify the config");
            if (con != null)
                try {
                    System.out.println("Crash-------------");
                    con.close();
                } catch (Exception b) {
                }
        }
        return null;
    }


    public static boolean checkDB() {

        JavaPlugin.getPlugin(Kingdom.class).getLogger().warning(" Performing database checkup.");
        Connection con = null;
        try {
            con = connectDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (con == null)
            return false;
        for (String table : SqlTemplate.Tables) {
            if (!checkTableExist(con, table)) {
                JavaPlugin.getPlugin(Kingdom.class).getLogger().warning("Table " + table + " doesn't exist, attempting to create it.");
                if (!generateTable(con, table)) {
                    JavaPlugin.getPlugin(Kingdom.class).getLogger().log(Level.SEVERE, "Table " + table + " could not be created.");
                    return false;
                }
            }
        }

        try {
            con.close();
        } catch (Exception ignored){}
        return true;
    }


    public static boolean checkTableExist(Connection con, String table) {
        try {
            DatabaseMetaData dbm = con.getMetaData();
            ResultSet tables = dbm.getTables(null, null, new SqlInfo().tableprefix+table, null);
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

    public static boolean generateTable(Connection con, String table) {
        PreparedStatement stmt = null;
        try {
            switch (table.toLowerCase()) {
                case "claim":
                    stmt = con.prepareStatement(SqlTemplate.getClaim());
                    break;
                case "town":
                    stmt = con.prepareStatement(SqlTemplate.getTown());
                    break;
                case "playerkd":
                    stmt = con.prepareStatement(SqlTemplate.getPlayer());
                    break;
                case "townuser":
                    stmt = con.prepareStatement(SqlTemplate.getTownPlayer());
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JavaPlugin.getPlugin(Kingdom.class).getLogger().log(Level.SEVERE, "Table " + table + " failed to prepare for generation.");
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

}
