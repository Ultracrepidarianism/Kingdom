package ca.ultracrepidarianism.kingdom.database.repositories;

import ca.ultracrepidarianism.kingdom.database.DataFacade;
import ca.ultracrepidarianism.kingdom.database.models.KDKingdom;
import ca.ultracrepidarianism.kingdom.database.models.KDPlayer;
import ca.ultracrepidarianism.kingdom.database.models.PermissionLevelEnum;
import ca.ultracrepidarianism.kingdom.utils.HibernateUtil;
import ca.ultracrepidarianism.kingdom.utils.PersistenceUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class KingdomRepository extends Repository {
    private final static String TABLE = "kingdoms";

    public KingdomRepository() {

    }


    public KDKingdom getKingdom(final Long id) {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        return entityManager.find(KDKingdom.class,id);
    }

    /**
     * Returns the town the player is part of.
     *
     * @param playerUUID UUID of player you want to obtain the town from.
     * @return Player's Town
     */
    public KDKingdom getPlayerKingdom(final String playerUUID) {
        throw new NotImplementedException();
    }


    public void createKingdom(final Player player, final String kingdomName) {
        EntityManager entityManager = HibernateUtil.getEntityManager();

        entityManager.getTransaction().begin();  // Start transaction

        KDPlayer kdP = entityManager.find(KDPlayer.class, player.getUniqueId().toString());
        if (kdP == null) {
            kdP = new KDPlayer(player.getUniqueId().toString(), PermissionLevelEnum.OWNER, null);
        }
        KDKingdom kingdom = new KDKingdom(kingdomName, kdP);
        kdP.setKingdom(kingdom);

        entityManager.persist(kdP);

        entityManager.getTransaction().commit();  // Commit the transaction

    }

    public void removeKingdom(final KDKingdom kdKingdom) {
        throw new NotImplementedException();
    }

    public void setPlayerKingdom() {
        throw new NotImplementedException();
    }
}
