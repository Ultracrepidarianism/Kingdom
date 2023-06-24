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
    private final static String TABLE = "kingdoms";

    public KingdomRepository(final DAL dal) {
        super(dal);
    }

    public KDKingdom getKindom(final Long id) {
        return getKindom(id, true);
    }

    public KDKingdom getKindom(final Long id, final boolean loadOwner) {
        try {
            final ResultSet results = dal.get(TABLE, "id", Long.toString(id));
            if (!results.next()) {
                return null;
            }

            KDPlayer owner = null;
            if (loadOwner) {
                owner = DataFacade.getInstance().players().getPlayer(results.getString("ownerId"), false);
            }

            final KDKingdom kingdom = new KDKingdom(results.getLong("id"), results.getString("name"), owner);
            if (owner != null) {
                owner.setKingdom(kingdom);
            }

            return kingdom;

        } catch (SQLException e) {
            e.printStackTrace();
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
    public KDKingdom getPlayerKingdom(final String playerUUID) {
        throw new NotImplementedException();
    }


    public void createKingdom(final Player player, final String kingdomName) {
        try{
            final Map<String,String> properties = new HashMap<>();
            properties.put("name", kingdomName);
            properties.put("ownerid", player.getUniqueId().toString());
            final String id = dal.insert(TABLE,properties);
            DataFacade.getInstance().players().createPlayer(player.getUniqueId().toString(), PermissionLevelEnum.OWNER,id);
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void removeKingdom(final KDKingdom kdKingdom) {
        throw new NotImplementedException();
    }

    public void setPlayerKingdom() {
        throw new NotImplementedException();
    }
}
