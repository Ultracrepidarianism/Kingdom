package ca.ultracrepidarianism.kingdom.database.models;

import jakarta.persistence.*;

@Entity
@Table(name="claims")
public class KDClaim {

    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private KDChunk chunk;

    @ManyToOne
    @JoinColumn(name="kingdomId",foreignKey = @ForeignKey(name="FK_CLAIM_KINGDOM"))
    private KDKingdom kingdom;


    public KDClaim(final Long id, final KDChunk chunk, final KDKingdom kingdom) {
        this.id = id;
        this.chunk = chunk;
        this.kingdom = kingdom;
    }

    public KDClaim(){}

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
