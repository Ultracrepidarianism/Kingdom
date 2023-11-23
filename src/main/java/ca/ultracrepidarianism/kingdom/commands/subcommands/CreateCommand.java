package ca.ultracrepidarianism.kingdom.commands.subcommands;

import ca.ultracrepidarianism.kingdom.commands.SubCommand;
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
    public void perform(final Player player, final String[] args) {
        if (args.length != 1) {
            player.sendMessage(getUsage());
            return;
        }

        final KDPlayer kdPlayer = database.getPlayerRepository().getPlayerFromBukkitPlayer(player);
        if (kdPlayer.getKingdom() != null) {
            KDMessageUtil.sendMessage(player, "error.global.alreadyInKingdom");
            return;
        }

        final String kingdomName = args[0];
        database.getKingdomRepository().createKingdom(kdPlayer, kingdomName);
        KDMessageUtil.sendMessage(player, "success.create", Map.entry("kingdom", kingdomName));
    }
}
