package ca.ultracrepidarianism.model;

import jakarta.persistence.*;

@Entity
@Table(name = "claims")
public class KDClaim {
    @Embedded
    private final KDChunk chunk;
    @ManyToOne
    @JoinColumn(name = "town_id", foreignKey = @ForeignKey(name = "FK_CLAIM_TOWN"))
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
