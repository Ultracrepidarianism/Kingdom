package ca.ultracrepidarianism.kingdom.commands.subcommands;

import ca.ultracrepidarianism.kingdom.commands.SubCommand;
import ca.ultracrepidarianism.kingdom.database.models.KDPlayer;
import ca.ultracrepidarianism.kingdom.database.models.enums.PermissionLevelEnum;
import ca.ultracrepidarianism.kingdom.utils.KDMessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PromoteCommand extends SubCommand {
    @Override
    public String getPermission() {
        return "kingdom.promote";
    }

    @Override
    public String getLabel() {
        return "promote";
    }

    @Override
    public String getUsage() {
        return "/kd promote <user>";
    }

    @Override
    public String getDescription() {
        return "Promote a player in your kingdom";
    }

    @Override
    public void perform(final Player player, final String[] args) {
        final KDPlayer kdPlayer = database.getPlayerRepository().getPlayerFromBukkitPlayer(player);
        if (kdPlayer.getKingdom() == null) {
            player.sendMessage(KDMessageUtil.getMessage("error.global.nokingdom"));
            return;
        }

        if (!kdPlayer.getPermissionLevel().hasPermission(PermissionLevelEnum.OWNER)) {
            player.sendMessage(KDMessageUtil.getMessage("error.global.permissionLevel"));
            return;
        }

        if (args.length != 2) {
            player.sendMessage(KDMessageUtil.getMessage(getUsage()));
            return;
        }

        final Player targetPlayer = Bukkit.getPlayer(args[1]);
        final KDPlayer targetKdPlayer;
        if (targetPlayer == null) {
            targetKdPlayer = database.getPlayerRepository().getPlayerByName(args[1]);
        } else {
            targetKdPlayer = database.getPlayerRepository().getPlayerFromBukkitPlayer(targetPlayer);
        }

        if (targetKdPlayer == null) {
            player.sendMessage(ChatColor.RED + "The player " + args[1] + " doesn't exist.");
            return;
        }

        if (targetKdPlayer.getKingdom().getId() != kdPlayer.getKingdom().getId()) {
            player.sendMessage(KDMessageUtil.getMessage("error.promote.notInKingdom"));
            return;
        }


        final PermissionLevelEnum currentPermissionLevel = targetKdPlayer.getPermissionLevel();
        if (currentPermissionLevel.getHigherPermissionLevel() == kdPlayer.getPermissionLevel()) {
            player.sendMessage(KDMessageUtil.getMessage("error.promote.cantGoHigher"));
            return;
        }

        database.getPlayerRepository().updatePermissionLevelForPlayer(targetKdPlayer, currentPermissionLevel.getHigherPermissionLevel());
        if (targetPlayer != null) {
            targetPlayer.sendMessage(KDMessageUtil.getMessage("success.promote.target"));
        }
        player.sendMessage(KDMessageUtil.getMessage("success.promote.sender"));
    }
}
