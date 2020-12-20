package org.github.nullexceptionarg.commands.subcommands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.github.nullexceptionarg.Kingdom;
import org.github.nullexceptionarg.commands.SubCommand;
import org.github.nullexceptionarg.model.Util;

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
        else if(Kingdom.DB.getTownfromPlayer(ply.getUniqueId().toString()) != null){
            ply.sendMessage(Util.getMessage("error.user.createTown"));
        }else{
            Kingdom.DB.createTown(ply,args[1]);
            ply.sendMessage(ChatColor.GREEN + "Town" + args[1] + "successfully created");
        }
    }
}
