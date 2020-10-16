package org.github.nullexceptionarg.commands.subcommands;

import org.bukkit.entity.Player;
import org.github.nullexceptionarg.commands.SubCommand;
import org.github.nullexceptionarg.model.PlayerKD;
import org.github.nullexceptionarg.model.Util;
import org.github.nullexceptionarg.model.db;

import java.io.File;

public class Claim extends SubCommand {

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
        String worldName = ply.getWorld().getName();
        String chunkName = ply.getLocation().getChunk().getX() + "_" + ply.getLocation().getChunk().getZ();
        String claimName = worldName + " " + chunkName;
        File dataFolder = new File(kingdom.getDataFolder().getAbsolutePath() + File.separator + "claims");
        dataFolder.mkdirs();
        File file = new File(dataFolder,claimName + ".yml");
        if(file.exists()){
            try{
                ply.sendMessage(Util.getMessage("error.claim.alreadyclaimed"));
            } catch(Exception e){
                e.printStackTrace();
            }
        }else{
            PlayerKD playerKD = db.getPlayer(ply);
            if(playerKD == null) return;
            if(playerKD.getTown() == null) return;
            db.createClaim(playerKD,claimName);
        }
    }

}
