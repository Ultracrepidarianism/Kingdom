package ca.ultracrepidarianism.commands.subcommands;

import ca.ultracrepidarianism.commands.SubCommand;
import ca.ultracrepidarianism.model.KDChunk;
import ca.ultracrepidarianism.model.KDClaim;
import ca.ultracrepidarianism.model.KDPlayer;
import ca.ultracrepidarianism.utils.KDUtil;
import ca.ultracrepidarianism.model.enums.PermissionLevelEnum;
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
    public void perform(Player ply, String[] args) {
        final KDPlayer kdPlayer = database.getPlayer(ply);
        if (kdPlayer == null) {
            ply.sendMessage(KDUtil.getMessage("error.global.noKingdom"));
            return;
        }
        if (!kdPlayer.getPermissionLevel().hasPermission(PermissionLevelEnum.OFFICER)) {
            ply.sendMessage(KDUtil.getMessage("error.global.permissionLevel"));
            return;
        }
        final KDChunk kdChunk = KDChunk.parse(ply.getLocation().getChunk());
        final KDClaim kdClaim = database.getClaimFromChunk(kdChunk);
        if (kdClaim != null) {
            ply.sendMessage(KDUtil.getMessage("error.claim.alreadyClaimed"));
            return;
        }
        database.createClaim(kdPlayer.getKingdom(), kdChunk);
        ply.sendMessage(KDUtil.getMessage("success.claim"));
    }

}
