package ca.ultracrepidarianism.kingdom.commands.subcommands;

import ca.ultracrepidarianism.kingdom.commands.SubCommand;
import ca.ultracrepidarianism.kingdom.database.DataFacade;
import ca.ultracrepidarianism.kingdom.database.models.KDPlayer;
import ca.ultracrepidarianism.kingdom.utils.KDMessageUtil;
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
    public void perform(final Player player,final String[] args) {
        final KDPlayer kdPlayer = DataFacade.getInstance().players().getPlayer(player);
        if (args.length != 2) {
            player.sendMessage(getUsage());
        } else if (kdPlayer != null && kdPlayer.getKingdom() != null) {
            player.sendMessage(KDMessageUtil.getMessage("error.create.alreadyInKingdom"));
        } else {
            database.kingdoms().createKingdom(player, args[1]);
            player.sendMessage(KDMessageUtil.getMessage("success.create", Map.entry("kingdom", args[1])));
        }
    }
}
