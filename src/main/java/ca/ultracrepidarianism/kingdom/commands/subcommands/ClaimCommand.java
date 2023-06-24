package ca.ultracrepidarianism.kingdom.commands.subcommands;

import ca.ultracrepidarianism.kingdom.commands.SubCommand;
import ca.ultracrepidarianism.kingdom.database.DataFacade;
import ca.ultracrepidarianism.kingdom.database.models.KDChunk;
import ca.ultracrepidarianism.kingdom.database.models.KDClaim;
import ca.ultracrepidarianism.kingdom.database.models.KDPlayer;
import ca.ultracrepidarianism.kingdom.database.models.PermissionLevelEnum;
import ca.ultracrepidarianism.kingdom.utils.KDMessageUtil;
import org.bukkit.Bukkit;
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
    public void perform(final Player player,final String[] args) {

        final KDPlayer kdPlayer = database.players().getPlayer(player);
        if (kdPlayer == null) {
            player.sendMessage(KDMessageUtil.getMessage("error.global.noKingdom"));
            return;
        }
        if (!kdPlayer.getPermissionLevel().hasPermission(PermissionLevelEnum.OFFICER)) {
            player.sendMessage(KDMessageUtil.getMessage("error.global.permissionLevel"));
            return;
        }
        final KDChunk kdChunk = KDChunk.parse(player.getLocation().getChunk());
        final KDClaim kdClaim = database.claims().getClaimFromChunk(kdChunk);
        if (kdClaim != null) {
            player.sendMessage(KDMessageUtil.getMessage("error.claim.alreadyClaimed"));
            return;
        }
        database.claims().createClaim(kdPlayer.getKingdom(), kdChunk);
        player.sendMessage(KDMessageUtil.getMessage("success.claim"));
    }

}
