package ca.ultracrepidarianism.kingdom.database.repositories;

import ca.ultracrepidarianism.kingdom.database.DataFacade;
import ca.ultracrepidarianism.kingdom.database.models.KDInvite;
import ca.ultracrepidarianism.kingdom.database.models.KDKingdom;
import ca.ultracrepidarianism.kingdom.database.models.KDPlayer;
import ca.ultracrepidarianism.kingdom.database.models.PermissionLevelEnum;
import ca.ultracrepidarianism.kingdom.utils.HibernateUtil;
import jakarta.persistence.EntityManager;
import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class PlayerRepository extends Repository {

    private final String table = "Players";

    private final Map<String,List<KDInvite>> kdInvitesByPlayerUUID = new HashMap<>();

    public PlayerRepository() {

    }

    public KDPlayer getPlayer(final Player player) {
        return getPlayer(player.getUniqueId().toString());
    }

    public KDPlayer getPlayer(final String playerId) {
        return HibernateUtil.getEntityManager().find(KDPlayer.class, playerId);
    }

//    public void setPlayerTown(final Player player, final KDKingdom kingdom) {
//        setPlayerTown(player.getUniqueId().toString(), kingdom.getId());
//    }
//    public void setPlayerTown(final String uuid, final Long townName) {
//        throw new NotImplementedException();
//    }


    public void removePlayer(final KDPlayer kdPlayer) {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        entityManager.getTransaction().begin();

        entityManager.remove(kdPlayer);

        entityManager.getTransaction().commit();
    }

    public void removePlayer(final String id) {
        KDPlayer player = HibernateUtil.getEntityManager().find(KDPlayer.class,id);
        if(player != null){
            removePlayer(player);
        }
    }


    public List<KDInvite> getPendingInvites(final String playerId) {
        return kdInvitesByPlayerUUID.get(playerId);
    }

    public void addPendingInvite(final KDPlayer inviter, final Player invitee) {
        final String inviteeUniqueId = invitee.getUniqueId().toString();
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
}
