package org.github.nullexceptionarg.model;

import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Town {

    private String townName;
    private String owner;
    private List<String> lstOfficers;
    private List<String> lstMembers;
    private List<String> lstClaims = new ArrayList<>();

    public Town(String townName, String owner, List<String> lstClaims, List<String> lstOfficers, List<String> lstMembers){
        this.townName = townName;
        this.owner = owner;
        this.lstClaims = lstClaims;
        this.lstOfficers = lstOfficers;
        this.lstMembers = lstMembers;
    }

    public void addTownClaim(String claimName){
        lstClaims.add(claimName);
    }

    public String getTownName() {
        return townName;
    }

    public String getOwnerUID(){
        return owner;
    }

    public List<String> getMembers(){
        return lstMembers;
    }

    public List<String> getOfficers(){
        return lstOfficers;
    }

    public List<String> getClaims(){
        return lstClaims;
    }

}
