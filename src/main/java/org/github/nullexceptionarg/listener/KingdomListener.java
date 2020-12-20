package org.github.nullexceptionarg.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.github.nullexceptionarg.Kingdom;

public class KingdomListener implements Listener {
    private Kingdom instance;

    public KingdomListener(Kingdom kingdom){
        instance = kingdom;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        Kingdom.DB.createPlayer(p.getUniqueId().toString());
        Kingdom.DB.addPlayerToMap(p);
    }

    @EventHandler
    public void onLeave(PlayerJoinEvent e){
        Kingdom.DB.removePlayerfromMap(e.getPlayer());
    }
}
