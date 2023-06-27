package ca.ultracrepidarianism.kingdom.commands.subcommands;

import ca.ultracrepidarianism.kingdom.commands.SubCommand;
import ca.ultracrepidarianism.kingdom.database.models.KDPlayer;
import ca.ultracrepidarianism.kingdom.database.models.enums.PermissionLevelEnum;
import ca.ultracrepidarianism.kingdom.utils.KDMessageUtil;
import org.bukkit.entity.Player;

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
        return "/kd kick";
    }

    @Override
    public String getDescription() {
        return "Allows you to kick someone from your kingdom.";
    }

    @Override
    public void perform(final Player player, final String[] args) {
        if (args.length != 2) {
            player.sendMessage(KDMessageUtil.getMessage(getUsage()));
            return;
        }

        final KDPlayer officer = database.getPlayerRepository().getPlayerFromBukkitPlayer(player);
        if (officer.getKingdom() == null) {
            player.sendMessage(KDMessageUtil.getMessage("error.global.noKingdom"));
            return;
        }

        if (!officer.getPermissionLevel().hasPermission(PermissionLevelEnum.OFFICER)) {
            player.sendMessage(KDMessageUtil.getMessage("error.global.permissionLevel"));
            return;
        }

        final KDPlayer target = database.getPlayerRepository().getPlayerByName(args[1]);
        if (target.getKingdom() != officer.getKingdom()) {
            player.sendMessage(KDMessageUtil.getMessage("error.global.notInKingdom"));
            return;
        }

        if (target.getPermissionLevel().hasPermission(officer.getPermissionLevel())) {
            player.sendMessage(KDMessageUtil.getMessage("error.global.insufficientPermissionLevel"));
            return;
        }

        database.getKingdomRepository().kickPlayer(target);
        player.sendMessage(KDMessageUtil.getMessage("success.kingdom.kick"));
    }
}
