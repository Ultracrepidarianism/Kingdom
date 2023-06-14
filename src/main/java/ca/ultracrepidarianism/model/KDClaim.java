package ca.ultracrepidarianism.model;

import jakarta.persistence.*;

@Entity
@Table(name = "claims")
public class KDClaim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private KDChunk chunk;

    @ManyToOne
    @JoinColumn(name = "townId", foreignKey = @ForeignKey(name = "FK_CLAIM_TOWN"))
    private KDTown town;

    protected KDClaim() {}

    public KDClaim(KDChunk chunk, KDTown town) {
        this.chunk = chunk;
        this.town = town;
    }

    public Long getId() {
        return id;
    }

    public KDChunk getChunk() {
        return chunk;
    }

    public KDTown getTown() {
        return town;
    }
}
