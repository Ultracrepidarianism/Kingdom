package ca.ultracrepidarianism.commands.subcommands;

import ca.ultracrepidarianism.commands.SubCommand;
import ca.ultracrepidarianism.model.KDChunk;
import ca.ultracrepidarianism.model.KDClaim;
import ca.ultracrepidarianism.model.KDPlayer;
import ca.ultracrepidarianism.model.KDUtil;
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
        KDChunk kdChunk = KDChunk.parse(ply.getLocation().getChunk());
        KDClaim kdClaim = database.getClaimFromChunk(kdChunk);
        if (kdClaim != null) {
            try {
                ply.sendMessage(KDUtil.getMessage("error.claim.alreadyclaimed"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            KDPlayer kdPlayer = database.getPlayer(ply);
            if (kdPlayer == null) return;
            if (kdPlayer.getTown() == null) return;
            database.createClaim(kdPlayer, kdChunk);
        }
    }

}
