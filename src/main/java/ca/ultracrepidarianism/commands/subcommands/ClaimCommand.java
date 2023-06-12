package ca.ultracrepidarianism.commands.subcommands;

import ca.ultracrepidarianism.model.KDChunk;
import ca.ultracrepidarianism.model.KDClaim;
import org.bukkit.entity.Player;
import ca.ultracrepidarianism.commands.SubCommand;
import ca.ultracrepidarianism.model.KDPlayer;
import ca.ultracrepidarianism.model.KDUtil;

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
    public void perform(Player ply, String[] args){
        KDChunk chunk = KDChunk.parse(ply.getLocation().getChunk());
        KDClaim claim = database.getClaimFromChunk(chunk);
        if(claim != null){
            try{
                ply.sendMessage(KDUtil.getMessage("error.claim.alreadyclaimed"));
            } catch(Exception e){
                e.printStackTrace();
            }
        }else{
            KDPlayer playerKD = database.getPlayer(ply);
            if(playerKD == null) return;
            if(playerKD.getTown() == null) return;
            database.createClaim(playerKD,chunk);
        }
    }

}
