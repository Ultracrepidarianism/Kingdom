package ca.ultracrepidarianism.kingdom.database.repositories;

import ca.ultracrepidarianism.kingdom.database.dal.DAL;
import ca.ultracrepidarianism.kingdom.model.KDChunk;
import ca.ultracrepidarianism.kingdom.model.KDClaim;
import ca.ultracrepidarianism.kingdom.model.KDKingdom;
import org.apache.commons.lang3.NotImplementedException;

import java.util.Set;

public class ClaimRepository extends Repository {
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
        throw new NotImplementedException();
    }

    public Set<KDClaim> getKingdomClaims(KDKingdom kingdom) {
        throw new NotImplementedException();
    }

    public Set<KDChunk> getKingdomChunks(KDKingdom kingdom) {
        throw new NotImplementedException();
    }
}
