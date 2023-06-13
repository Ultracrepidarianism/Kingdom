package ca.ultracrepidarianism.services;

import ca.ultracrepidarianism.Kingdom;
import ca.ultracrepidarianism.model.KDChunk;
import ca.ultracrepidarianism.model.KDClaim;
import ca.ultracrepidarianism.model.KDPlayer;
import ca.ultracrepidarianism.model.KDTown;
import ca.ultracrepidarianism.model.enums.PermissionLevelEnum;
import ca.ultracrepidarianism.services.sqlutil.SqlInfo;
import ca.ultracrepidarianism.utils.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.bukkit.entity.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

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
    @Transactional
    public void createPlayer(String uuid) {
        HibernateUtil.doInTransaction(session -> {
            KDPlayer player = new KDPlayer(uuid,null,null);
            session.persist(player);
        });
    }

    /**
     * Creates a new town with an owner if it doesn't already exist.
     *
     * @param ply      Bukkit Player entity.
     * @param townName Name of town to create.
     */
    @Override
    public void createTown(Player ply, String townName) {
        HibernateUtil.doInTransaction(session -> {

            KDPlayer kdP = session.find(KDPlayer.class, ply.getUniqueId().toString());
            if(kdP == null){
                kdP = new KDPlayer(ply.getUniqueId().toString(),null, null);
            }

            KDTown town = new KDTown(townName, kdP);
            kdP.setTown(town);

            session.persist(kdP);
        });

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
        EntityManager entityManager = HibernateUtil.getEntityManager();

        TypedQuery<KDTown> typedQuery = entityManager.createQuery("FROM KDTown where townName = :townName", KDTown.class);
        typedQuery.setParameter("townName", townName);

        return HibernateUtil.getSingleResultOrNull(typedQuery);
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

    @Override
    public KDClaim getClaimFromChunk(KDChunk c) {
        return null;
    }

    /**
     * Initialize the database
     *
     * @return Whether the database properly initialized or not
     */
    private boolean initializeDatabase() {
        return true;
    }

    private <T> T firstOrDefault(Query)
}
