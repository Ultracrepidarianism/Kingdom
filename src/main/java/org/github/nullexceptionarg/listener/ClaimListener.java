package org.github.nullexceptionarg.listener;

import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.github.nullexceptionarg.Kingdom;

import java.io.File;
import java.util.ArrayList;

public class ClaimListener implements Listener {
    private Kingdom instance;
    private File dataFolder;

    public ClaimListener(Kingdom kingdom){
        instance = kingdom;
        dataFolder = new File(instance.getDataFolder().getAbsolutePath() + File.separator + "Claims");
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        Player p = e.getPlayer();
        e.setCancelled(checkClaim(p,e.getBlock().getChunk()));
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
        Player p = e.getPlayer();
        e.setCancelled(checkClaim(p,e.getBlockPlaced().getChunk()));
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        if(e.getHand() == EquipmentSlot.OFF_HAND) return;
        if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_AIR) return;
        Player p = e.getPlayer();
        e.setCancelled(checkClaim(p,e.getClickedBlock().getChunk()));
    }

    @EventHandler
    public void onEntityExplosion(EntityExplodeEvent e){
        File file;
        String worldName;
        String chunkName;
        for(Block b  : new ArrayList<>(e.blockList())){
            worldName = b.getWorld().getName();
            chunkName = b.getChunk().getX() + "_" + b.getChunk().getZ();
            file = new File(dataFolder,worldName + " " + chunkName + ".yml");
            if(file.exists()){
                e.blockList().remove(b);
            }
        }

    }

    @EventHandler
    public void antiWither(EntityChangeBlockEvent e) {
        if (e.getEntity() instanceof Wither) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){
        if(e.getFrom().getChunk() == e.getTo().getChunk()) return;
        String fromChunk = e.getFrom().getWorld().getName() + " " + e.getFrom().getChunk().getX() +"_"+ e.getFrom().getChunk().getZ();
        String toChunk = e.getTo().getWorld().getName() + " " + e.getTo().getChunk().getX() +"_"+ e.getTo().getChunk().getZ();
        File dataFolder = new File(instance.getDataFolder().getAbsolutePath() + File.separator + "Claims");
        File fileFrom = new File(dataFolder, fromChunk + ".yml");
        File fileTo = new File(dataFolder, toChunk + ".yml");
        if(fileFrom.exists() && !fileTo.exists()){e.getPlayer().sendMessage("Entering wilderness");}
        else if(!fileFrom.exists() && fileTo.exists()){
            FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(fileTo);
            e.getPlayer().sendMessage("Entering " + fileConfiguration.getString("town"));
        }

    }

    public boolean checkClaim(Player p, Chunk c){
        String worldName = p.getWorld().getName();
        String chunkName =c.getX() + "_" + c.getZ();
        File file = new File(dataFolder,worldName + " " + chunkName + ".yml");
        if(!file.exists()) return false;
        FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(file);
        if(Kingdom.DB.getTownfromPlayer(p.getUniqueId().toString()) == null) return true;

        return !Kingdom.DB.getTownfromPlayer(p.getUniqueId().toString()).getTownName().equalsIgnoreCase(fileConfig.getString("town"));
    }

}
