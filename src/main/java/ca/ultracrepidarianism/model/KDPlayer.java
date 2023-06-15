package ca.ultracrepidarianism.model;

import ca.ultracrepidarianism.model.enums.PermissionLevelEnum;
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
    @JoinColumn(name = "townId", foreignKey = @ForeignKey(name = "FK_PLAYER_TOWN"))
    private KDTown town;

    protected KDPlayer() {}

    public KDPlayer(String UUID, PermissionLevelEnum rank, KDTown town) {
        this.town = town;
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

    public KDTown getTown() {
        return town;
    }

    public void setTown(KDTown town) {
        this.town = town;
    }
}
