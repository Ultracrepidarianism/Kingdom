package ca.ultracrepidarianism.model;

import ca.ultracrepidarianism.model.enums.PermissionLevelEnum;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "towns")

public class KDTown {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String townName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ownerUUID", foreignKey = @ForeignKey(name = "FK_TOWN_OWNER"))
    private KDPlayer owner;

    @OneToMany(mappedBy = "town")
    private List<KDPlayer> lstMembers;

    @OneToMany(mappedBy = "town")
    private List<KDClaim> kdClaims;

    protected KDTown() {}

    public KDTown(String townName, KDPlayer owner) {
        this.kdClaims = new ArrayList<>();
        this.lstMembers = new ArrayList<>();

        this.townName = townName;
        this.lstMembers.add(owner);

        this.owner = owner;
    }

    public String getTownName() {
        return townName;
    }

    public KDPlayer getOwner() {
        return owner;
    }

    public void setOwner(KDPlayer player){this.owner = player;}

    public List<KDClaim> getKdClaim() {
        return kdClaims;
    }

    public void addClaim(KDClaim claim) {
        this.kdClaims.add(claim);
    }

    /**
     * Contains ALL members
     *
     * @return list of all members
     */
    public List<KDPlayer> getMembers() {
        return lstMembers;
    }

    /**
     * Contains only members with {@link PermissionLevelEnum PermissionLevelEnum.OFFICER} or higher.
     *
     * @return list of officers
     */
    public List<KDPlayer> getOfficers() {
        return lstMembers
                .stream()
                .filter(x -> x.getPermissionLevel().hasPermission(PermissionLevelEnum.OFFICER))
                .collect(Collectors.toList());
    }
}
