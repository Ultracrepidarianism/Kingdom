package ca.ultracrepidarianism.kingdom.commands.subcommands;

import ca.ultracrepidarianism.kingdom.commands.SubCommand;
import ca.ultracrepidarianism.kingdom.database.models.KDKingdom;
import ca.ultracrepidarianism.kingdom.database.models.KDPlayer;
import ca.ultracrepidarianism.kingdom.commands.messages.SuccessMessageEnum;
import ca.ultracrepidarianism.kingdom.utils.KDMessageUtil;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

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
        final KDPlayer kdPlayer = database.getPlayerRepository().getPlayerFromBukkitPlayer(player);
        if (kdPlayer.getKingdom() == null) {
            player.sendMessage(KDMessageUtil.getMessage("error.global.nokingdom"));
            return;
        }

        final KDKingdom kdKingdom = kdPlayer.getKingdom();
        if (!StringUtils.equals(kdKingdom.getOwner().getId(), player.getUniqueId().toString())) {
            database.getKingdomRepository().kickPlayer(kdPlayer);
            player.sendMessage(KDMessageUtil.getMessage(SuccessMessageEnum.KINGDOM_LEAVE));
            return;
        }

        player.sendMessage(KDMessageUtil.getMessage("error.leave.owner"));
        player.sendMessage("You cannot leave the town if you are the owner. Please consider using /kd disband");
    }
}
