package ca.ultracrepidarianism.commands.subcommands;

import org.bukkit.entity.Player;
import ca.ultracrepidarianism.commands.SubCommand;

import java.util.List;

public class JoinCommand extends SubCommand {
    @Override
    public String getPermission() {
        return "kingdom.join";
    }

    @Override
    public String getLabel() {
        return "join";
    }

    @Override
    public String getUsage() {
        return "/kd join";
    }

    @Override
    public String getDescription() {
        return "Allows you to join a kingdom.";
    }

    @Override
    public void perform(Player ply, String[] args) {
        //T
        if(database.getTownfromPlayer(ply.getUniqueId().toString()) != null){
            ply.sendMessage("Please leave your town first.");
            return;
        }
        List<String> lstInvites = database.getPendingInvites(ply.getUniqueId().toString());
        if(lstInvites == null){
            ply.sendMessage("You have no pending invitations");
            return;
        }
        if(args.length == 1){
            database.setPlayerTown(ply.getUniqueId().toString(),lstInvites.get(lstInvites.size() - 1));
            ply.sendMessage("Successfully joined " + lstInvites.get(lstInvites.size() - 1));
            database.removePendingInvite(ply.getUniqueId().toString(),lstInvites.get(lstInvites.size() - 1));
        }else if(args.length == 2){
            String town = lstInvites.stream().filter(x-> x.toLowerCase().contains(args[1])).findFirst().orElse("");
            if(town.length() == 0)
            {
                ply.sendMessage("You haven't been invited to any kingdom named " + args[1]);
            }else{
                database.setPlayerTown(ply.getUniqueId().toString(),town);
                ply.sendMessage("Successfully joined " + town);
                database.removePendingInvite(ply.getUniqueId().toString(),town);
            }

        }


    }
}
