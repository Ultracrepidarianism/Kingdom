package org.github.nullexceptionarg.listener;

import org.bukkit.Chunk;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.github.nullexceptionarg.Kingdom;
import org.github.nullexceptionarg.model.db;

public class KingdomListener implements Listener {
    private Kingdom instance;

    public KingdomListener(Kingdom kingdom){
        instance = kingdom;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        db.createPlayer(p.getUniqueId().toString());
        db.addPlayerToMap(p);
    }

    @EventHandler
    public void onLeave(PlayerJoinEvent e){
        db.removePlayerfromMap(e.getPlayer());
    }
}
