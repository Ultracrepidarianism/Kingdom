package ca.ultracrepidarianism.model;

import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class KDTown {

    private String townName;
    private String owner;
    private List<String> lstOfficers;
    private List<String> lstMembers;


    public KDTown(String townName, String owner, List<String> lstOfficers, List<String> lstMembers){
        this.townName = townName;
        this.owner = owner;
        this.lstOfficers = lstOfficers;
        this.lstMembers = lstMembers;
    }



    public String getTownName() {
        return townName;
    }

    public String getOwnerUID(){
        return owner;
    }

    /**
     * Contains ALL members
     * @return list of all members
     */
    public List<String> getMembers(){
        return lstMembers;
    }

    public List<String> getOfficers(){
        return lstOfficers;
    }


}
