package ca.ultracrepidarianism.kingdom.model;

import ca.ultracrepidarianism.kingdom.model.enums.PermissionLevelEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "players")
public class KDPlayer {

    @Id
    private String UUID;

    @Column(name = "permissionLevel", nullable = true)
    @Enumerated(EnumType.STRING)
    private PermissionLevelEnum permissionLevel;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "kingdomId", foreignKey = @ForeignKey(name = "FK_PLAYER_KINGDOM"))
    private KDKingdom kingdom;

    protected KDPlayer() {}

    public KDPlayer(String UUID, PermissionLevelEnum rank, KDKingdom kingdom) {
        this.kingdom = kingdom;
        this.permissionLevel = rank;
        this.UUID = UUID;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
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
