package ca.ultracrepidarianism.model;

public class KDClaim {
    private final KDChunk chunk;
    private final KDTown town;

    public KDClaim(KDChunk chunk, KDTown town){
        this.chunk = chunk;
        this.town = town;
    }

    public KDChunk getChunk() {
        return chunk;
    }
    public KDTown getTown() {
        return town;
    }
}
