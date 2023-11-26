package ca.ultracrepidarianism.kingdom.database.repositories;

import ca.ultracrepidarianism.kingdom.database.models.KDChunk;
import ca.ultracrepidarianism.kingdom.database.models.KDClaim;
import ca.ultracrepidarianism.kingdom.database.models.KDKingdom;
import ca.ultracrepidarianism.kingdom.utils.PersistenceUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class ClaimRepository extends Repository {
    public void createClaim(final KDKingdom kdKingdom, final KDChunk chunk) {
        try (final EntityManager entityManager = getEntityManager()) {
            entityManager.getTransaction().begin();

            entityManager.persist(new KDClaim(chunk, kdKingdom));

            entityManager.getTransaction().commit();
        }
    }

    public KDClaim getClaimFromChunk(final KDChunk kdChunk) {
        final EntityManager entityManager = getEntityManager();

        final TypedQuery<KDClaim> typedQuery = entityManager.createQuery("FROM KDClaim where chunk.world = :world AND chunk.x = :x AND chunk.z = :z", KDClaim.class);
        typedQuery.setParameter("world", kdChunk.getWorld());
        typedQuery.setParameter("x", kdChunk.getX());
        typedQuery.setParameter("z", kdChunk.getZ());

        return PersistenceUtil.getSingleResultOrNull(typedQuery);
    }

    public void removeClaim(final KDClaim claim) {
        try (final EntityManager entityManager = getEntityManager()) {
            entityManager.getTransaction().begin();

            entityManager.remove(claim);

            entityManager.getTransaction().commit();
        }
    }
}
