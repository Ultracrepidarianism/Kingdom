package ca.ultracrepidarianism.kingdom.database.models;

import ca.ultracrepidarianism.kingdom.database.models.enums.PermissionLevelEnum;
import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "kingdoms", indexes = @Index(columnList = "name"))
public class KDKingdom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToOne
    @JoinColumn(name = "ownerId", foreignKey = @ForeignKey(name = "FK_PLAYER_KINGDOM"), nullable = false)
    private KDPlayer owner;

    @OneToMany(mappedBy = "kingdom", cascade = CascadeType.PERSIST)
    private List<KDPlayer> members;

    @OneToMany(mappedBy = "kingdom", cascade = CascadeType.REMOVE)
    private List<KDClaim> claims;

    protected KDKingdom() {
    }

    public KDKingdom(final String name, final KDPlayer owner) {
        this.name = name;
        this.owner = owner;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public KDPlayer getOwner() {
        return owner;
    }

    public List<KDPlayer> getMembers() {
        return members;
    }

    public void setMembers(List<KDPlayer> members) {
        this.members = members;
    }

    public List<KDPlayer> getOfficers() {
        return getMembers().stream().filter(x -> x.getPermissionLevel() == PermissionLevelEnum.OFFICER).toList();
    }

    public List<KDClaim> getClaims() {
        return claims;
    }

    public void setClaims(List<KDClaim> claims) {
        this.claims = claims;
    }
}
