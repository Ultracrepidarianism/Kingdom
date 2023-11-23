package ca.ultracrepidarianism.kingdom.commands.subcommands;

import ca.ultracrepidarianism.kingdom.commands.SubCommand;
import ca.ultracrepidarianism.kingdom.database.models.KDPlayer;
import ca.ultracrepidarianism.kingdom.database.models.enums.PermissionLevelEnum;
import ca.ultracrepidarianism.kingdom.utils.KDMessageUtil;
import org.bukkit.entity.Player;

import java.util.Map;

public class KickCommand extends SubCommand {
    @Override
    public String getPermission() {
        return "kingdom.kick";
    }

    @Override
    public String getLabel() {
        return "kick";
    }

    @Override
    public String getUsage() {
        return "/kd kick <playerName>";
    }

    @Override
    public String getDescription() {
        return "Allows you to kick someone from your kingdom.";
    }

    @Override
    public void perform(final Player player, final String[] args) {
        if (args.length != 1) {
            player.sendMessage(getUsage());
            return;
        }

        final KDPlayer officer = database.getPlayerRepository().getPlayerFromBukkitPlayer(player);
        if (officer.getKingdom() == null) {
            KDMessageUtil.sendMessage(player, "error.global.noKingdom");
            return;
        }

        if (!officer.getPermissionLevel().hasPermission(PermissionLevelEnum.OFFICER)) {
            KDMessageUtil.sendMessage(player, "error.global.permissionLevel");
            return;
        }

        final KDPlayer target = database.getPlayerRepository().getPlayerByName(args[0]);
        if (target.getKingdom() != officer.getKingdom()) {
            KDMessageUtil.sendMessage(player, "error.global.notInKingdom");
            return;
        }

        if (target.getPermissionLevel().hasPermission(officer.getPermissionLevel())) {
            KDMessageUtil.sendMessage(player, "error.global.permissionLevel");
            return;
        }

        database.getKingdomRepository().kickPlayer(target);
        KDMessageUtil.sendMessage(player, "success.kick", Map.entry("player", target.getName()));
    }
}
