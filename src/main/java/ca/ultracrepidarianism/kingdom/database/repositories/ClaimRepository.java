package ca.ultracrepidarianism.kingdom.database.repositories;

import ca.ultracrepidarianism.kingdom.database.DataFacade;
import ca.ultracrepidarianism.kingdom.database.dal.DAL;
import ca.ultracrepidarianism.kingdom.database.models.*;
import org.apache.commons.lang3.NotImplementedException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ClaimRepository extends Repository {

    private final String table = "claims";
    public ClaimRepository(final DAL dal) {
        super(dal);
    }

    public KDClaim getClaim() {
        throw new NotImplementedException();
    }

    public void createClaim(final KDKingdom kdKingdom, final KDChunk chunk) {
        try {
            final Map<String,String> properties = new HashMap<>();
            properties.put("x", String.valueOf(chunk.getX()));
            properties.put("z", String.valueOf(chunk.getZ()));
            properties.put("world", chunk.getWorld());
            properties.put("kingdomId", String.valueOf(kdKingdom.getId()));

            dal.insert(table,properties);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public KDClaim getClaimFromChunk(final KDChunk kdChunk, final boolean loadKingdom) {
        try {
            final String whereClause = String.format("world = '%s' AND x = '%s' AND z = '%s'", kdChunk.getWorld(), kdChunk.getX(), kdChunk.getZ());

            final ResultSet result = dal.filteredGet(table, whereClause);
            if (!result.next()) {
                return null;
            }

            final Long claimId = result.getLong("id");
            final Long kingdomId = result.getLong("kingdomId");
            result.close();

            if (loadKingdom) {
                final KDKingdom kingdom = DataFacade.getInstance().kingdoms().getKindom(kingdomId, false);
                return new KDClaim(claimId, kdChunk, kingdom);
            }

            return new KDClaim(claimId, kdChunk, kingdomId);
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public Set<KDClaim> getKingdomClaims(final KDKingdom kingdom) {
        throw new NotImplementedException();
    }

    public Set<KDPlayer> getKingdomPlayers(final KDKingdom kingdom){
        return DataFacade.getInstance().players().getPlayersFromKingdom(kingdom);
    }

    public Set<KDChunk> getKingdomChunks(final KDKingdom kingdom) {
        throw new NotImplementedException();
    }
}
