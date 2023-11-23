package ca.ultracrepidarianism.kingdom.commands.subcommands;

import ca.ultracrepidarianism.kingdom.commands.SubCommand;
import ca.ultracrepidarianism.kingdom.database.models.KDPlayer;
import ca.ultracrepidarianism.kingdom.utils.KDMessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;

public class InviteCommand extends SubCommand {
    @Override
    public String getPermission() {
        return "kingdom.invite";
    }

    @Override
    public String getLabel() {
        return "invite";
    }

    @Override
    public String getUsage() {
        return "/kd invite <playerName>";
    }

    @Override
    public String getDescription() {
        return "Allows you to invite a player to your kingdom.";
    }

    @Override
    public void perform(final Player player, final String[] args) {
        if (args.length != 1) {
            player.sendMessage(getUsage());
            return;
        }

        final KDPlayer inviter = database.getPlayerRepository().getPlayerFromBukkitPlayer(player);
        if (inviter.getKingdom() == null) {
            KDMessageUtil.sendMessage(player, "error.global.noKingdom");
            return;
        }

        final Player invitee = Bukkit.getPlayer(args[0]);
        if (invitee == null) {
            KDMessageUtil.sendMessage(player, "error.global.playerDoesntExist");
            return;
        }

        final KDPlayer inviteePlayer = database.getPlayerRepository().getPlayerFromBukkitPlayer(invitee);
        if (inviteePlayer.getKingdom() != null && inviteePlayer.getKingdom().getId() == inviteePlayer.getKingdom().getId()) {
            KDMessageUtil.sendMessage(player, "error.invite.alreadyInYourKingdom", Map.entry("player", inviteePlayer.getName()));
            return;
        }

        database.getPlayerRepository().addPendingInvite(inviter, inviteePlayer);
        KDMessageUtil.sendMessage(
                player,
                "success.invite.sender",
                Map.entry("player", invitee.getDisplayName())
        );
        //player.sendMessage(ChatColor.GREEN + "An invitation to join your kingdom has been sent to " + invitee.getDisplayName() + ".");
        KDMessageUtil.sendMessage(
                invitee,
                "success.invite.target",
                Map.entry("kingdom", inviter.getKingdom().getName())
        );
        //invitee.sendMessage(ChatColor.GREEN + "You have been invited to join the kingdom " + inviter.getKingdom().getName() + ". Please do" + ChatColor.YELLOW + " /kd join" + ChatColor.GREEN + " to join their team.");
    }
}
