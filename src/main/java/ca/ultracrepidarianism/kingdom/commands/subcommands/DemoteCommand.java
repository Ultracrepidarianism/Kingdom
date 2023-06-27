package ca.ultracrepidarianism.kingdom.commands.subcommands;

import ca.ultracrepidarianism.kingdom.commands.SubCommand;
import ca.ultracrepidarianism.kingdom.commands.messages.ErrorMessageEnum;
import ca.ultracrepidarianism.kingdom.database.models.KDPlayer;
import ca.ultracrepidarianism.kingdom.database.models.enums.PermissionLevelEnum;
import ca.ultracrepidarianism.kingdom.commands.messages.SuccessMessageEnum;
import ca.ultracrepidarianism.kingdom.utils.KDMessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

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
        final KDPlayer kdPlayer = database.getPlayerRepository().getPlayerFromBukkitPlayer(player);
        if (kdPlayer.getKingdom() == null) {
            player.sendMessage(KDMessageUtil.getMessage(ErrorMessageEnum.KINGDOM_NO_KINGDOM));
            return;
        }

        if (!kdPlayer.getPermissionLevel().hasPermission(PermissionLevelEnum.OWNER)) {
            player.sendMessage(KDMessageUtil.getMessage(ErrorMessageEnum.KINGDOM_PERMISSION_LEVEL));
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
            player.sendMessage(KDMessageUtil.getMessage(ErrorMessageEnum.KINGDOM_PLAYER_DOESNT_EXIST));
            return;
        }

        if (targetKdPlayer.getKingdom() == null || targetKdPlayer.getKingdom().getId() != kdPlayer.getKingdom().getId()) {
            player.sendMessage(KDMessageUtil.getMessage(ErrorMessageEnum.KINGDOM_WRONG_KINGDOM));
            return;
        }

        final PermissionLevelEnum currentPermissionLevel = targetKdPlayer.getPermissionLevel();
        if (currentPermissionLevel.getLowerPermissionLevel() == null || currentPermissionLevel.hasPermission(kdPlayer.getPermissionLevel())) {
            player.sendMessage(KDMessageUtil.getMessage(ErrorMessageEnum.KINGDOM_CANT_DEMOTE));
            return;
        }

        database.getPlayerRepository().updatePermissionLevelForPlayer(targetKdPlayer, currentPermissionLevel.getLowerPermissionLevel());
        if (targetPlayer != null) {
            targetPlayer.sendMessage(KDMessageUtil.getMessage(SuccessMessageEnum.KINGDOM_DEMOTE_TARGET));
        }
        player.sendMessage(KDMessageUtil.getMessage(SuccessMessageEnum.KINGDOM_DEMOTE_SENDER));
    }
}
