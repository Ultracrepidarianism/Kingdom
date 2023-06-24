package ca.ultracrepidarianism.kingdom.database.repositories;

import ca.ultracrepidarianism.kingdom.database.DataFacade;
import ca.ultracrepidarianism.kingdom.database.models.*;
import ca.ultracrepidarianism.kingdom.utils.HibernateUtil;
import ca.ultracrepidarianism.kingdom.utils.PersistenceUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.apache.commons.lang3.NotImplementedException;

import java.util.Set;

public class ClaimRepository extends Repository {

    private final String table = "claims";

    public ClaimRepository() {

    }

    public KDClaim getClaim() {
        throw new NotImplementedException();
    }

    public void createClaim(final KDKingdom kdKingdom, final KDChunk chunk) {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        entityManager.getTransaction().begin();


        entityManager.persist(new KDClaim(null, chunk, kdKingdom));


        entityManager.getTransaction().commit();
    }

    public KDClaim getClaimFromChunk(final KDChunk kdChunk) {
        EntityManager entityManager = HibernateUtil.getEntityManager();

        TypedQuery<KDClaim> typedQuery = entityManager.createQuery("FROM KDClaim where chunk.world = :world AND chunk.x = :x AND chunk.z = :z", KDClaim.class);
        typedQuery.setParameter("world", kdChunk.getWorld());
        typedQuery.setParameter("x", kdChunk.getX());
        typedQuery.setParameter("z", kdChunk.getZ());

        return PersistenceUtil.getSingleResultOrNull(typedQuery);
    }

    public Set<KDClaim> getKingdomClaims(final KDKingdom kingdom) {
        throw new NotImplementedException();
    }


    public Set<KDChunk> getKingdomChunks(final KDKingdom kingdom) {
        throw new NotImplementedException();
    }
}
