package ca.ultracrepidarianism.kingdom.database.repositories;

import ca.ultracrepidarianism.kingdom.database.models.KDChunk;
import ca.ultracrepidarianism.kingdom.database.models.KDClaim;
import ca.ultracrepidarianism.kingdom.database.models.KDKingdom;
import ca.ultracrepidarianism.kingdom.utils.HibernateUtil;
import ca.ultracrepidarianism.kingdom.utils.PersistenceUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.apache.commons.lang3.NotImplementedException;

import java.util.Set;
import java.util.stream.Collectors;

public class ClaimRepository extends Repository {
    private final static String TABLE = "claims";

    public void createClaim(final KDKingdom kdKingdom, final KDChunk chunk) {
        final EntityManager entityManager = HibernateUtil.getEntityManager();
        entityManager.getTransaction().begin();

        entityManager.persist(new KDClaim(chunk, kdKingdom));

        entityManager.getTransaction().commit();
    }

    public KDClaim getClaimFromChunk(final KDChunk kdChunk) {
        final EntityManager entityManager = HibernateUtil.getEntityManager();

        final TypedQuery<KDClaim> typedQuery = entityManager.createQuery("FROM KDClaim where chunk.world = :world AND chunk.x = :x AND chunk.z = :z", KDClaim.class);
        typedQuery.setParameter("world", kdChunk.getWorld());
        typedQuery.setParameter("x", kdChunk.getX());
        typedQuery.setParameter("z", kdChunk.getZ());

        return PersistenceUtil.getSingleResultOrNull(typedQuery);
    }

    public Set<KDClaim> getKingdomClaims(final KDKingdom kingdom) {
        throw new NotImplementedException();
    }


    public Set<KDChunk> getChunksForKingdom(final KDKingdom kingdom) {
        final EntityManager entityManager = HibernateUtil.getEntityManager();

        final TypedQuery<KDChunk> typedQuery = entityManager.createQuery("SELECT chunk FROM KDClaim where kingdom.id = :kingdomId", KDChunk.class);
        typedQuery.setParameter("kingdomId",kingdom.getId());
        return typedQuery.getResultStream().collect(Collectors.toSet());
    }

    public void removeClaim(final KDClaim claim){
        final EntityManager entityManager = HibernateUtil.getEntityManager();
        entityManager.getTransaction().begin();

        entityManager.remove(claim);

        entityManager.getTransaction().commit();
    }
}
