package ca.ultracrepidarianism.model;

import ca.ultracrepidarianism.model.enums.PermissionLevelEnum;
import jakarta.persistence.*;

@Entity
@Table(name="players")
public class KDPlayer {

    @Id
    private String UUID;

    @Column(name = "permissionLevel", nullable = true)
    @Enumerated
    private String permissionLevel;

    @ManyToOne
    @JoinColumn(name="townId",nullable = true)
    private KDTown town;

    public KDPlayer(String UUID, PermissionLevelEnum rank, KDTown town){
        this.town = town;
        this.permissionLevel = rank;
        this.UUID = UUID;
    }

    public KDPlayer(){}

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(String rank) {
        this.permissionLevel = rank;
    }

    public KDTown getTown() {
        return town;
    }

    public void setTown(KDTown town) {
        this.town = town;
    }
}
