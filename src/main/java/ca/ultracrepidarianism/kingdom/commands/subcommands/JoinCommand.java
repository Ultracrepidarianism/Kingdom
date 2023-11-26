package ca.ultracrepidarianism.kingdom.commands.subcommands;

import ca.ultracrepidarianism.kingdom.commands.SubCommand;
import ca.ultracrepidarianism.kingdom.database.models.KDInvite;
import ca.ultracrepidarianism.kingdom.database.models.KDPlayer;
import ca.ultracrepidarianism.kingdom.database.repositories.PlayerRepository;
import ca.ultracrepidarianism.kingdom.utils.KDMessageUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class JoinCommand extends SubCommand {
    @Override
    public String getPermission() {
        return "kingdom.join";
    }

    @Override
    public String getLabel() {
        return "join";
    }

    @Override
    public String getUsage() {
        return "/kd join (kingdomName)";
    }

    @Override
    public String getDescription() {
        return "Allows you to join a kingdom.";
    }

    @Override
    public void perform(final Player player, final String[] args) {
        if (args.length > 1) {
            player.sendMessage(getUsage());
            return;
        }

        final PlayerRepository playerRepository = database.getPlayerRepository();

        final KDPlayer kdPlayer = playerRepository.getPlayerFromBukkitPlayer(player);
        if (kdPlayer.getKingdom() != null) {
            KDMessageUtil.sendMessage(player, "error.join.leaveKingdomFirst");
            return;
        }

        final List<KDInvite> kdInvites = playerRepository.getPendingInvites(player.getUniqueId().toString());
        if (kdInvites == null) {
            KDMessageUtil.sendMessage(player, "error.join.noPendingInvites");
            return;
        }

        final String kdName = ArrayUtils.get(args, 0, null);
        final KDInvite invite = getInviteFromArgs(kdInvites, kdName);
        if (invite == null) {
            KDMessageUtil.sendMessage(
                    player,
                    "error.join.notInvitedToKingdom",
                    Map.entry("kingdom", kdName)
            );
            return;
        }

        final KDPlayer invitee = invite.getInvitee();
        database.getKingdomRepository().setKingdomForPlayer(invite.getKingdom(), invitee);
        playerRepository.removeAllPendingInvites(player.getUniqueId().toString());

        final List<KDPlayer> kdPlayers = invite.getKingdom().getMembers();
        final String message = KDMessageUtil.getMessage("success.join", Map.entry("player", invitee.getName()));
        for (final KDPlayer kingdomPlayer : kdPlayers) {
            final Player member = Bukkit.getPlayer(UUID.fromString(kingdomPlayer.getId()));
            if (member != null) {
                KDMessageUtil.sendRawMessage(member, message);
            }
        }
    }

    /**
     * Get invite for the kdName or returns the first invite in the invites list.
     * When the kdInvites is empty and kdName is null, it'll return null
     *
     * @param kdInvites
     * @param kdName
     * @return The kdInvite or null
     */
    private KDInvite getInviteFromArgs(final List<KDInvite> kdInvites, final String kdName) {
        if (StringUtils.isNotEmpty(kdName)) {
            return kdInvites.stream()
                    .filter(x -> StringUtils.indexOfIgnoreCase(x.getKingdom().getName(), kdName) >= 0)
                    .findFirst().orElse(null);
        }

        return kdInvites.get(kdInvites.size() - 1);
    }
}
