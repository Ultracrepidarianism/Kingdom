package ca.ultracrepidarianism.kingdom.commands.subcommands;

import ca.ultracrepidarianism.kingdom.commands.SubCommand;
import ca.ultracrepidarianism.kingdom.database.models.KDInvite;
import ca.ultracrepidarianism.kingdom.database.models.KDPlayer;
import ca.ultracrepidarianism.kingdom.utils.KDMessageUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class RevokeCommand extends SubCommand {
    @Override
    public String getPermission() {
        return "kd.revoke";
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
        if (args.length != 2) {
            player.sendMessage(getUsage());
            return;
        }

        final KDPlayer kdPlayer = database.getPlayerRepository().getPlayerFromBukkitPlayer(player);
        if (kdPlayer == null) {
            player.sendMessage(KDMessageUtil.getMessage("error.global.noKingdom"));
            return;
        }

        final Player targetPlayer = Bukkit.getPlayer(args[1]);
        if (targetPlayer == null) {
            player.sendMessage(ChatColor.RED + "The player " + args[1] + " doesn't exist.");
            return;
        }

        final List<KDInvite> kdInvites = database.getPlayerRepository().getPendingInvites(targetPlayer.getUniqueId().toString());
        if (CollectionUtils.isEmpty(kdInvites)) {
            player.sendMessage("error.revoke.notInvited");
            return;
        }

        final KDInvite kdInvite = kdInvites
                .stream()
                .filter(i -> i.getKingdom().equals(kdPlayer.getKingdom()) &&
                        i.getInvitee().getId().equals(targetPlayer.getUniqueId().toString()))
                .findFirst()
                .orElse(null);
        if (kdInvite == null) {
            player.sendMessage("error.revoke.notInvited");
            return;
        }

        database.getPlayerRepository().removePendingInvite(targetPlayer.getUniqueId().toString(), kdPlayer.getKingdom());
        player.sendMessage("success.revoke");
    }
}