package org.github.nullexceptionarg.commands.subcommands;

import org.bukkit.entity.Player;
import org.github.nullexceptionarg.commands.SubCommand;
import org.github.nullexceptionarg.model.Util;
import org.github.nullexceptionarg.model.db;

public class Create extends SubCommand {
    @Override
    public String getPermission() {
        return "kingdom.create";
    }

    @Override
    public String getLabel() {
        return "create";
    }

    @Override
    public String getUsage() {
        return "/kd create <name>";
    }

    @Override
    public String getDescription() {
        return "Allows you to create a town";
    }

    @Override
    public void perform(Player ply, String[] args) {
        if(args.length != 2){
            ply.sendMessage(getUsage());
        }
        else if(db.getTownfromPlayer(ply.getUniqueId().toString()) != null){
            ply.sendMessage(Util.getMessage("error.user.createTown"));
        }else{
            db.createTown(ply,args[1]);
        }
    }
}
