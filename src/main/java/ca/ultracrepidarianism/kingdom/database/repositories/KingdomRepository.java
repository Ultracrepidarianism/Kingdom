package ca.ultracrepidarianism.kingdom.database.repositories;

import ca.ultracrepidarianism.kingdom.database.DataFacade;
import ca.ultracrepidarianism.kingdom.database.dal.DAL;
import ca.ultracrepidarianism.kingdom.model.KDKingdom;
import ca.ultracrepidarianism.kingdom.model.KDPlayer;
import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

public class KingdomRepository extends Repository {
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
        throw new NotImplementedException();
    }

    public void removeKingdom(KDKingdom kdKingdom) {
        throw new NotImplementedException();
    }

    public void setPlayerKingdom() {
        throw new NotImplementedException();
    }
}
