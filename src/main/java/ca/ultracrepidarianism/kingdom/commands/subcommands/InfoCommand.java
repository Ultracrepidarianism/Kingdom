package ca.ultracrepidarianism.kingdom.commands.subcommands;

import ca.ultracrepidarianism.kingdom.commands.SubCommand;
import ca.ultracrepidarianism.kingdom.database.models.KDInvite;
import ca.ultracrepidarianism.kingdom.database.models.KDKingdom;
import ca.ultracrepidarianism.kingdom.database.models.KDPlayer;
import ca.ultracrepidarianism.kingdom.utils.KDMessageUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class InfoCommand extends SubCommand {
    @Override
    public String getPermission() {
        return "kingdom.info";
    }

    @Override
    public String getLabel() {
        return "info";
    }

    @Override
    public String getUsage() {
        return "/kd info";
    }

    @Override
    public String getDescription() {
        return "Displays the information about your kingdom.";
    }

    @Override
    public void perform(final Player player, final String[] args) {
        final KDPlayer kdPlayer = database.getPlayerRepository().getPlayerFromBukkitPlayer(player);
        if (kdPlayer.getKingdom() == null) {
            player.sendMessage(KDMessageUtil.getMessage("error.global.noKingdom"));
            return;
        }

        final KDKingdom kdKingdom = kdPlayer.getKingdom();
        final List<KDPlayer> officers = database.getKingdomRepository().findOfficersForKingdom(kdKingdom);
        final List<KDPlayer> members = database.getKingdomRepository().findMembersForKingdom(kdKingdom);
        final List<KDInvite> invites = database.getKingdomRepository().findPendingInvitesForKingdom(kdKingdom);

        final String processedOfficers = getProcessedOfficersString(officers);
        final String processedMembers = getProcessedMembersString(members);

        final String kingdomInfo = "---------- [" + ChatColor.GOLD + kdKingdom.getName() + ChatColor.RESET + "] ----------\n" +
                ChatColor.BOLD + "Owner: " + ChatColor.RESET + kdKingdom.getOwner().getName() + "\n" +
                ChatColor.BOLD + "Officers: " + ChatColor.RESET + processedOfficers + "\n" +
                ChatColor.BOLD + "Members: " + ChatColor.RESET + processedMembers;

        player.sendMessage(kingdomInfo);
    }

    private String getProcessedOfficersString(List<KDPlayer> officers) {
        String processedOfficers = "No officers";
        if (CollectionUtils.isNotEmpty(officers)) {
            processedOfficers = StringUtils.joinWith(", ", officers);
        }

        return processedOfficers;
    }

    private String getProcessedMembersString(List<KDPlayer> members) {
        String processedMembers = "No members";
        if (CollectionUtils.isNotEmpty(members)) {
            processedMembers = StringUtils.joinWith(", ", members);
        }

        return processedMembers;
    }
}
