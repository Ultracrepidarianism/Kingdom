package ca.ultracrepidarianism.kingdom.database.models;


import jakarta.persistence.*;

@Entity
@Table(name="players")
public class KDPlayer {

    @Id
    private String id;

    @Column(name="permissionLevel",nullable = true)
    @Enumerated(EnumType.STRING)
    private PermissionLevelEnum permissionLevel;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="kingdomId",foreignKey = @ForeignKey(name="FK_KINGDOM_PLAYER"))
    private KDKingdom kingdom;


    public KDPlayer(){}
    public KDPlayer(String UUID,PermissionLevelEnum rank, KDKingdom kingdom) {
        this.id = UUID;
        this.permissionLevel = rank;
        this.kingdom = kingdom;
    }

    public String getUUID() {
        return id;
    }

    public void setUUID(String UUID) {
        this.id = UUID;
    }

    public PermissionLevelEnum getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(PermissionLevelEnum permissionLevel) {
        this.permissionLevel = permissionLevel;
    }


    public KDKingdom getKingdom() {
        return kingdom;
    }

    public void setKingdom(KDKingdom town) {
        this.kingdom = town;
    }
}
