package ca.ultracrepidarianism.kingdom.commands.subcommands;

import ca.ultracrepidarianism.kingdom.commands.SubCommand;
import ca.ultracrepidarianism.kingdom.database.models.KDPlayer;
import ca.ultracrepidarianism.kingdom.database.models.enums.PermissionLevelEnum;
import ca.ultracrepidarianism.kingdom.utils.KDMessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;

public class DemoteCommand extends SubCommand {
    @Override
    public String getPermission() {
        return "kingdom.demote";
    }

    @Override
    public String getLabel() {
        return "demote";
    }

    @Override
    public String getUsage() {
        return "/kd demote <user>";
    }

    @Override
    public String getDescription() {
        return "Demote a player in your kingdom";
    }

    @Override
    public void perform(final Player player, final String[] args) {
        if (args.length != 2) {
            player.sendMessage(getUsage());
            return;
        }

        final KDPlayer kdPlayer = database.getPlayerRepository().getPlayerFromBukkitPlayer(player);
        if (kdPlayer.getKingdom() == null) {
            KDMessageUtil.sendMessage(player, "error.global.noKingdom");
            return;
        }

        if (!kdPlayer.getPermissionLevel().hasPermission(PermissionLevelEnum.OWNER)) {
            KDMessageUtil.sendMessage(player, "error.global.permissionLevel");
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
            KDMessageUtil.sendMessage(player, "error.global.playerDoesntExist");
            return;
        }

        if (targetKdPlayer.getKingdom() == null || targetKdPlayer.getKingdom().getId() != kdPlayer.getKingdom().getId()) {
            KDMessageUtil.sendMessage(player, "error.global.notInKingdom");
            return;
        }

        final PermissionLevelEnum currentPermissionLevel = targetKdPlayer.getPermissionLevel();
        final PermissionLevelEnum previousPermissionLevel = currentPermissionLevel.getPreviousPermissionLevel();
        // cannot demote a player if it's already at the lowest rank
        if (previousPermissionLevel == null) {
            KDMessageUtil.sendMessage(player, "error.demote.lowest");
        }
        // Cannot demote someone if has the same rank as you or higher
        if (currentPermissionLevel.hasPermission(kdPlayer.getPermissionLevel())) {
            KDMessageUtil.sendMessage(player, "error.demote.cantDemote");
            return;
        }

        database.getPlayerRepository().updatePermissionLevelForPlayer(targetKdPlayer, previousPermissionLevel);
        if (targetPlayer != null) {
            KDMessageUtil.sendMessage(player, "success.demote.target", Map.entry("rank", previousPermissionLevel.name()));
        }
        KDMessageUtil.sendMessage(player, "success.demote.sender");
    }
}
