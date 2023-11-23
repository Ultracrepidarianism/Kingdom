package ca.ultracrepidarianism.kingdom.commands.subcommands;

import ca.ultracrepidarianism.kingdom.commands.SubCommand;
import ca.ultracrepidarianism.kingdom.database.models.KDChunk;
import ca.ultracrepidarianism.kingdom.database.models.KDClaim;
import ca.ultracrepidarianism.kingdom.database.models.KDPlayer;
import ca.ultracrepidarianism.kingdom.database.models.enums.PermissionLevelEnum;
import ca.ultracrepidarianism.kingdom.utils.KDMessageUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class UnclaimCommand extends SubCommand {
    @Override
    public String getPermission() {
        return "kingdom.unclaim";
    }

    @Override
    public String getLabel() {
        return "unclaim";
    }

    @Override
    public String getUsage() {
        return "/kd unclaim";
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public void perform(final Player player, final String[] args) {
        if (ArrayUtils.isNotEmpty(args)) {
            player.sendMessage(getUsage());
            return;
        }

        final KDPlayer kdPlayer = database.getPlayerRepository().getPlayerFromBukkitPlayer(player);
        if (kdPlayer == null) {
            KDMessageUtil.sendMessage(player, "error.global.noKingdom");
            return;
        }

        if (!kdPlayer.getPermissionLevel().hasPermission(PermissionLevelEnum.OFFICER)) {
            KDMessageUtil.sendMessage(player, "error.global.permissionLevel");
            return;
        }

        final KDClaim kdClaim = database.getClaimRepository().getClaimFromChunk(KDChunk.parse(player.getLocation().getChunk()));
        if (kdClaim == null) {
            KDMessageUtil.sendMessage(player, "error.unclaim.notClaimed");
            return;
        }

        if (kdClaim.getKingdom().getId() != kdPlayer.getKingdom().getId()) {
            KDMessageUtil.sendMessage(player, "error.unclaim.doNotOwn");
            return;
        }

        final Set<KDChunk> chunks = database.getClaimRepository().getChunksForKingdom(kdPlayer.getKingdom());
        final Set<KDChunk> chunksToReach = getChunksToReach(kdClaim, chunks);

        chunks.remove(kdClaim.getChunk());
        final KDChunk firstChunk = chunksToReach.stream().findFirst().orElse(null);
        if (firstChunk == null || canUnclaim(firstChunk, chunks, chunksToReach, new HashSet<>())) {
            database.getClaimRepository().removeClaim(kdClaim);
            KDMessageUtil.sendMessage(player, "success.unclaim");
            return;
        }
        KDMessageUtil.sendMessage(player, "error.unclaim.cannotUnclaim");
    }

    private Set<KDChunk> getChunksToReach(KDClaim kdClaim, Set<KDChunk> chunks) {
        final Set<KDChunk> chunksToReach = new HashSet<>();
        final KDChunk kdChunk = kdClaim.getChunk();

        KDChunk temp = new KDChunk(kdChunk.getWorld(), kdChunk.getX() - 1, kdChunk.getZ());
        if (chunks.contains(temp)) {
            chunksToReach.add(temp);
        }

        temp = new KDChunk(kdChunk.getWorld(), kdChunk.getX() + 1, kdChunk.getZ());
        if (chunks.contains(temp)) {
            chunksToReach.add(temp);
        }

        temp = new KDChunk(kdChunk.getWorld(), kdChunk.getX(), kdChunk.getZ() - 1);
        if (chunks.contains(temp)) {
            chunksToReach.add(temp);
        }

        temp = new KDChunk(kdChunk.getWorld(), kdChunk.getX(), kdChunk.getZ() + 1);
        if (chunks.contains(temp)) {
            chunksToReach.add(temp);
        }

        return chunksToReach;
    }

    private boolean canUnclaim(KDChunk chunk, Set<KDChunk> allChunks, Set<KDChunk> chunksToReach, Set<KDChunk> chunksReached) {
        if (chunk == null) {
            return false;
        }

        chunksReached.add(chunk);
        chunksToReach.remove(chunk);

        if (CollectionUtils.isEmpty(chunksToReach)) {
            return true;
        }

        final KDChunk N = new KDChunk(chunk.getWorld(), chunk.getX(), chunk.getZ() - 1);
        final KDChunk S = new KDChunk(chunk.getWorld(), chunk.getX(), chunk.getZ() + 1);
        final KDChunk E = new KDChunk(chunk.getWorld(), chunk.getX() + 1, chunk.getZ());
        final KDChunk W = new KDChunk(chunk.getWorld(), chunk.getX() - 1, chunk.getZ());

        final Queue<KDChunk> queuedChunk = new LinkedList<>();
        if (allChunks.contains(N) && !chunksReached.contains(N)) {
            queuedChunk.add(N);
        }
        if (allChunks.contains(S) && !chunksReached.contains(S)) {
            queuedChunk.add(S);
        }
        if (allChunks.contains(E) && !chunksReached.contains(E)) {
            queuedChunk.add(E);
        }
        if (allChunks.contains(W) && !chunksReached.contains(W)) {
            queuedChunk.add(W);
        }

        return canUnclaim(queuedChunk.poll(), allChunks, chunksToReach, chunksReached);

    }
}
