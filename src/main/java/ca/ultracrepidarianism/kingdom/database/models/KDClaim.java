package ca.ultracrepidarianism.kingdom.database.models;

public class KDClaim {
    private final Long id;
    private final KDChunk chunk;
    private final Long kingdomId;
    private KDKingdom kingdom;

    public KDClaim(final Long id, final KDChunk chunk, final Long kingdomId) {
        this.id = id;
        this.chunk = chunk;
        this.kingdomId = kingdomId;
    }

    public KDClaim(final Long id, final KDChunk chunk, final KDKingdom kingdom) {
        this.id = id;
        this.chunk = chunk;
        this.kingdom = kingdom;
        this.kingdomId = kingdom.getId();
    }

    public Long getId() {
        return id;
    }

    public Long getKingdomId() {
        return kingdomId;
    }

    public KDChunk getChunk() {
        return chunk;
    }

    public KDKingdom getKingdom() {
        return kingdom;
    }
}
