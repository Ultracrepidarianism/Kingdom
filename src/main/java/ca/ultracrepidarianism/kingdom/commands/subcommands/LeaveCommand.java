package ca.ultracrepidarianism.kingdom.commands.subcommands;

import ca.ultracrepidarianism.kingdom.commands.SubCommand;
import ca.ultracrepidarianism.kingdom.database.models.KDKingdom;
import ca.ultracrepidarianism.kingdom.database.models.KDPlayer;
import ca.ultracrepidarianism.kingdom.utils.KDMessageUtil;
import org.apache.commons.lang3.ArrayUtils;
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
    public void perform(final Player player, final String[] args) {
        if (ArrayUtils.isNotEmpty(args)) {
            player.sendMessage(getUsage());
            return;
        }

        final KDPlayer kdPlayer = database.getPlayerRepository().getPlayerFromBukkitPlayer(player);
        if (kdPlayer.getKingdom() == null) {
            KDMessageUtil.sendMessage(player, "error.global.noKingdom"); // "No kingdom?"
            return;
        }

        final KDKingdom kdKingdom = kdPlayer.getKingdom();
        if (StringUtils.equals(kdKingdom.getOwner().getId(), player.getUniqueId().toString())) {
            KDMessageUtil.sendMessage(player, "error.leave.ownerCantLeave");
            return;
        }

        database.getKingdomRepository().kickPlayer(kdPlayer);
        KDMessageUtil.sendMessage(player, "success.leave");
    }
}
