package ca.ultracrepidarianism.kingdom.commands.subcommands;

import ca.ultracrepidarianism.kingdom.commands.SubCommand;
import ca.ultracrepidarianism.kingdom.database.models.KDKingdom;
import ca.ultracrepidarianism.kingdom.database.models.KDPlayer;
import ca.ultracrepidarianism.kingdom.utils.KDMessageUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
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
        if (ArrayUtils.isNotEmpty(args)) {
            player.sendMessage(getUsage());
            return;
        }

        final KDPlayer kdPlayer = database.getPlayerRepository().getPlayerFromBukkitPlayer(player);
        if (kdPlayer.getKingdom() == null) {
            KDMessageUtil.sendMessage(player, "error.global.noKingdom");
            return;
        }

        final KDKingdom kdKingdom = kdPlayer.getKingdom();
        final List<KDPlayer> officers = database.getKingdomRepository().findOfficersForKingdom(kdKingdom);
        final List<KDPlayer> members = database.getKingdomRepository().findMembersForKingdom(kdKingdom);

        final String processedOfficers = getProcessedOfficersString(officers);
        final String processedMembers = getProcessedMembersString(members);

        final String kingdomInfo = "---------- [" + ChatColor.GOLD + kdKingdom.getName() + ChatColor.RESET + "] ----------\n" +
                ChatColor.RESET + KDMessageUtil.getMessage("message.info.owner") + ": " + ChatColor.RESET + kdKingdom.getOwner().getName() + "\n" +
                ChatColor.RESET + KDMessageUtil.getMessage("message.info.officers") + " (" + officers.size() + "): " + ChatColor.RESET + processedOfficers + "\n" +
                ChatColor.RESET + KDMessageUtil.getMessage("message.info.members") + " (" + members.size() + "): " + ChatColor.RESET + processedMembers;

        KDMessageUtil.sendRawMessage(player, kingdomInfo);
    }

    private String getProcessedOfficersString(List<KDPlayer> officers) {
        if (CollectionUtils.isEmpty(officers)) {
            return ChatColor.RED + KDMessageUtil.getMessage("message.info.noOfficer") + ChatColor.RESET;
        }

        return StringUtils.joinWith(", ", officers);
    }

    private String getProcessedMembersString(List<KDPlayer> members) {
        if (CollectionUtils.isEmpty(members)) {
            return ChatColor.RED + KDMessageUtil.getMessage("message.info.noMember") + ChatColor.RESET;
        }

        return StringUtils.joinWith(", ", members);
    }
}
