package ca.ultracrepidarianism.kingdom.database.models;

import jakarta.persistence.*;


@Entity
@Table(name="kingdoms")
public class KDKingdom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "ownerId",foreignKey = @ForeignKey(name="FK_PLAYER_KINGDOM"))
    private KDPlayer owner;

    public KDKingdom(final String name, final KDPlayer owner) {
        this.name = name;
        this.owner = owner;
    }

    public KDKingdom(final Long id, final String name, final KDPlayer owner) {
        this(name, owner);
        this.id = id;
    }

    public KDKingdom(){}

    public long getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public KDPlayer getOwner() {
        return owner;
    }
}
