package ca.ultracrepidarianism.kingdom.database.models;


public class KDPlayer {

    private String id;

    private PermissionLevelEnum permissionLevel;

    private Long kingdomId;
    private KDKingdom kingdom;

    public KDPlayer(String id,PermissionLevelEnum rank, Long kingdomId) {
        this.id = id;
        this.permissionLevel = rank;
        this.kingdomId = kingdomId;
    }

    public KDPlayer(String UUID,PermissionLevelEnum rank, KDKingdom kingdom) {
        this.id = UUID;
        this.permissionLevel = rank;
        this.kingdom = kingdom;
        this.kingdomId = kingdom.getId();
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

    public Long getKingdomId() {
        return kingdomId;
    }

    public void setKingdomId(Long kingdomId){
        this.kingdomId = kingdomId;
    }


    public KDKingdom getKingdom() {
        return kingdom;
    }

    public void setKingdom(KDKingdom town) {
        this.kingdom = town;
    }
}
