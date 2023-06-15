package ca.ultracrepidarianism.commands.subcommands;

import ca.ultracrepidarianism.commands.SubCommand;
import ca.ultracrepidarianism.model.KDChunk;
import ca.ultracrepidarianism.model.KDClaim;
import ca.ultracrepidarianism.model.KDPlayer;
import ca.ultracrepidarianism.model.KDUtil;
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
        return "claims a land for your kingdom";
    }

    @Override
    public void perform(Player ply, String[] args) {
        KDPlayer kdPlayer = database.getPlayer(ply);
        if(kdPlayer == null){
            ply.sendMessage(KDUtil.getMessage("error.kingdom.nokingdom"));
            return;
        }
        if(!kdPlayer.getPermissionLevel().hasPermission(PermissionLevelEnum.OFFICER)){
            ply.sendMessage(KDUtil.getMessage("error.kingdom.permissionLevel"));
            return;
        }
            KDChunk kdChunk = KDChunk.parse(ply.getLocation().getChunk());
            KDClaim kdClaim = database.getClaimFromChunk(kdChunk);
        if (kdClaim != null) {
            ply.sendMessage(KDUtil.getMessage("error.claim.alreadyclaimed"));
            return;
        }
        database.createClaim(kdPlayer.getTown(), kdChunk);
        ply.sendMessage(KDUtil.getMessage("success.kingdom.claim"));
    }

}
