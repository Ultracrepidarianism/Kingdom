package ca.ultracrepidarianism.commands.subcommands;

import ca.ultracrepidarianism.commands.SubCommand;
import ca.ultracrepidarianism.model.KDPlayer;
import ca.ultracrepidarianism.utils.KDUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Map;

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
        } else if (kdPlayer != null && kdPlayer.getKingdom() != null) {
            ply.sendMessage(KDUtil.getMessage("error.create.alreadyInKingdom"));
        } else {
            database.createTown(ply, args[1]);


            String message = KDUtil.getMessage("success.create", Map.entry("town", args[1]));
            ply.sendMessage(message);
        }
    }
}
