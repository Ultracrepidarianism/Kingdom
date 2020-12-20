package org.github.nullexceptionarg.services;

import org.apache.commons.lang.NotImplementedException;
import org.bukkit.entity.Player;
import org.github.nullexceptionarg.model.PlayerKD;
import org.github.nullexceptionarg.model.Town;

import java.util.List;

public interface IDatabase {

    /**
     * Creates user's information if it doesn't exist.
     *
     * @param uuid User UUID
     */
    abstract void createPlayer(String uuid);

    /**
     * Creates a new town with an owner if it doesn't already exist.
     *
     * @param ply      Bukkit Player entity.
     * @param townName Name of town to create.
     */
    abstract void createTown(Player ply, String townName);

    /**
     * Create a chunk claim for the player's town.
     *
     * @param plyKD     Kingdom's entity for Players part of a Town.
     * @param claimName Claim name generated with the Player's world and coordinates.
     */
    abstract void createClaim(PlayerKD plyKD, String claimName);

    /**
     * Finds a Town by it's name in the database.
     *
     * @param townName Name of the town you want to obtain.
     * @return Town entity if found in database else null
     */
    abstract Town getTown(String townName);

    /**
     * Find Kingdom's Player entity from Bukkit's
     *
     * @param ply Bukkit Player entity.
     * @return Kingdom's Player entity.
     */
    abstract PlayerKD getPlayer(Player ply);

    /**
     * Get the town a player is part of.
     *
     * @param   uuid UUID of player you want to obtain the town from.
     * @return       Player's Town
     */
    abstract Town getTownfromPlayer(String uuid);

    /**
     * Adds a player to the town.
     *
     * @param uuid      UUID of player you want to add to the town.
     * @param townName  Name of town you want to set for the Player.
     */
    abstract void setPlayerTown(String uuid, String townName);

    /**
     *
     * @param uuid UUID of player you want to remove from the town.
     */
    abstract void removePlayerTown(String uuid);

    /**
     * Loads PlayerKD information from Storage engine to RAM.
     *
     * @param ply Bukkit player entity.
     */
    abstract void addPlayerToMap(Player ply);

    /**
     * Unloads player from RAM to Storage engine.
     *
     * @param ply
     */
    abstract void removePlayerfromMap(Player ply);

    /**
     * Adds a town on the list of pending invites of a player.
     * @param uuid uuid of the player
     * @param townName name of the town
     */
    abstract void addPendingInvite(String uuid, String townName);

    /**
     *  Allows you to get the current list of pending invites for a player.
     * @param uuid uuid of the player
     * @return returns the list of actual pending invites
     */
    abstract List<String> getPendingInvites(String uuid);

    /**
     * Allows you to revoke an invitation to a town for a player.
     * @param uuid uuid of the player
     * @param townName name of the town
     */
    abstract void removePendingInvite(String uuid, String townName);
}
