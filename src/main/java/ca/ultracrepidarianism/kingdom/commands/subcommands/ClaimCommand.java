package ca.ultracrepidarianism.kingdom.commands.subcommands;

import ca.ultracrepidarianism.kingdom.commands.SubCommand;
import ca.ultracrepidarianism.kingdom.commands.messages.ErrorMessageEnum;
import ca.ultracrepidarianism.kingdom.database.models.KDChunk;
import ca.ultracrepidarianism.kingdom.database.models.KDClaim;
import ca.ultracrepidarianism.kingdom.database.models.KDPlayer;
import ca.ultracrepidarianism.kingdom.database.models.enums.PermissionLevelEnum;
import ca.ultracrepidarianism.kingdom.commands.messages.SuccessMessageEnum;
import ca.ultracrepidarianism.kingdom.utils.KDMessageUtil;
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
        final KDPlayer kdPlayer = database.getPlayerRepository().getPlayerFromBukkitPlayer(player);
        if (kdPlayer.getKingdom() == null) {
            player.sendMessage(KDMessageUtil.getMessage(ErrorMessageEnum.KINGDOM_NO_KINGDOM));
            return;
        }

        if (!kdPlayer.getPermissionLevel().hasPermission(PermissionLevelEnum.OFFICER)) {
            player.sendMessage(KDMessageUtil.getMessage(ErrorMessageEnum.KINGDOM_PERMISSION_LEVEL));
            return;
        }

        final KDChunk kdChunk = KDChunk.parse(player.getLocation().getChunk());
        final KDClaim kdClaim = database.getClaimRepository().getClaimFromChunk(kdChunk);
        if (kdClaim != null) {
            player.sendMessage(KDMessageUtil.getMessage(ErrorMessageEnum.KINGDOM_ALREADY_CLAIMED));
            return;
        }

        database.getClaimRepository().createClaim(kdPlayer.getKingdom(), kdChunk);
        player.sendMessage(KDMessageUtil.getMessage(SuccessMessageEnum.KINGDOM_CLAIM));
    }

}
