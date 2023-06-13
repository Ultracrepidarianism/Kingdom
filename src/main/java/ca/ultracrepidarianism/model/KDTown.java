package ca.ultracrepidarianism.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="towns")
public class KDTown {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String townName;

    @OneToOne
    @JoinColumn(name = "ownerUUID")
    private KDPlayer owner ;

    @OneToMany(mappedBy = "town")
    private List<KDPlayer> lstMembers = new ArrayList<>();

    protected KDTown() {}

    public KDTown(String townName, KDPlayer owner) {
        this.townName = townName;
        this.owner = owner;
        this.lstMembers.add(owner);
    }


    public String getTownName() {
        return townName;
    }

    public KDPlayer getOwner(){
        return owner;
    }

    public void setOwner(KDPlayer owner) {
        this.owner = owner;
    }

    /**
     * Contains ALL members
     * @return list of all members
     */
    public List<KDPlayer> getMembers(){
        return lstMembers;
    }

    public List<KDPlayer> getOfficers(){
        return lstMembers.stream().filter(x-> x.getPermissionLevel());
    }


}
