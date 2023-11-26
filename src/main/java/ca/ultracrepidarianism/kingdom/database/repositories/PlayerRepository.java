package ca.ultracrepidarianism.kingdom.database.repositories;

import ca.ultracrepidarianism.kingdom.database.models.KDInvite;
import ca.ultracrepidarianism.kingdom.database.models.KDKingdom;
import ca.ultracrepidarianism.kingdom.database.models.KDPlayer;
import ca.ultracrepidarianism.kingdom.database.models.enums.PermissionLevelEnum;
import ca.ultracrepidarianism.kingdom.utils.PersistenceUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerRepository extends Repository {
    private final Map<String, List<KDInvite>> kdInvitesByPlayerId = new HashMap<>();

    public KDPlayer getPlayerByName(final String name) {
        final EntityManager entityManager = getEntityManager();

        final TypedQuery<KDPlayer> typedQuery = entityManager.createQuery("from KDPlayer WHERE UPPER(name) LIKE :name", KDPlayer.class);
        typedQuery.setParameter("name", "%" + name + "%");

        return PersistenceUtil.getSingleResultOrNull(typedQuery);
    }

    public KDPlayer getPlayerFromBukkitPlayer(final Player player) {
        return getPlayerById(player.getUniqueId().toString());
    }

    public KDPlayer getPlayerById(final String playerId) {
        return getEntityManager().find(KDPlayer.class, playerId);
    }

    public void updatePlayerName(final KDPlayer player, final String name) {
        try (final EntityManager entityManager = getEntityManager()) {
            entityManager.getTransaction().begin();

            player.setName(name);
            entityManager.merge(player);

            entityManager.getTransaction().commit();
        }
    }

    public KDPlayer createPlayer(final Player player) {
        try (final EntityManager entityManager = getEntityManager()) {
            entityManager.getTransaction().begin();

            final KDPlayer kdPlayer = new KDPlayer(player.getUniqueId().toString(), player.getName(), null, null);
            entityManager.persist(kdPlayer);

            entityManager.getTransaction().commit();

            return kdPlayer;
        }
    }

    public List<KDInvite> getPendingInvites(final String playerId) {
        return kdInvitesByPlayerId.get(playerId);
    }

    public void addPendingInvite(final KDPlayer inviter, final KDPlayer invitee) {
        final String inviteeUniqueId = invitee.getId();
        final List<KDInvite> invites = kdInvitesByPlayerId.getOrDefault(inviteeUniqueId, new ArrayList<>());

        invites.add(new KDInvite(inviter, invitee, inviter.getKingdom()));

        kdInvitesByPlayerId.put(inviteeUniqueId, invites);
    }

    public void removeAllPendingInvites(final String playerId) {
        kdInvitesByPlayerId.remove(playerId);
    }

    public void removePendingInvite(final String playerId, final KDKingdom kingdom) {
        final List<KDInvite> filteredInvites = kdInvitesByPlayerId
                .get(playerId)
                .stream()
                .filter(invite -> invite.getKingdom() != kingdom)
                .toList();
        kdInvitesByPlayerId.replace(playerId, filteredInvites);
    }

    public void updatePermissionLevelForPlayer(final KDPlayer player, final PermissionLevelEnum permissionLevel) {
        try (final EntityManager entityManager = getEntityManager()) {
            entityManager.getTransaction().begin();

            player.setPermissionLevel(permissionLevel);
            entityManager.merge(player);

            entityManager.getTransaction().commit();
        }
    }
}
