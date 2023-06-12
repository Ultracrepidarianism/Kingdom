package ca.ultracrepidarianism.model;

import jakarta.persistence.Entity;
import org.bukkit.entity.Player;

@Entity
public class KDPlayer {
    private KDTown town;
    private Player player;
    private String rank;

    public KDPlayer(Player player, KDTown town){
        this.player = player;
        this.town = town;
    }

    public KDTown getTown(){
        return town;
    }

    public String getUuid(){
        return player.getUniqueId().toString();
    }
    public Player getPlayer(){return player;}
}
