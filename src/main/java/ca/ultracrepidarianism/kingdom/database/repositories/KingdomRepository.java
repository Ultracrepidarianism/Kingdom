package ca.ultracrepidarianism.kingdom.database.repositories;

import ca.ultracrepidarianism.kingdom.database.models.KDKingdom;
import ca.ultracrepidarianism.kingdom.database.models.KDPlayer;
import ca.ultracrepidarianism.kingdom.database.models.enums.PermissionLevelEnum;
import ca.ultracrepidarianism.kingdom.utils.PersistenceUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class KingdomRepository extends Repository {

    public KDKingdom getKingdomByPlayerId(final String playerUUID) {
        final EntityManager entityManager = getEntityManager();
        final TypedQuery<KDKingdom> query = entityManager.createQuery("SELECT k FROM KDPlayer p inner join KDKingdom k on k.id = p.kingdom.id WHERE p.id = :playerId", KDKingdom.class);
        query.setParameter("playerId", playerUUID);
        return PersistenceUtil.getSingleResultOrNull(query);
    }

    public void createKingdom(final KDPlayer player, final String kingdomName) {
        try (final EntityManager entityManager = getEntityManager()) {
            entityManager.getTransaction().begin();

            final KDKingdom kingdom = new KDKingdom(kingdomName, player);
            player.setKingdom(kingdom);
            player.setPermissionLevel(PermissionLevelEnum.OWNER);
            entityManager.persist(kingdom);

            entityManager.getTransaction().commit();
        }
    }

    public void disbandKingdom(final KDKingdom kdKingdom) {
        try (final EntityManager entityManager = getEntityManager()) {
            entityManager.getTransaction().begin();

            kdKingdom.getMembers().forEach(x -> {
                x.setKingdom(null);
                x.setPermissionLevel(null);
            });
            entityManager.remove(kdKingdom);

            entityManager.getTransaction().commit();
        }
    }

    public void setKingdomForPlayer(final KDKingdom kingdom, final KDPlayer player) {
        setKingdomForPlayerWithPermission(kingdom, player, PermissionLevelEnum.MEMBER);
    }

    public void setKingdomForPlayerWithPermission(final KDKingdom kingdom, final KDPlayer player, final PermissionLevelEnum permissionLevelEnum) {
        try (final EntityManager entityManager = getEntityManager()) {
            entityManager.getTransaction().begin();

            player.setKingdom(kingdom);
            player.setPermissionLevel(permissionLevelEnum);

            entityManager.persist(player);

            entityManager.getTransaction().commit();
        }
    }

    public void kickPlayer(final KDPlayer kdPlayer) {
        try (final EntityManager entityManager = getEntityManager()) {
            entityManager.getTransaction().begin();

            kdPlayer.setKingdom(null);
            kdPlayer.setPermissionLevel(null);
            entityManager.merge(kdPlayer);

            entityManager.getTransaction().commit();
        }
    }
}
