package ca.ultracrepidarianism.kingdom.model;

import ca.ultracrepidarianism.kingdom.model.enums.PermissionLevelEnum;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class KDKingdom {

    private Long id;

    private String kingdomName;

    private KDPlayer owner;

    private List<KDPlayer> members;

    private List<KDClaim> claims;

    protected KDKingdom() {}

    public KDKingdom(String kingdomName, KDPlayer owner) {
        this.claims = new ArrayList<>();
        this.members = new ArrayList<>();

        this.kingdomName = kingdomName;
        this.members.add(owner);

        this.owner = owner;
    }

    public KDKingdom(long id,String kingdomName,KDPlayer owner){
        this.id = id;
        this.claims = new ArrayList<>();
        this.members = new ArrayList<>();

        this.kingdomName = kingdomName;
        this.members.add(owner);

        this.owner = owner;
    }

    public long getId(){
        return id;
    }

    public String getKingdomName() {
        return kingdomName;
    }

    public KDPlayer getOwner() {
        return owner;
    }

    public void setOwner(KDPlayer player){this.owner = player;}

    public List<KDClaim> getClaims() {
        return claims;
    }

    public void addClaim(KDClaim claim) {
        this.claims.add(claim);
    }

    /**
     * Contains ALL members
     *
     * @return list of all members
     */
    public List<KDPlayer> getMembers() {
        return members;
    }

    /**
     * Contains only members with {@link PermissionLevelEnum PermissionLevelEnum.OFFICER} or higher.
     *
     * @return list of officers
     */
    public List<KDPlayer> getOfficers() {
        return members
                .stream()
                .filter(x -> x.getPermissionLevel().hasPermission(PermissionLevelEnum.OFFICER))
                .collect(Collectors.toList());
    }
}
