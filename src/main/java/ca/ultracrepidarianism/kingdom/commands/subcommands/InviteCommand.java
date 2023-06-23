package ca.ultracrepidarianism.kingdom.commands.subcommands;

import ca.ultracrepidarianism.kingdom.database.DataFacade;
import ca.ultracrepidarianism.kingdom.database.models.KDKingdom;
import ca.ultracrepidarianism.kingdom.utils.KDUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import ca.ultracrepidarianism.kingdom.commands.SubCommand;

public class InviteCommand extends SubCommand {
    @Override
    public String getPermission() {
        return "kd.invite";
    }

    @Override
    public String getLabel() {
        return "invite";
    }

    @Override
    public String getUsage() {
        return "/kd invite <playerName>";
    }

    @Override
    public String getDescription() {
        return "Allows you to invite a player to your kingdom.";
    }

    @Override
    public void perform(Player ply, String[] args) {
        if (args.length != 2) {
            ply.sendMessage(getUsage());
            return;
        }
        if(!ply.hasPermission(getPermission())){
            ply.sendMessage(ChatColor.RED + KDUtil.getMessage("error.global.permissionlevel"));
//          ply.sendMessage(ChatColor.RED + "You do not have the permission to use this command.");
            return;
        }
        if(DataFacade.getInstance().Kingdoms().getPlayerKingdom(ply.getUniqueId().toString()) == null){
            ply.sendMessage(KDUtil.getMessage("error.global.noKingdom"));
            return;
        }

        Player p = Bukkit.getPlayer(args[1]);
        if (p == null) {
            ply.sendMessage(ChatColor.RED + "The player " + args[1] + " doesn't exist.");
        } else {
            if (DataFacade.getInstance().Kingdoms().getPlayerKingdom(p.getUniqueId().toString()) == null) {
                ply.sendMessage(ChatColor.GREEN + "An invitation to join your kingdom has been sent to " + args[1] + ".");
                KDKingdom town = DataFacade.getInstance().Kingdoms().getPlayerKingdom(ply.getUniqueId().toString());
                p.sendMessage(ChatColor.GREEN + "You have been invited to join the town " + town.getKingdomName() + ". Please do" + ChatColor.YELLOW + " /kd accept" + ChatColor.GREEN + " to join their team.");
                //TODO
//                DataFacade.getInstance().Players().addPendingInvite(p.getUniqueId().toString(), town.getKingdomName());
            } else
                ply.sendMessage("This player is already in a town.");
        }
    }
}
