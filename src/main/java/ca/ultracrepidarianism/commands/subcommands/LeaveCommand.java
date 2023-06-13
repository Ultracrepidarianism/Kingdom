package ca.ultracrepidarianism.commands.subcommands;

import org.bukkit.entity.Player;
import ca.ultracrepidarianism.commands.SubCommand;
import ca.ultracrepidarianism.model.KDTown;

public class LeaveCommand extends SubCommand {
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
        String uuid = ply.getUniqueId().toString();
        KDTown town = database.getTownfromPlayer(uuid);
        if(town == null){
            ply.sendMessage("You need to be in a town first.");
        }else if(town.getOwner().getUUID().equals(uuid)){
            ply.sendMessage("You cannot leave the town if you are the owner. please consider using /kd delete");
        }else{
            database.removePlayerTown(uuid);
        }
    }
}
