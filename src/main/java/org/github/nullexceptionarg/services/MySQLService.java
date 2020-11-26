package org.github.nullexceptionarg.services;

import org.apache.commons.lang.NotImplementedException;
import org.bukkit.entity.Player;
import org.github.nullexceptionarg.Kingdom;
import org.github.nullexceptionarg.model.PlayerKD;
import org.github.nullexceptionarg.model.Town;

import java.sql.*;

public class MySQLService implements IDatabase {

    private static Connection connectDB() {
        Connection con = null;
        try {
            String url = "jdbc:mysql://"+ Kingdom.getPlugin(Kingdom.class).getConfig().getString("DB.address")+":"+ Kingdom.getPlugin(Kingdom.class).getConfig().getInt("DB.port")+"/"+ Kingdom.getPlugin(Kingdom.class).getConfig().getString("DB.database")+
                    "?verifyServerCertificate=false"+
                    "&useSSL=true"+
                    "&requireSSL=true";
            System.out.println(url);
            con = DriverManager.getConnection(url,  Kingdom.getPlugin(Kingdom.class).getConfig().getString("DB.username"),  Kingdom.getPlugin(Kingdom.class).getConfig().getString("DB.password"));

            return con;
        } catch (SQLException e) {
            Kingdom.getPlugin(Kingdom.class).getLogger().info("Connection error!\n" + e.getMessage());
            e.printStackTrace();
            System.out.println("SQL might not be set up correctly please be sure to modify the config");
            if (con != null)
                try
                {
                    System.out.println("Crash-------------");
                    con.close();
                }
                catch (Exception b){}
        }
        return null;
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
}
