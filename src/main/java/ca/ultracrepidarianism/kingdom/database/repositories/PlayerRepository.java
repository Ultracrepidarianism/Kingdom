package ca.ultracrepidarianism.kingdom.database.repositories;

import ca.ultracrepidarianism.kingdom.database.DataFacade;
import ca.ultracrepidarianism.kingdom.database.dal.DAL;
import ca.ultracrepidarianism.kingdom.database.models.KDInvite;
import ca.ultracrepidarianism.kingdom.database.models.KDKingdom;
import ca.ultracrepidarianism.kingdom.database.models.KDPlayer;
import ca.ultracrepidarianism.kingdom.database.models.PermissionLevelEnum;
import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.K;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class PlayerRepository extends Repository {

    private final String table = "Players";

    private final Map<String,List<KDInvite>> kdInvitesByPlayerUUID = new HashMap<>();

    public PlayerRepository(DAL dal) {
        super(dal);
    }

    public KDPlayer getPlayer(final Player player, final boolean loadKingdom) {
        return getPlayer(player.getUniqueId().toString(), loadKingdom);
    }

    public KDPlayer getPlayer(final String playerId, final boolean loadKingdom) {
        try {
            final ResultSet results = dal.get(table, "id", playerId);
            if (!results.next()) {
                return null;
            }

            final String id = results.getString("id");
            final Long kingdomId = results.getLong("kingdomID");
            final PermissionLevelEnum permissionLevel = PermissionLevelEnum.valueOf(results.getString("permissionLevel"));
            
            if (loadKingdom) {
                final KDKingdom kingdom = DataFacade.getInstance().kingdoms().getKindom(kingdomId, false);
                return new KDPlayer(id, permissionLevel, kingdom);
            }

            return new KDPlayer(id, permissionLevel, kingdomId);
        } catch (SQLException e) {
            // TODO:ERRORS log and filter errors
            return null;
        }
    }

    public Set<KDPlayer> getPlayersFromKingdom(final KDKingdom kingdom){
        return getPlayersFromKingdom(kingdom.getId());
    }

    public Set<KDPlayer> getPlayersFromKingdom(final Long kingdomId){
        try{
            final Set<KDPlayer> players = new HashSet<>();
            final ResultSet set = dal.get(table,"kingdomId",Long.toString(kingdomId));
            while (set.next()) {
                final KDPlayer kdPlayer = new KDPlayer(set.getString("UUID"),PermissionLevelEnum.valueOf(set.getString("permissionLevel")),set.getLong("kingdomId"));
                players.add(kdPlayer);
            }
            return players;
        }catch (SQLException exception){
            // TODO:ERRORS log and filter errors
            return null;
        }

    }


    public void setPlayerTown(final Player player, final KDKingdom kingdom) {
        setPlayerTown(player.getUniqueId().toString(), kingdom.getId());
    }

    public void setPlayerTown(final String uuid, final Long townName) {
        throw new NotImplementedException();
    }


    public void removePlayer(final Player player) {
        removePlayer(player.getUniqueId().toString());
    }

    public void removePlayer(final KDPlayer kdPlayer) {
        removePlayer(kdPlayer.getUUID());
    }

    public void removePlayer(final String id) {
        try{
            dal.delete(table,id);
        }catch (SQLException exception){
            // TODO:ERRORS log and filter errors
        }
    }

    public KDPlayer createPlayer(final String uuid, final PermissionLevelEnum permissionLevel, final String kingdomId) {
        try{
            final Map<String,String> properties = new HashMap<>();
            properties.put("id",uuid);
            properties.put("permissionLevel",permissionLevel.name());
            properties.put("kingdomId",kingdomId);
            final String id = dal.insert("players",properties );
            final KDPlayer ply = getPlayer(id,false);
            return ply;
        }catch (SQLException e){
            e.printStackTrace();
            // TODO:ERRORS log and filter errors
            return null;
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
