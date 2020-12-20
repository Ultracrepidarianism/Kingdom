package org.github.nullexceptionarg.commands.subcommands;

import org.bukkit.entity.Player;
import org.github.nullexceptionarg.commands.SubCommand;
import org.github.nullexceptionarg.model.Town;
import org.github.nullexceptionarg.services.DbManager;
import org.github.nullexceptionarg.services.IDatabase;

public class Leave extends SubCommand {
    @Override
    public String getPermission() {
        return "kingdom.leave";
    }

    @Override
    public String getLabel() {
        return "leave";
    }

    @Override
    public String getUsage() {
        return "/kd leave";
    }

    @Override
    public String getDescription() {
        return "Allows you to leave your kingdom.";
    }

    @Override
    public void perform(Player ply, String[] args) {
        IDatabase db = DbManager.DB;
        String uuid = ply.getUniqueId().toString();
        Town town = db.getTownfromPlayer(uuid);
        if(town == null){
            ply.sendMessage("You need to be in a town first.");
        }else if(town.getOwnerUID().equals(uuid)){
            ply.sendMessage("You cannot leave the town if you are the owner. please consider using /kd delete");
        }else{
            db.removePlayerTown(uuid);
        }
    }
}
