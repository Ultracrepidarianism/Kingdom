package ca.ultracrepidarianism.kingdom.commands.subcommands;

import ca.ultracrepidarianism.kingdom.database.models.KDKingdom;
import ca.ultracrepidarianism.kingdom.database.models.KDPlayer;
import ca.ultracrepidarianism.kingdom.commands.SubCommand;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.entity.Player;

public class DisbandCommand extends SubCommand {
    @Override
    public String getPermission() {
        return "kingdom.disband";
    }

    @Override
    public String getLabel() {
        return "disband";
    }

    @Override
    public String getUsage() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public void perform(final Player player,final String[] args) {
        final KDPlayer kdPlayer = database.getPlayerRepository().getPlayerFromBukkitPlayer(player);
        if (kdPlayer == null || kdPlayer.getKingdom() == null) {
            player.sendMessage("You are not in a Kingdom.");
            return;
        }

        final KDKingdom kdKingdom = kdPlayer.getKingdom();
        if (!StringUtils.equals(player.getUniqueId().toString(), kdPlayer.getId())) {
            player.sendMessage("Only the Kingdom's ruler can perform this action.");
            return;
        }

        database.getKingdomRepository().removeKingdom(kdKingdom);
        player.sendMessage("Kingdom has been disbanded.");
    }
}
