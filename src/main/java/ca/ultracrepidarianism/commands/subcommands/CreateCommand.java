package ca.ultracrepidarianism.commands.subcommands;

import ca.ultracrepidarianism.commands.SubCommand;
import ca.ultracrepidarianism.model.KDPlayer;
import ca.ultracrepidarianism.model.KDUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CreateCommand extends SubCommand {
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
        KDPlayer kdPlayer = database.getPlayer(ply);
        if (args.length != 2) {
            ply.sendMessage(getUsage());
        } else if (kdPlayer != null && kdPlayer.getTown() != null) {
            ply.sendMessage(KDUtil.getMessage("error.user.createTown"));
        } else {
            database.createTown(ply, args[1]);
            ply.sendMessage(ChatColor.GREEN + "Town " + args[1] + " successfully created");
        }
    }
}
