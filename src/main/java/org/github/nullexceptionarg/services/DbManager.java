package org.github.nullexceptionarg.services;

import org.apache.commons.lang.NotImplementedException;
import org.bukkit.entity.Player;
import org.github.nullexceptionarg.model.PlayerKD;
import org.github.nullexceptionarg.model.Town;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbManager implements IDatabase {

    private static DbManager instance;

    public IDatabase DB = new YmlService();

    protected Map<String, Town> playerTownMap = new HashMap<>();
    protected Map<String, Town> townMap = new HashMap<>();
    protected Map<String, List<String>> pendingInvitesMap = new HashMap<>();


    public static void getInstance() {
        if (instance == null) {
            instance = new DbManager();
        }
    }


    /**
     * Creates user's information if it doesn't exist.
     *
     * @param uuid User UUID
     */
    @Override
    public void createPlayer(String uuid) {
        DB.createPlayer(uuid);
    }

    /**
     * Creates a new town with an owner if it doesn't already exist.
     *
     * @param ply      Bukkit Player entity.
     * @param townName Name of town to create.
     */
    @Override
    public void createTown(Player ply, String townName) {
        DB.createTown(ply, townName);
    }

    /**
     * Create a chunk claim for the player's town.
     *
     * @param plyKD     Kingdom's entity for Players part of a Town.
     * @param claimName Claim name generated with the Player's world and coordinates.
     */
    @Override
    public void createClaim(PlayerKD plyKD, String claimName) {
        DB.createClaim(plyKD, claimName);
    }

    /**
     * Finds a Town by it's name in the database.
     *
     * @param townName Name of the town you want to obtain.
     * @return Town entity if found in database else null
     */
    @Override
    public Town getTown(String townName) {
        return DB.getTown(townName);
    }

    /**
     * Find Kingdom's Player entity from Bukkit's
     *
     * @param ply Bukkit Player entity.
     * @return Kingdom's Player entity.
     */
    @Override
    public PlayerKD getPlayer(Player ply) {
        return DB.getPlayer(ply);
    }

    /**
     * Get the town a player is part of.
     *
     * @param uuid UUID of player you want to obtain the town from.
     * @return Player's Town
     */
    @Override
    public Town getTownfromPlayer(String uuid) {
        return DB.getTownfromPlayer(uuid);
    }

    /**
     * Adds a player to the town.
     *
     * @param uuid     UUID of player you want to add to the town.
     * @param townName Name of down you want to add the Player to.
     */
    @Override
    public void setPlayerTown(String uuid, String townName) {
        DB.setPlayerTown(uuid,townName);
    }

    /**
     * Loads PlayerKD information from Storage engine to RAM.
     *
     * @param ply Bukkit player entity.
     */
    @Override
    public void addPlayerToMap(Player ply) {
        DB.addPlayerToMap(ply);
    }

    /**
     * Unloads player from RAM to Storage engine.
     *
     * @param ply
     */
    @Override
    public void removePlayerfromMap(Player ply) {
        DB.removePlayerfromMap(ply);
    }

    /**
     * @param displayName
     * @param townName
     */
    @Override
    public void addPendingInvite(String displayName, String townName) {
        DB.addPendingInvite(displayName,townName);
    }

    @Override
    public List<String> getPendingInvites(String displayname) { return DB.getPendingInvites(displayname); }

    @Override
    public void removePendingInvite(String displayname,String townName) { DB.removePendingInvite(displayname,townName); }
}
