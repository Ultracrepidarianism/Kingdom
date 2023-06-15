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
    @JoinColumn(name = "kingdomId", foreignKey = @ForeignKey(name = "FK_CLAIM_KINGDOM"))
    private KDKingdom kingdom;

    protected KDClaim() {}

    public KDClaim(KDChunk chunk, KDKingdom kingdom) {
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
