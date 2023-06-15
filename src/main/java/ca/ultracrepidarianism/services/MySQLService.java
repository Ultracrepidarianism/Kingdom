package ca.ultracrepidarianism.services;

import ca.ultracrepidarianism.Kingdom;
import ca.ultracrepidarianism.model.KDChunk;
import ca.ultracrepidarianism.model.KDClaim;
import ca.ultracrepidarianism.model.KDKingdom;
import ca.ultracrepidarianism.model.KDPlayer;
import ca.ultracrepidarianism.model.enums.PermissionLevelEnum;
import ca.ultracrepidarianism.services.sqlutil.SqlInfo;
import ca.ultracrepidarianism.utils.HibernateUtil;
import ca.ultracrepidarianism.utils.PersistenceUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class MySQLService extends Database {
    private Connection connection;
    private final SqlInfo sqlInfo = new SqlInfo();

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
    public KDPlayer createPlayer(String uuid) {
//        KDPlayer player = HibernateUtil.getEntityManager().find(KDPlayer.class,uuid);
//        if(player == null){
//            player = new KDPlayer(uuid,null,null)
//            HibernateUtil.doInTransaction(session -> {
//                session.persist(player);
//            });
//        }
//        return player;
        return null;
    }

    /**
     * Creates a new town with an owner if it doesn't already exist.
     *
     * @param ply      Bukkit Player entity.
     * @param townName Name of town to create.
     */
    @Override
    public void createTown(Player ply, String townName) {
        PersistenceUtil.doInTransaction(session -> {
            KDKingdom town = new KDKingdom(townName, null);
            KDPlayer kdP = session.find(KDPlayer.class, ply.getUniqueId().toString());
            if (kdP == null) {
                kdP = new KDPlayer(ply.getUniqueId().toString(), PermissionLevelEnum.OWNER, null);

            }
            town.setOwner(kdP);
            kdP.setKingdom(town);

            session.persist(kdP);
        });
    }

    @Override
    public void removeTown(KDKingdom kdKingdom) {
//        PersistenceUtil.doInTransaction(session -> {
////            kdKingdom.getMembers().forEach(session::remove);
////            kdKingdom.getClaims().forEach(session::remove);
//            session.remove(kdKingdom);
//            session.flush();
//        });
    }

    /**
     * Create a chunk claim for the player's town.
     *
     * @param kdKingdom Kingdom's entity for Players part of a Town.
     * @param chunk  chunk to be claimed
     */
    @Override
    public void createClaim(KDKingdom kdKingdom, KDChunk chunk) {
        PersistenceUtil.doInTransaction(session -> {
            KDClaim claim = new KDClaim(chunk, kdKingdom);
            session.persist(claim);
        });
    }

    /**
     * Finds a Town by it's name in the database.
     *
     * @param townName Name of the town you want to obtain.
     * @return Town entity if found in database else null
     */
    @Override
    public KDKingdom getTown(String townName) {
        EntityManager entityManager = HibernateUtil.getEntityManager();

        TypedQuery<KDKingdom> typedQuery = entityManager.createQuery("FROM KDKingdom where kingdomName = :townName", KDKingdom.class);
        typedQuery.setParameter("townName", townName);

        return PersistenceUtil.getSingleResultOrNull(typedQuery);
    }

    /**
     * Find Kingdom's Player entity from Bukkit's
     *
     * @param player Bukkit Player entity.
     * @return Kingdom's Player entity.
     */
    @Override
    public KDPlayer getPlayer(Player player) {
        return HibernateUtil.getEntityManager().find(KDPlayer.class, player.getUniqueId().toString());
    }

    /**
     * Get the town a player is part of.
     *
     * @param playerUUID UUID of player you want to obtain the town from.
     * @return Player's Town
     */
    @Override
    public KDKingdom getTownFromPlayerUUID(String playerUUID) {
        final EntityManager entityManager = HibernateUtil.getEntityManager();
        final TypedQuery<KDKingdom> typedQuery = entityManager.createQuery("from KDKingdom where :playerUUID in members", KDKingdom.class);
        typedQuery.setParameter("playerUUID", playerUUID);

        return PersistenceUtil.getSingleResultOrNull(typedQuery);
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
    public void removePlayer(KDPlayer kdPlayer) {
        PersistenceUtil.doInTransaction(session -> {
            session.remove(kdPlayer);
        });
    }

    /**
     * Loads PlayerKD information from Storage engine to RAM.
     *
     * @param ply Bukkit player entity.
     */
    @Override
    public void addPlayerToMap(KDPlayer ply) {

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

    @Override
    public KDClaim getClaimFromChunk(KDChunk c) {
        EntityManager entityManager = HibernateUtil.getEntityManager();

        TypedQuery<KDClaim> typedQuery = entityManager.createQuery("FROM KDClaim where chunk.world = :world AND chunk.x = :x AND chunk.z = :z", KDClaim.class);
        typedQuery.setParameter("world", c.getWorld());
        typedQuery.setParameter("x", c.getX());
        typedQuery.setParameter("z", c.getZ());

        return PersistenceUtil.getSingleResultOrNull(typedQuery);
    }

    /**
     * Initialize the database
     *
     * @return Whether the database properly initialized or not
     */
    private boolean initializeDatabase() {
        HibernateUtil.getSessionFactory();
        return true;
    }
}
