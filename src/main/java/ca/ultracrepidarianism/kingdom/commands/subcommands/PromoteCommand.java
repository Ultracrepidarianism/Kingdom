package ca.ultracrepidarianism.kingdom.commands.subcommands;

import ca.ultracrepidarianism.kingdom.commands.SubCommand;
import ca.ultracrepidarianism.kingdom.database.models.KDPlayer;
import ca.ultracrepidarianism.kingdom.database.models.enums.PermissionLevelEnum;
import ca.ultracrepidarianism.kingdom.utils.KDMessageUtil;
import org.bukkit.Bukkit;
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
        return "/kd promote <playerName>";
    }

    @Override
    public String getDescription() {
        return "Promote a player in your kingdom";
    }

    @Override
    public void perform(final Player player, final String[] args) {
        if (args.length != 1) {
            player.sendMessage(getUsage());
            return;
        }

        final KDPlayer kdPlayer = database.getPlayerRepository().getPlayerFromBukkitPlayer(player);
        if (kdPlayer.getKingdom() == null) {
            KDMessageUtil.sendMessage(player, "error.global.noKingdom");
            return;
        }

        if (!kdPlayer.getPermissionLevel().hasPermission(PermissionLevelEnum.OWNER)) {
            KDMessageUtil.sendMessage(player, "error.global.ownerOnly");
            return;
        }

        final String userName = args[0];
        final Player targetPlayer = Bukkit.getPlayer(userName);
        final KDPlayer targetKdPlayer;
        if (targetPlayer == null) {
            targetKdPlayer = database.getPlayerRepository().getPlayerByName(userName);
        } else {
            targetKdPlayer = database.getPlayerRepository().getPlayerFromBukkitPlayer(targetPlayer);
        }

        if (targetKdPlayer == null) {
            KDMessageUtil.sendMessage(player, "error.global.playerDoesntExist");
            return;
        }

        if (targetKdPlayer.getId().equals(kdPlayer.getId())) {
            KDMessageUtil.sendMessage(player, "error.promote.self");
            return;
        }

        if (targetKdPlayer.getKingdom().getId() != kdPlayer.getKingdom().getId()) {
            KDMessageUtil.sendMessage(player, "error.global.notInKingdom");
            return;
        }

        final PermissionLevelEnum currentPermissionLevel = targetKdPlayer.getPermissionLevel();
        final PermissionLevelEnum nextPermissionLevel = currentPermissionLevel.getNextPermissionLevel();
        if (currentPermissionLevel == kdPlayer.getPermissionLevel()) {
            KDMessageUtil.sendMessage(player, "error.promote.cantPromote");
            return;
        }

        if (nextPermissionLevel == null) {
            KDMessageUtil.sendMessage(player, "error.promote.cantGoHigher");
            return;
        }

        database.getPlayerRepository().updatePermissionLevelForPlayer(targetKdPlayer, nextPermissionLevel);
        if (targetPlayer != null) {
            KDMessageUtil.sendMessage(targetPlayer, "success.promote.target");
        }
        KDMessageUtil.sendMessage(player, "success.promote.sender");
    }
}
