package ca.ultracrepidarianism.kingdom.model;

import ca.ultracrepidarianism.kingdom.model.enums.PermissionLevelEnum;
import com.j256.ormlite.table.DatabaseTable;
import jakarta.persistence.*;


public class KDPlayer {

    private String UUID;

    private PermissionLevelEnum permissionLevel;


    private long kingdomId;
    private KDKingdom kingdom;

    protected KDPlayer() {}

    public KDPlayer(String UUID, PermissionLevelEnum rank, KDKingdom kingdom) {
        this.kingdom = kingdom;
        this.permissionLevel = rank;
        this.UUID = UUID;
    }

    public KDPlayer(String UUID,PermissionLevelEnum rank,long kingdomId){
        this.UUID = UUID;
        this.permissionLevel = rank;
        this.kingdomId = kingdomId;
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

    public long getKingdomId(){return kingdomId;}

    public KDKingdom getKingdom() {
        return kingdom;
    }

    public void setKingdom(KDKingdom town) {
        this.kingdom = town;
    }
}
