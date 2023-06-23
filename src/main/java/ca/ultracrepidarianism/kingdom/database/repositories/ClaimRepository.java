package ca.ultracrepidarianism.kingdom.database.repositories;

import ca.ultracrepidarianism.kingdom.database.DataFacade;
import ca.ultracrepidarianism.kingdom.database.dal.DAL;
import ca.ultracrepidarianism.kingdom.database.models.KDChunk;
import ca.ultracrepidarianism.kingdom.database.models.KDClaim;
import ca.ultracrepidarianism.kingdom.database.models.KDKingdom;
import ca.ultracrepidarianism.kingdom.database.models.KDPlayer;
import org.apache.commons.lang3.NotImplementedException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

public class ClaimRepository extends Repository {

    private final String table = "Claims";
    public ClaimRepository(DAL dal) {
        super(dal);
    }

    public KDClaim getClaim() {
        throw new NotImplementedException();
    }

    public void createClaim(KDKingdom kdKingdom, KDChunk chunk) {
        throw new NotImplementedException();
    }


    public KDClaim getClaimFromChunk(KDChunk c) {

        try{
            String whereClause = String.format("world = %s AND x = %s AND z = %s",c.getWorld(),c.getX(),c.getZ());
            ResultSet result = dal.filteredGet(table,whereClause);
            if(!result.next()){
                return null;
            }

            return new KDClaim(result.getLong("id"),c,result.getLong("kingdomId"));

        }catch (SQLException exception){
            return null;
        }
    }

    public Set<KDClaim> getKingdomClaims(KDKingdom kingdom) {
        throw new NotImplementedException();
    }

    public Set<KDPlayer> getKingdomPlayers(KDKingdom kingdom){
        return DataFacade.getInstance().Players().getPlayersFromKingdom(kingdom);
    }

    public Set<KDChunk> getKingdomChunks(KDKingdom kingdom) {
        throw new NotImplementedException();
    }
}
