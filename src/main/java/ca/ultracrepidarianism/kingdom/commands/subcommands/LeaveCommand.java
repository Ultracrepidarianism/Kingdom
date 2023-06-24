package ca.ultracrepidarianism.kingdom.commands.subcommands;

import ca.ultracrepidarianism.kingdom.database.DataFacade;
import ca.ultracrepidarianism.kingdom.database.models.KDKingdom;
import ca.ultracrepidarianism.kingdom.commands.SubCommand;
import ca.ultracrepidarianism.kingdom.database.models.KDPlayer;
import ca.ultracrepidarianism.kingdom.utils.KDMessageUtil;
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
    public void perform(final Player player,final String[] args) {
        final KDPlayer kdPlayer = DataFacade.getInstance().players().getPlayer(player);
        if (kdPlayer == null) {
            player.sendMessage(KDMessageUtil.getMessage("error.global.nokingdom"));
            //player.sendMessage("You need to be in a town first.");
            return;
        }

        final KDKingdom kdKingdom = kdPlayer.getKingdom();
        if (!StringUtils.equals(kdKingdom.getOwner().getUUID(), player.getUniqueId().toString())) {
            DataFacade.getInstance().players().removePlayer(kdPlayer);
            return;
        }
        player.sendMessage(KDMessageUtil.getMessage("error.leave.owner"));
        player.sendMessage("You cannot leave the town if you are the owner. Please consider using /kd disband");
    }
}
