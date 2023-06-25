package ca.ultracrepidarianism.kingdom.database.repositories;

import ca.ultracrepidarianism.kingdom.database.models.KDInvite;
import ca.ultracrepidarianism.kingdom.database.models.KDKingdom;
import ca.ultracrepidarianism.kingdom.database.models.KDPlayer;
import ca.ultracrepidarianism.kingdom.database.models.enums.PermissionLevelEnum;
import ca.ultracrepidarianism.kingdom.utils.HibernateUtil;
import ca.ultracrepidarianism.kingdom.utils.PersistenceUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerRepository extends Repository {
    private final static String TABLE = "players";

    private final Map<String,List<KDInvite>> kdInvitesByPlayerUUID = new HashMap<>();

    public KDPlayer getPlayerByName(final String name) {
        final EntityManager entityManager = HibernateUtil.getEntityManager();

        final TypedQuery<KDPlayer> typedQuery = entityManager.createQuery("from KDPlayer WHERE UPPER(name) LIKE :name", KDPlayer.class);
        typedQuery.setParameter("name", "%"+name+"%");

        return PersistenceUtil.getSingleResultOrNull(typedQuery);
    }

    public KDPlayer getPlayerFromBukkitPlayer(final Player player) {
        return getPlayerById(player.getUniqueId().toString());
    }

    public KDPlayer getPlayerById(final String playerId) {
        return HibernateUtil.getEntityManager().find(KDPlayer.class, playerId);
    }

    public void updatePlayerName(final KDPlayer player, final String name) {
        final EntityManager entityManager = HibernateUtil.getEntityManager();
        entityManager.getTransaction().begin();

        player.setName(name);
        entityManager.merge(player);

        entityManager.getTransaction().commit();
    }

    public KDPlayer createPlayer(final Player player) {
        final EntityManager entityManager = HibernateUtil.getEntityManager();
        entityManager.getTransaction().begin();

        final KDPlayer kdPlayer = new KDPlayer(player.getUniqueId().toString(), player.getName(), null, null);
        entityManager.persist(kdPlayer);

        entityManager.getTransaction().commit();

        return kdPlayer;
    }

    public void kickPlayer(final KDPlayer kdPlayer) {
        final EntityManager entityManager = HibernateUtil.getEntityManager();
        entityManager.getTransaction().begin();

        kdPlayer.setKingdom(null);
        kdPlayer.setPermissionLevel(null);
        entityManager.merge(kdPlayer);

        entityManager.getTransaction().commit();
    }

    public void kickPlayer(final String id) {
        final KDPlayer player = HibernateUtil.getEntityManager().find(KDPlayer.class, id);
        if(player != null){
            kickPlayer(player);
        }
    }


    public List<KDInvite> getPendingInvites(final String playerId) {
        return kdInvitesByPlayerUUID.get(playerId);
    }

    public void addPendingInvite(final KDPlayer inviter, final KDPlayer invitee) {
        final String inviteeUniqueId = invitee.getId();
        final List<KDInvite> invites = kdInvitesByPlayerUUID.getOrDefault(inviteeUniqueId, new ArrayList<>());

        invites.add(new KDInvite(inviter, invitee, inviter.getKingdom()));

        kdInvitesByPlayerUUID.put(inviteeUniqueId, invites);
    }

    public void removePendingInvite(final String playerId, final Long kingdomId) {
        if (kingdomId == null) {
            kdInvitesByPlayerUUID.remove(playerId);
        } else {
            kdInvitesByPlayerUUID.get(playerId);
        }
    }

    public List<KDPlayer>getPlayersForKingdom(KDKingdom kingdom){
        EntityManager entityManager = HibernateUtil.getEntityManager();
        TypedQuery<KDPlayer> query = entityManager.createQuery("FROM KDPlayer WHERE kingdom.id = :kingdomId",KDPlayer.class);
        query.setParameter("kingdomId",kingdom.getId());

        return query.getResultList();
    }


    public void updatePermissionLevelForPlayer(final KDPlayer player, final PermissionLevelEnum permissionLevel) {
        final EntityManager entityManager = HibernateUtil.getEntityManager();
        entityManager.getTransaction().begin();

        player.setPermissionLevel(permissionLevel);
        entityManager.merge(player);

        entityManager.getTransaction().commit();
    }
}
