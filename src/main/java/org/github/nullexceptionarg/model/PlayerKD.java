package org.github.nullexceptionarg.model;

import org.bukkit.entity.Player;

public class PlayerKD {
    private Town town;
    private Player player;
    private String rank;

    public PlayerKD(Player player, Town town){
        this.player = player;
        this.town = town;
    }

    public Town getTown(){
        return town;
    }

    public String getUuid(){
        return player.getUniqueId().toString();
    }
    public Player getPlayer(){return player;}
}
