package ca.ultracrepidarianism.kingdom.database.models;

public class KDClaim {

    private Long id;

    private KDChunk chunk;

    private int kingdomId;
    private KDKingdom kingdom;

    protected KDClaim() {}

    public KDClaim(KDChunk chunk, KDKingdom kingdom) {
        this.chunk = chunk;
        this.kingdom = kingdom;
    }

    public KDClaim(long id,KDChunk c, long kingdomId) {
        this.chunk = chunk;
        this.kingdom = kingdom;
    }

    public Long getId() {
        return id;
    }

    public KDChunk getChunk() {
        return chunk;
    }

    public KDKingdom getKingdom() {
        return kingdom;
    }
}
