package ca.ultracrepidarianism.kingdom.database.repositories;

import ca.ultracrepidarianism.kingdom.database.DataFacade;
import ca.ultracrepidarianism.kingdom.database.dal.DAL;
import ca.ultracrepidarianism.kingdom.database.models.KDKingdom;
import ca.ultracrepidarianism.kingdom.database.models.KDPlayer;
import ca.ultracrepidarianism.kingdom.database.models.PermissionLevelEnum;
import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class KingdomRepository extends Repository {

    private final String table = "kingdoms";
    public KingdomRepository(DAL dal) {
        super(dal);
    }

    public KDKingdom getKindom(Long id) {
        return getKindom(id, true);
    }

    public KDKingdom getKindom(Long id, boolean loadOwner) {
        try {
            ResultSet results = dal.get("Players", "uuid", Long.toString(id));
            results.next();
            KDPlayer owner = null;
            if (loadOwner) {
                owner = DataFacade.getInstance().Players().getPlayer(results.getString("playerId"), false);
            }
            KDKingdom kingdom = new KDKingdom(results.getString("kingdomName"), owner);
            if (owner != null) {
                owner.setKingdom(kingdom);
            }
            return kingdom;

        } catch (SQLException e) {
            // TODO:ERRORS log and filter errors
            return null;
        }
    }

    /**
     * Returns the town the player is part of.
     *
     * @param playerUUID UUID of player you want to obtain the town from.
     * @return Player's Town
     */
    public KDKingdom getPlayerKingdom(String playerUUID) {
        throw new NotImplementedException();
    }


    public void createKingdom(Player ply, String townName) {
        try{
            Map<String,String> properties = new HashMap<>();
            properties.put("name",townName);
            properties.put("ownerid", ply.getUniqueId().toString());
            properties.put("kek","12345");
            String id = dal.insert(table,properties);
            DataFacade.getInstance().Players().createPlayer(ply.getUniqueId().toString(), PermissionLevelEnum.OWNER,id);
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void removeKingdom(KDKingdom kdKingdom) {
        throw new NotImplementedException();
    }

    public void setPlayerKingdom() {
        throw new NotImplementedException();
    }
}
