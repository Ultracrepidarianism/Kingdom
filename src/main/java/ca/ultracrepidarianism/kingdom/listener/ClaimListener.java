package ca.ultracrepidarianism.kingdom.listener;

import ca.ultracrepidarianism.kingdom.database.DataFacade;
import ca.ultracrepidarianism.kingdom.database.models.KDChunk;
import ca.ultracrepidarianism.kingdom.database.models.KDClaim;
import ca.ultracrepidarianism.kingdom.database.models.KDKingdom;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ClaimListener implements Listener {

    private final Map<UUID, String> currentPlayerLocationKingdomName = new HashMap<>();

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        final Player player = e.getPlayer();
        e.setCancelled(!canInteract(player, KDChunk.parse(e.getBlock().getChunk())));
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        final Player player = e.getPlayer();
        e.setCancelled(!canInteract(player, KDChunk.parse(e.getBlockPlaced().getChunk())));
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getHand() == EquipmentSlot.OFF_HAND) {
            return;
        }

        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_AIR) {
            return;
        }

        final Player player = e.getPlayer();
        e.setCancelled(!canInteract(player, KDChunk.parse(e.getClickedBlock().getChunk())));
    }

    @EventHandler
    public void onEntityExplosion(EntityExplodeEvent e) {
        final Map<KDChunk, Boolean> claims = new HashMap<>();
        for (final Block b : e.blockList()) {
            final KDChunk currentChunk = KDChunk.parse(b.getChunk());
            final boolean isClaimed;
            if (claims.containsKey(currentChunk)) {
                isClaimed = claims.get(currentChunk);
            } else {
                final KDClaim claim = DataFacade.getInstance().getClaimRepository().getClaimFromChunk(currentChunk);
                isClaimed = claim != null;
                claims.put(currentChunk, isClaimed);
            }

            if (isClaimed) {
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
    public void onPlayerMove(PlayerMoveEvent e) {
        if (e.getTo() == null) {
            return;
        }

        if (e.getFrom().getChunk().equals(e.getTo().getChunk())) {
            return;
        }

        final KDClaim claim = DataFacade.getInstance().getClaimRepository().getClaimFromChunk(KDChunk.parse(e.getTo().getChunk()));
        if (claim != null) {
            final String kingdomName = currentPlayerLocationKingdomName.getOrDefault(e.getPlayer().getUniqueId(), null);
            if (kingdomName == null || !kingdomName.equals(claim.getKingdom().getName())) {
                currentPlayerLocationKingdomName.put(e.getPlayer().getUniqueId(), claim.getKingdom().getName());
                e.getPlayer().sendMessage("Entering " + claim.getKingdom().getName());
            }
        } else {
            currentPlayerLocationKingdomName.remove(e.getPlayer().getUniqueId());
        }
    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent e) {

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
