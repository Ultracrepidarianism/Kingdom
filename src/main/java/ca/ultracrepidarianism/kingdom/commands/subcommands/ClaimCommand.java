package ca.ultracrepidarianism.kingdom.commands.subcommands;

import ca.ultracrepidarianism.kingdom.commands.SubCommand;
import ca.ultracrepidarianism.kingdom.database.DataFacade;
import ca.ultracrepidarianism.kingdom.database.models.KDChunk;
import ca.ultracrepidarianism.kingdom.database.models.KDClaim;
import ca.ultracrepidarianism.kingdom.database.models.KDPlayer;
import ca.ultracrepidarianism.kingdom.database.models.PermissionLevelEnum;
import ca.ultracrepidarianism.kingdom.utils.KDUtil;
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
        final KDPlayer kdPlayer = DataFacade.getInstance().Players().getPlayer(ply, true);
        if (kdPlayer == null) {
            ply.sendMessage(KDUtil.getMessage("error.global.noKingdom"));
            return;
        }
        if (!kdPlayer.getPermissionLevel().hasPermission(PermissionLevelEnum.OFFICER)) {
            ply.sendMessage(KDUtil.getMessage("error.global.permissionLevel"));
            return;
        }
        final KDChunk kdChunk = KDChunk.parse(ply.getLocation().getChunk());
        final KDClaim kdClaim = DataFacade.getInstance().Claims().getClaimFromChunk(kdChunk);
        if (kdClaim != null) {
            ply.sendMessage(KDUtil.getMessage("error.claim.alreadyClaimed"));
            return;
        }
        DataFacade.getInstance().Claims().createClaim(kdPlayer.getKingdom(), kdChunk);
        ply.sendMessage(KDUtil.getMessage("success.claim"));
    }

}
