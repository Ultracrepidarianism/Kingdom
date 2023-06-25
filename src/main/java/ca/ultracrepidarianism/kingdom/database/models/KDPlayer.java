package ca.ultracrepidarianism.kingdom.database.models;


import ca.ultracrepidarianism.kingdom.database.models.enums.PermissionLevelEnum;
import jakarta.persistence.*;

@Entity
@Table(name="players")
public class KDPlayer {

    @Id
    private String id;

    @Column
    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    private PermissionLevelEnum permissionLevel;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="kingdomId", foreignKey = @ForeignKey(name="FK_KINGDOM_PLAYER"))
    private KDKingdom kingdom;

    protected KDPlayer() {}

    public KDPlayer(final String UUID, final String name, final PermissionLevelEnum rank, final KDKingdom kingdom) {
        this.id = UUID;
        this.name = name;
        this.permissionLevel = rank;
        this.kingdom = kingdom;
    }

    public String getId() {
        return id;
    }

    public void setId(final String UUID) {
        this.id = UUID;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public PermissionLevelEnum getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(final PermissionLevelEnum permissionLevel) {
        this.permissionLevel = permissionLevel;
    }


    public KDKingdom getKingdom() {
        return kingdom;
    }

    public void setKingdom(final KDKingdom town) {
        this.kingdom = town;
    }
}
