package ca.ultracrepidarianism.kingdom.commands.subcommands;

import ca.ultracrepidarianism.kingdom.commands.SubCommand;
import ca.ultracrepidarianism.kingdom.database.models.KDChunk;
import ca.ultracrepidarianism.kingdom.database.models.KDClaim;
import ca.ultracrepidarianism.kingdom.database.models.KDPlayer;
import ca.ultracrepidarianism.kingdom.database.models.enums.PermissionLevelEnum;
import ca.ultracrepidarianism.kingdom.utils.KDMessageUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.bukkit.entity.Player;

public class ClaimCommand extends SubCommand {

    @Override
    public String getPermission() {
        return "kingdom.claim";
    }

    @Override
    public String getLabel() {
        return "claim";
    }

    @Override
    public String getUsage() {
        return "/kd claim";
    }

    @Override
    public String getDescription() {
        return "Claim a land for your kingdom";
    }

    @Override
    public void perform(final Player player, final String[] args) {
        if (ArrayUtils.isNotEmpty(args)) {
            player.sendMessage(getUsage());
            return;
        }

        final KDPlayer kdPlayer = database.getPlayerRepository().getPlayerFromBukkitPlayer(player);
        if (kdPlayer.getKingdom() == null) {
            KDMessageUtil.sendMessage(player, "error.global.noKingdom");
            return;
        }

        if (!kdPlayer.getPermissionLevel().hasPermission(PermissionLevelEnum.OFFICER)) {
            KDMessageUtil.sendMessage(player, "error.global.permissionLevel");
            return;
        }

        final KDChunk kdChunk = KDChunk.parse(player.getLocation().getChunk());
        final KDClaim kdClaim = database.getClaimRepository().getClaimFromChunk(kdChunk);
        if (kdClaim != null) {
            KDMessageUtil.sendMessage(player, "error.claim.alreadyClaimed");
            return;
        }

        database.getClaimRepository().createClaim(kdPlayer.getKingdom(), kdChunk);
        KDMessageUtil.sendMessage(player, "success.claim");
    }

}
