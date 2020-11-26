package org.github.nullexceptionarg.commands.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.github.nullexceptionarg.commands.SubCommand;
import org.github.nullexceptionarg.model.Town;
import org.github.nullexceptionarg.model.db;

public class Invite extends SubCommand {
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
        return null;
    }

    @Override
    public void perform(Player ply, String[] args) {
        if (args.length != 2) {
            ply.sendMessage(getUsage());
            return;
        }
        if (ply.hasPermission(getPermission())) {
            Player p = Bukkit.getPlayer(args[1]);
            if (p == null) {
                ply.sendMessage(ChatColor.RED + "The player " + args[1] + " doesn't exist.");
            } else {
                if (db.getTownfromPlayer(p.getUniqueId().toString()) == null) {
                    ply.sendMessage(ChatColor.GREEN + "An invitation to join your kingdom has been sent to " + args[1] + ".");
                    Town town = db.getTownfromPlayer(ply.getUniqueId().toString());
                    p.sendMessage(ChatColor.GREEN + "You have been invited to join the town " + town.getTownName() + ". Please do" + ChatColor.YELLOW + " /kd accept" + ChatColor.GREEN + " to join their team.");
                    db.addPendingInvite(p.getDisplayName(), town.getTownName());
                } else
                    ply.sendMessage("This player is already in a town.");
            }
        } else {
            ply.sendMessage(ChatColor.RED + "You do not have the permission to use this command.");
        }
    }
}
