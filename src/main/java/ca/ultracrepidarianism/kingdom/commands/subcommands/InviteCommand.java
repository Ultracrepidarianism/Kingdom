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

        final KDPlayer inviter = database.players().getPlayer(player, true);
        if (inviter == null) {
            player.sendMessage(KDMessageUtil.getMessage("error.global.noKingdom"));
            return;
        }

        final Player invitee = Bukkit.getPlayer(args[1]);
        if (invitee == null) {
            player.sendMessage(ChatColor.RED + "The player " + args[1] + " doesn't exist.");
            return;
        }

        final KDKingdom inviteeKingdom = database.kingdoms().getPlayerKingdom(invitee.getUniqueId().toString());
        if (inviteeKingdom != null) {
            player.sendMessage("This player is already in a town.");
            return;
        }

        database.players().addPendingInvite(inviter, invitee);
        final KDKingdom kdKingdom = inviter.getKingdom();
        invitee.sendMessage(ChatColor.GREEN + "You have been invited to join the town " + kdKingdom.getName() + ". Please do" + ChatColor.YELLOW + " /kd accept" + ChatColor.GREEN + " to join their team.");
        player.sendMessage(ChatColor.GREEN + "An invitation to join your kingdom has been sent to " + invitee.getDisplayName() + ".");
    }
}
