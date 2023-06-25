package ca.ultracrepidarianism.kingdom.commands.subcommands;

import ca.ultracrepidarianism.kingdom.database.models.KDKingdom;
import ca.ultracrepidarianism.kingdom.database.models.KDPlayer;
import ca.ultracrepidarianism.kingdom.utils.KDMessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import ca.ultracrepidarianism.kingdom.commands.SubCommand;

public class InviteCommand extends SubCommand {
    @Override
    public String getPermission() {
        return "kd.invite";
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
        if (args.length != 2) {
            player.sendMessage(getUsage());
            return;
        }

        final KDPlayer inviter = database.getPlayerRepository().getPlayerFromBukkitPlayer(player);
        if (inviter == null ||  inviter.getKingdom() == null) {
            player.sendMessage(KDMessageUtil.getMessage("error.global.noKingdom"));
            return;
        }

        final Player invitee = Bukkit.getPlayer(args[1]);
        if (invitee == null) {
            player.sendMessage(ChatColor.RED + "The player " + args[1] + " doesn't exist or isn't connected");
            return;
        }

        final KDPlayer inviteePlayer = database.getPlayerRepository().getPlayerFromBukkitPlayer(invitee);
        if (inviteePlayer.getKingdom() != null && inviteePlayer.getKingdom().getId() == inviteePlayer.getKingdom().getId()) {
            player.sendMessage("This player is already in your kingdom.");
            return;
        }

        database.getPlayerRepository().addPendingInvite(inviter, inviteePlayer);
        final KDKingdom kdKingdom = inviter.getKingdom();
        invitee.sendMessage(ChatColor.GREEN + "You have been invited to join the kingdom " + kdKingdom.getName() + ". Please do" + ChatColor.YELLOW + " /kd join" + ChatColor.GREEN + " to join their team.");
        player.sendMessage(ChatColor.GREEN + "An invitation to join your kingdom has been sent to " + invitee.getDisplayName() + ".");
    }
}
