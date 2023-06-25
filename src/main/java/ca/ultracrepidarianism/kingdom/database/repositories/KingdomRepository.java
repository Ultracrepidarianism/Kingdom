package ca.ultracrepidarianism.kingdom.database.repositories;

import ca.ultracrepidarianism.kingdom.database.models.KDKingdom;
import ca.ultracrepidarianism.kingdom.database.models.KDPlayer;
import ca.ultracrepidarianism.kingdom.database.models.enums.PermissionLevelEnum;
import ca.ultracrepidarianism.kingdom.utils.HibernateUtil;
import ca.ultracrepidarianism.kingdom.utils.PersistenceUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.apache.commons.lang3.NotImplementedException;

public class KingdomRepository extends Repository {
    private final static String TABLE = "kingdoms";

    public KDKingdom getKingdomByPlayerId(final String playerUUID) {
        final EntityManager entityManager = HibernateUtil.getEntityManager();
        final TypedQuery<KDKingdom> query = entityManager.createQuery("SELECT k FROM KDPlayer p inner join KDKingdom k on k.id = p.kingdom.id WHERE p.id = :userId", KDKingdom.class);
        query.setParameter("userId", playerUUID);
        return PersistenceUtil.getSingleResultOrNull(query);
    }

    public void createKingdom(final KDPlayer player, final String kingdomName) {
        final EntityManager entityManager = HibernateUtil.getEntityManager();

        entityManager.getTransaction().begin();

        final KDKingdom kingdom = new KDKingdom(kingdomName, player);
        player.setKingdom(kingdom);
        player.setPermissionLevel(PermissionLevelEnum.OWNER);
        entityManager.persist(player);

        entityManager.getTransaction().commit();

    }

    public void disbandKingdom(final KDKingdom kdKingdom) {
        throw new NotImplementedException();
    }

    public void setKingdomForPlayer(final KDKingdom kingdom, final KDPlayer player) {
        setKingdomForPlayerWithPermission(kingdom, player, PermissionLevelEnum.MEMBER);
    }

    public void setKingdomForPlayerWithPermission(final KDKingdom kingdom, final KDPlayer player, final PermissionLevelEnum permissionLevelEnum) {
        final EntityManager entityManager = HibernateUtil.getEntityManager();
        entityManager.getTransaction().begin();

        player.setKingdom(kingdom);
        player.setPermissionLevel(permissionLevelEnum);

        entityManager.persist(player);

        entityManager.getTransaction().commit();
    }
}
