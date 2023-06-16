package ca.ultracrepidarianism.commands.subcommands;

import ca.ultracrepidarianism.commands.SubCommand;
import ca.ultracrepidarianism.model.KDPlayer;
import ca.ultracrepidarianism.model.KDKingdom;
import ca.ultracrepidarianism.utils.KDUtil;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.entity.Player;

public class LeaveCommand extends SubCommand {
    @Override
    public String getPermission() {
        return "kingdom.leave";
    }

    @Override
    public String getLabel() {
        return "leave";
    }

    @Override
    public String getUsage() {
        return "/kd leave";
    }

    @Override
    public String getDescription() {
        return "Allows you to leave your kingdom.";
    }

    @Override
    public void perform(Player player, String[] args) {
        final KDPlayer kdPlayer = database.getPlayer(player);
        if (kdPlayer == null) {
            player.sendMessage(KDUtil.getMessage("error.global.nokingdom"));
            //player.sendMessage("You need to be in a town first.");
            return;
        }

        final KDKingdom kdKingdom = kdPlayer.getKingdom();
        if (!StringUtils.equals(kdKingdom.getOwner().getUUID(), player.getUniqueId().toString())) {
            database.removePlayer(kdPlayer);
            return;
        }
        player.sendMessage(KDUtil.getMessage("error.leave.owner"));
        player.sendMessage("You cannot leave the town if you are the owner. Please consider using /kd delete");
    }
}
