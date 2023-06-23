package ca.ultracrepidarianism.kingdom.services;

import ca.ultracrepidarianism.kingdom.database.repositories.ClaimRepository;
import ca.ultracrepidarianism.kingdom.database.repositories.KingdomRepository;
import ca.ultracrepidarianism.kingdom.database.repositories.PlayerRepository;
import ca.ultracrepidarianism.kingdom.model.KDChunk;
import ca.ultracrepidarianism.kingdom.model.KDClaim;
import ca.ultracrepidarianism.kingdom.model.KDKingdom;
import ca.ultracrepidarianism.kingdom.model.KDPlayer;
import ca.ultracrepidarianism.kingdom.model.enums.PermissionLevelEnum;
import ca.ultracrepidarianism.kingdom.Kingdom;
import ca.ultracrepidarianism.kingdom.services.sqlutil.SqlInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@Deprecated
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
     * @deprecated use {@link PlayerRepository#createPlayer} from new DB system instead.
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
     * @deprecated use {@link KingdomRepository#createKingdom} from new DB system instead.
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

    /**
     * @param kdKingdom The town to remove
     * @deprecated use {@link KingdomRepository#removeKingdom} from new DB system instead.
     */
    @Override
    public void removeTown(KDKingdom kdKingdom) {
//        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//        if (sessionFactory.getCurrentSession() == null) {
//            sessionFactory.openSession();
//        }

        Query query = HibernateUtil.getEntityManager().createQuery("DELETE FROM KDKingdom where id = :id");
        query.setParameter("id", kdKingdom.getId());

//        PersistenceUtil.doInTransaction(session -> {
////            kdKingdom.getMembers().forEach(session::remove);
////            kdKingdom.getClaims().forEach(session::remove);
////            kdKingdom.getMembers().remove(kdKingdom.getOwner());
//            kdKingdom.getMembers().clear();
//            kdKingdom.getClaims().clear();
//            session.remove(kdKingdom);
//        });
    }

    /**
     * Create a chunk claim for the player's town.
     *
     * @param kdKingdom Kingdom's entity for Players part of a Town.
     * @param chunk     chunk to be claimed
     * @see ClaimRepository#createClaim
     * @deprecated use {@link ClaimRepository#createClaim} from new DB system instead.
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
     * @deprecated use {@link KingdomRepository#getKindom(Long)} from new DB system instead.
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
     * @deprecated use {@link PlayerRepository#getPlayer} from new DB system instead.
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
     * @deprecated use {@link KingdomRepository#getPlayerTown} from new DB system instead.
     */
    @Override
    public KDKingdom getTownFromPlayerUUID(String playerUUID) {
        final EntityManager entityManager = HibernateUtil.getEntityManager();
        final TypedQuery<KDKingdom> typedQuery = entityManager.createQuery("from KDKingdom where :playerUUID in (members)", KDKingdom.class);
        typedQuery.setParameter("playerUUID", playerUUID);

        return PersistenceUtil.getSingleResultOrNull(typedQuery);
    }

    /**
     * Adds a player to the town.
     *
     * @param uuid     UUID of player you want to add to the town.
     * @param townName Name of down you want to add the Player to.
     * @deprecated use {@link PlayerRepository#setPlayerTown} from new DB system instead.
     */
    @Override
    public void setPlayerTown(String uuid, String townName) {

    }

    /**
     * @param kdPlayer UUID of player you want to remove from the town.
     * @deprecated use {@link PlayerRepository#removePlayer(Player)} from new DB system instead.
     */
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

    /**
     * @param c current chunk
     * @return
     * @deprecated use {@link ClaimRepository#getClaimFromChunk} from new DB system instead.
     */
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
