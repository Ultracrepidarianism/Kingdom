package ca.ultracrepidarianism.kingdom.commands.subcommands;

import ca.ultracrepidarianism.kingdom.commands.SubCommand;
import ca.ultracrepidarianism.kingdom.database.models.KDKingdom;
import ca.ultracrepidarianism.kingdom.database.models.KDPlayer;
import ca.ultracrepidarianism.kingdom.utils.KDMessageUtil;
import org.apache.commons.lang3.ArrayUtils;
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
    public void perform(final Player player, final String[] args) {
        if (ArrayUtils.isNotEmpty(args)) {
            player.sendMessage(getUsage());
            return;
        }

        final KDPlayer kdPlayer = database.getPlayerRepository().getPlayerFromBukkitPlayer(player);
        if (kdPlayer == null || kdPlayer.getKingdom() == null) {
            KDMessageUtil.sendMessage(player, "error.global.noKingdom");
            return;
        }

        final KDKingdom kdKingdom = kdPlayer.getKingdom();
        if (!StringUtils.equals(player.getUniqueId().toString(), kdPlayer.getId())) {
            KDMessageUtil.sendMessage(player, "error.global.ownerOnly");
            return;
        }

        database.getKingdomRepository().disbandKingdom(kdKingdom);
        KDMessageUtil.sendMessage(player, "success.disband");
    }
}
