package ca.ultracrepidarianism.kingdom.services;

import ca.ultracrepidarianism.kingdom.model.KDChunk;
import ca.ultracrepidarianism.kingdom.model.KDClaim;
import ca.ultracrepidarianism.kingdom.model.KDKingdom;
import ca.ultracrepidarianism.kingdom.model.KDPlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Deprecated
public abstract class Database {
    private static Database instance;

    /** player uuid -> KDPlayer **/
    protected Map<String, KDKingdom> kdplayerByUUID = new HashMap<>();
    /** Town Name -> Town **/
    protected Map<String, KDKingdom> townByName = new HashMap<>();
    /** Player UUID -> Town Names **/
    protected Map<String, List<String>> invitesByPlayerUUID = new HashMap<>();

    protected Map<KDChunk, KDKingdom> ClaimedCache = new HashMap<>();

    /**
     * Get or create the database instance
     *
     * @return Database instance
     * @deprecated
     */
    @Deprecated
    public static Database getInstance() {
        if (instance == null) {
            instance = new MySQLService();
        }
        return instance;
    }

    /**
     * Creates user's information if it doesn't exist.
     *
     * @param uuid User UUID
     * @return KDPlayer just created or found
     */
    @Deprecated
    public abstract KDPlayer createPlayer(String uuid);

    /**
     * Creates a new town with an owner if it doesn't already exist.
     *
     * @param ply      Bukkit Player entity.
     * @param townName Name of town to create.
     */
    @Deprecated
    public abstract void createTown(Player ply, String townName);

    /**
     * Remove the town. This will also remove the remaining players from the town.
     *
     * @param town The town to remove
     */
    public abstract void removeTown(KDKingdom town);

    /**
     * Create a chunk claim for the player's town.
     *
     * @param kdKingdom Kingdom's entity for Players part of a Town.
     * @param chunk  KDChunk object generated with the Player's world and coordinates.
     */
    public abstract void createClaim(KDKingdom kdKingdom, KDChunk chunk);

    /**
     * Finds a Town by it's name in the database.
     *
     * @param townName Name of the town you want to obtain.
     * @return Town entity if found in database else null
     */
    public abstract KDKingdom getTown(String townName);

    /**
     * Find Kingdom's Player entity from Bukkit's
     *
     * @param player Bukkit Player entity.
     * @return Kingdom's Player entity.
     */
    public abstract KDPlayer getPlayer(Player player);

    /**
     * Get the town a player is part of.
     *
     * @param   playerUUID UUID of player you want to obtain the town from.
     * @return       Player's Town
     */
    public abstract KDKingdom getTownFromPlayerUUID(String playerUUID);

    /**
     * Adds a player to the town.
     *
     * @param uuid      UUID of player you want to add to the town.
     * @param townName  Name of town you want to set for the Player.
     */
    public abstract void setPlayerTown(String uuid, String townName);

    /**
     *
     * @param uuid UUID of player you want to remove from the town.
     */
    public abstract void removePlayer(KDPlayer uuid);

    /**
     * Loads PlayerKD information from Storage engine to RAM.
     *
     * @param ply Bukkit player entity.
     */
    public abstract void addPlayerToMap(KDPlayer ply);

    /**
     * Unloads player from RAM to Storage engine.
     *
     * @param ply Bukkit player entity.
     */
    public abstract void removePlayerfromMap(Player ply);

    /**
     * Adds a town on the list of pending invites of a player.
     * @param uuid uuid of the player
     * @param townName name of the town
     */
    public abstract void addPendingInvite(String uuid, String townName);

    /**
     *  Allows you to get the current list of pending invites for a player.
     * @param uuid uuid of the player
     * @return returns the list of actual pending invites
     */
    public abstract List<String> getPendingInvites(String uuid);

    /**
     * Allows you to revoke an invitation to a town for a player.
     * @param uuid uuid of the player
     * @param townName name of the town
     */
    public abstract void removePendingInvite(String uuid, String townName);

    /**
     * Get the claim associated with the current chunk given
     * @param c current chunk
     * @return Claim found or NULL if WILDERNESS
     */
    public abstract KDClaim getClaimFromChunk(KDChunk c);
}
