package org.github.nullexceptionarg.listener;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.github.nullexceptionarg.Kingdom;
import org.github.nullexceptionarg.model.Util;

import java.io.File;

public class ClaimListener implements Listener {
    private Kingdom instance;

    public ClaimListener(Kingdom kingdom){
        instance = kingdom;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        Player p = e.getPlayer();
        String worldName = p.getWorld().getName();
        String chunkName = e.getBlock().getChunk().getX() + "_" + e.getBlock().getChunk().getZ();
        File dataFolder = new File(instance.getDataFolder().getAbsolutePath() + File.separator + "Claims");
        File file = new File(dataFolder,worldName + " " + chunkName + ".yml");
        if(!file.exists()) return;

        System.out.println("This has been claimed");
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){
        if(e.getFrom().getChunk() == e.getTo().getChunk()) return;
        System.out.println("filtered");
        String fromChunk = e.getFrom().getWorld() + " " + e.getFrom().getChunk().getX() +"_"+ e.getFrom().getZ();
        String toChunk = e.getTo().getWorld() + " " + e.getTo().getChunk().getX() +"_"+ e.getTo().getZ();
        File dataFolder = new File(instance.getDataFolder().getAbsolutePath() + File.separator + "Claims");
        File fileFrom = new File(dataFolder, fromChunk + ".yml");
        File fileTo = new File(dataFolder, toChunk + ".yml");

        if(fileFrom.exists() && !fileTo.exists()){e.getPlayer().sendMessage("Entering wilderness");}
        else if(!fileFrom.exists() && fileTo.exists()){
            FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(fileTo);
            e.getPlayer().sendMessage("Entering " + fileConfiguration.getString("town"));
        }

    }
}
