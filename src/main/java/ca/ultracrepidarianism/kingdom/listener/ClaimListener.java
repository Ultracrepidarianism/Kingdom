package ca.ultracrepidarianism.kingdom.listener;

import ca.ultracrepidarianism.kingdom.database.DataFacade;
import ca.ultracrepidarianism.kingdom.database.models.KDChunk;
import ca.ultracrepidarianism.kingdom.database.models.KDClaim;
import ca.ultracrepidarianism.kingdom.database.models.KDKingdom;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
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

import java.util.*;

public class ClaimListener implements Listener {

    private final Map<UUID, String> currentPlayerLocationKingdomName = new HashMap<>();

    @EventHandler
    public void onBlockBreak(final BlockBreakEvent event) {
        final Player player = event.getPlayer();
        event.setCancelled(!canInteract(player, KDChunk.parse(event.getBlock().getChunk())));
    }

    @EventHandler
    public void onBlockPlace(final BlockPlaceEvent event) {
        final Player player = event.getPlayer();
        event.setCancelled(!canInteract(player, KDChunk.parse(event.getBlockPlaced().getChunk())));
    }

    @EventHandler
    public void onInteract(final PlayerInteractEvent event) {
        if (event.getHand() == EquipmentSlot.OFF_HAND) {
            return;
        }

        if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_AIR) {
            return;
        }

        final Player player = event.getPlayer();
        event.setCancelled(!canInteract(player, KDChunk.parse(event.getClickedBlock().getChunk())));
    }

    @EventHandler
    public void onEntityExplosion(final EntityExplodeEvent event) {
        final Map<KDChunk, Boolean> claims = new HashMap<>();

        final List<Block> clonedBlockList = new ArrayList<>(event.blockList());
        for (final Block block : clonedBlockList) {
            final KDChunk currentChunk = KDChunk.parse(block.getChunk());
            final boolean isClaimed;
            if (claims.containsKey(currentChunk)) {
                isClaimed = claims.get(currentChunk);
            } else {
                final KDClaim claim = DataFacade.getInstance().getClaimRepository().getClaimFromChunk(currentChunk);
                isClaimed = claim != null;
                claims.put(currentChunk, isClaimed);
            }

            if (isClaimed) {
                event.blockList().remove(block);
            }
        }
    }

    @EventHandler
    public void onEntityChangeBlock(EntityChangeBlockEvent e) {
        if (e.getEntityType() == EntityType.WITHER) {
            e.setCancelled(true);
        } else if (e.getEntityType() == EntityType.RABBIT) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerMove(final PlayerMoveEvent event) {
        if (event.getTo() == null) {
            return;
        }

        if (event.getFrom().getChunk().equals(event.getTo().getChunk())) {
            return;
        }

        final KDClaim claim = DataFacade.getInstance().getClaimRepository().getClaimFromChunk(KDChunk.parse(event.getTo().getChunk()));
        if (claim != null) {
            final String kingdomName = currentPlayerLocationKingdomName.getOrDefault(event.getPlayer().getUniqueId(), null);
            if (kingdomName == null || !kingdomName.equals(claim.getKingdom().getName())) {
                currentPlayerLocationKingdomName.put(event.getPlayer().getUniqueId(), claim.getKingdom().getName());
                event.getPlayer().sendMessage("Entering " + claim.getKingdom().getName());
            }
        } else {
            currentPlayerLocationKingdomName.remove(event.getPlayer().getUniqueId());
        }
    }

    @EventHandler
    public void onChunkUnload(final ChunkUnloadEvent event) {

    }

    public boolean canInteract(final Player player, final KDChunk kdChunk) {
        final KDClaim kdClaim = DataFacade.getInstance().getClaimRepository().getClaimFromChunk(kdChunk);
        if (kdClaim == null) {
            return true;
        }

        final KDKingdom kdKingdom = DataFacade.getInstance().getKingdomRepository().getKingdomByPlayerId(player.getUniqueId().toString());
        if (kdKingdom == null) {
            return false;
        }

        return kdKingdom.getId() == kdClaim.getKingdom().getId();
    }

}
