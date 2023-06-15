package ca.ultracrepidarianism.listener;

import ca.ultracrepidarianism.model.KDChunk;
import ca.ultracrepidarianism.model.KDClaim;
import ca.ultracrepidarianism.model.KDTown;
import ca.ultracrepidarianism.services.Database;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.io.File;
import java.util.ArrayList;

public class ClaimListener implements Listener {
    private final Database database = Database.getInstance();

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        e.setCancelled(checkClaim(p, KDChunk.parse(e.getBlock().getChunk())));
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        e.setCancelled(checkClaim(p, KDChunk.parse(e.getBlockPlaced().getChunk())));
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if(e.getHand() == EquipmentSlot.OFF_HAND) return;
        if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_AIR) return;
        Player p = e.getPlayer();
        e.setCancelled(checkClaim(p, KDChunk.parse(e.getClickedBlock().getChunk())));
    }

    @EventHandler
    public void onEntityExplosion(EntityExplodeEvent e) {
        File file;
        String worldName;
        String chunkName;
        for(Block b  : new ArrayList<>(e.blockList())){
            //TODO 02 Mettre dans Database Aussi (Une méthode qu'on appel pour le check (Exists))
//            worldName = b.getWorld().getName();
//            chunkName = b.getChunk().getX() + "_" + b.getChunk().getZ();
//            file = new File(dataFolder,worldName + " " + chunkName + ".yml");
//            if(file.exists()){
//                e.blockList().remove(b);
//            }
            //TODO 02 FIN
        }

    }

    @EventHandler
    public void antiWither(EntityChangeBlockEvent e) {
        if (e.getEntity() instanceof Wither) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if (e.getTo() == null) {
            return;
        }

        if (e.getFrom().getChunk().equals(e.getTo().getChunk())) {
            return;
        }

        KDClaim claim = database.getClaimFromChunk(KDChunk.parse(e.getTo().getChunk()));
        if (claim != null) {
            e.getPlayer().sendMessage("Entering " + claim.getTown().getTownName());
        }
    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent e) {

    }

    public boolean checkClaim(Player player, KDChunk chunk) {
        KDClaim claim = database.getClaimFromChunk(chunk);
        if (claim == null) {
            return false;
        }

        KDTown town = this.database.getTownfromPlayer(player.getUniqueId().toString());
        if (town == null) {
            return true;
        }

        return !town.getMembers().contains(player.getUniqueId().toString());
    }

}
