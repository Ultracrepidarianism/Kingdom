package ca.ultracrepidarianism.kingdom.commands.subcommands;

import ca.ultracrepidarianism.kingdom.model.KDKingdom;
import ca.ultracrepidarianism.kingdom.model.KDPlayer;
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
    public void perform(Player player, String[] args) {
        KDPlayer kdPlayer = database.getPlayer(player);
        if (kdPlayer == null) {

            player.sendMessage("You are not in a Kingdom.");
            return;
        }

        KDKingdom kdKingdom = kdPlayer.getKingdom();
        if (!StringUtils.equals(player.getUniqueId().toString(), kdPlayer.getUUID())) {
            player.sendMessage("Only the Town Owner can perform this action.");
            return;
        }

        database.removeTown(kdKingdom);
        player.sendMessage("Kingdom has been disbanded.");
    }
}
