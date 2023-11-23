package ca.ultracrepidarianism.kingdom.commands.subcommands;

import ca.ultracrepidarianism.kingdom.commands.SubCommand;
import ca.ultracrepidarianism.kingdom.database.models.KDInvite;
import ca.ultracrepidarianism.kingdom.database.models.KDPlayer;
import ca.ultracrepidarianism.kingdom.utils.KDMessageUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class RevokeCommand extends SubCommand {
    @Override
    public String getPermission() {
        return "kingdom.revoke";
    }

    @Override
    public String getLabel() {
        return "revoke";
    }

    @Override
    public String getUsage() {
        return "/kd revoke <playerName>";
    }

    @Override
    public String getDescription() {
        return "Allows you to revoke a player's invitation to your kingdom.";
    }

    @Override
    public void perform(final Player player, final String[] args) {
        if (args.length != 1) {
            player.sendMessage(getUsage());
            return;
        }

        final KDPlayer kdPlayer = database.getPlayerRepository().getPlayerFromBukkitPlayer(player);
        if (kdPlayer == null) {
            KDMessageUtil.sendMessage(player, "error.global.noKingdom");
            return;
        }

        final Player targetPlayer = Bukkit.getPlayer(args[0]);
        if (targetPlayer == null) {
            KDMessageUtil.sendMessage(player, "error.global.playerDoesntExist");
            return;
        }

        final List<KDInvite> kdInvites = database.getPlayerRepository().getPendingInvites(targetPlayer.getUniqueId().toString());
        if (CollectionUtils.isEmpty(kdInvites)) {
            KDMessageUtil.sendMessage(player, "error.revoke.notInvited");
            return;
        }

        final KDInvite kdInvite = kdInvites
                .stream()
                .filter(i -> i.getKingdom().equals(kdPlayer.getKingdom()) &&
                        i.getInvitee().getId().equals(targetPlayer.getUniqueId().toString()))
                .findFirst()
                .orElse(null);
        if (kdInvite == null) {
            KDMessageUtil.sendMessage(player, "error.revoke.notInvited");
            return;
        }

        database.getPlayerRepository().removePendingInvite(targetPlayer.getUniqueId().toString(), kdPlayer.getKingdom());
        KDMessageUtil.sendMessage(player, "success.revoke", Map.entry("player", targetPlayer.getName()));
    }
}