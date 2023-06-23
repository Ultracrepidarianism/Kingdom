package ca.ultracrepidarianism.kingdom.commands.subcommands;

import ca.ultracrepidarianism.kingdom.commands.SubCommand;
import ca.ultracrepidarianism.kingdom.database.DataFacade;
import ca.ultracrepidarianism.kingdom.database.models.KDPlayer;
import ca.ultracrepidarianism.kingdom.utils.KDUtil;
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
        KDPlayer kdPlayer = DataFacade.getInstance().Players().getPlayer(ply, true);
        if (args.length != 2) {
            ply.sendMessage(getUsage());
        } else if (kdPlayer != null && kdPlayer.getKingdom() != null) {
            ply.sendMessage(KDUtil.getMessage("error.create.alreadyInKingdom"));
        } else {
            DataFacade.getInstance().Kingdoms().createKingdom(ply, args[1]);

            String message = KDUtil.getMessage("success.create", Map.entry("town", args[1]));
            ply.sendMessage(message);
        }
    }
}
