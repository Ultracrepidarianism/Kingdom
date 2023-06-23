package ca.ultracrepidarianism.kingdom.database.repositories;

import ca.ultracrepidarianism.kingdom.database.DataFacade;
import ca.ultracrepidarianism.kingdom.database.dal.DAL;
import ca.ultracrepidarianism.kingdom.model.KDKingdom;
import ca.ultracrepidarianism.kingdom.model.KDPlayer;
import ca.ultracrepidarianism.kingdom.model.enums.PermissionLevelEnum;

import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlayerRepository extends Repository {

    private final String table = "Players";

    public PlayerRepository(DAL dal) {
        super(dal);
    }

    public KDPlayer getPlayer(Player player, boolean loadKingdom) {
        return getPlayer(player.getUniqueId().toString(), loadKingdom);
    }

    public KDPlayer getPlayer(String playerId, boolean loadKingdom) {
        try {
            ResultSet results = dal.get(table, "uuid", playerId);
            results.next();
            KDKingdom kingdom = null;
            if (loadKingdom) {
                kingdom = DataFacade.getInstance().Kingdoms().getKindom(results.getLong("kingdomID"), false);
            }
            return new KDPlayer(results.getString("UUID"), PermissionLevelEnum.MEMBER.fromLevel(results.getInt("permissionLevel")), kingdom);
        } catch (SQLException e) {
            // TODO:ERRORS log and filter errors
            return null;
        }
    }

    public Set<KDPlayer> getPlayersFromKingdom(KDKingdom kingdom){
        return getPlayersFromKingdom(kingdom.getId());
    }

    public Set<KDPlayer> getPlayersFromKingdom(long kingdomId){
        try{
            Set<KDPlayer> players = new HashSet<>();
            ResultSet set = dal.get(table,"kingdomId",Long.toString(kingdomId));
            while(set.next()){
                KDPlayer ply = new KDPlayer(set.getString("UUID"),PermissionLevelEnum.valueOf(set.getString("permissionLevel")),set.getLong("kingdomId"));
                players.add(ply);
            }
            return players;
        }catch (SQLException exception){
            // TODO:ERRORS log and filter errors
            return null;
        }

    }


    public void setPlayerTown(Player player, KDKingdom kingdom) {
        setPlayerTown(player.getUniqueId().toString(), kingdom.getId());
    }

    public void setPlayerTown(String uuid, Long townName) {
        throw new NotImplementedException();
    }


    public void removePlayer(Player player) {
        removePlayer(player.getUniqueId().toString());
    }

    public void removePlayer(KDPlayer kdPlayer) {
        removePlayer(kdPlayer.getUUID());
    }

    public void removePlayer(String id) {
        try{
            dal.delete(table,id);
        }catch (SQLException exception){
            // TODO:ERRORS log and filter errors
        }
    }

    public KDPlayer createPlayer(String uuid) {
        throw new NotImplementedException();
    }

//    public List<String> getPendingInvites(String playerId) {
//        throw new NotImplementedException();
//    }
//
//    public void removePendingInvite(String playerId,String townName){
//        throw new NotImplementedException();
//    }

}
