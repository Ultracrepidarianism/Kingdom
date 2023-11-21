package ca.ultracrepidarianism.kingdom.commands.subcommands;

import ca.ultracrepidarianism.kingdom.commands.SubCommand;
import ca.ultracrepidarianism.kingdom.database.models.KDInvite;
import ca.ultracrepidarianism.kingdom.database.models.KDPlayer;
import ca.ultracrepidarianism.kingdom.database.repositories.PlayerRepository;
import ca.ultracrepidarianism.kingdom.utils.KDMessageUtil;
import com.mysql.cj.util.StringUtils;
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
        return "/kd join";
    }

    @Override
    public String getDescription() {
        return "Allows you to join a kingdom.";
    }

    @Override
    public void perform(final Player player, final String[] args) {
        if (args.length == 0 || args.length > 2) {
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

        final KDInvite invite;
        if (args.length == 1) {
            invite = kdInvites.get(kdInvites.size() - 1);
        } else {
            final String kingdomName = args[1];
            invite = kdInvites.stream()
                    .filter(x -> StringUtils.indexOfIgnoreCase(x.getKingdom().getName(), kingdomName) >= 0)
                    .findFirst().orElse(null);
            if (invite == null) {
                KDMessageUtil.sendMessage(
                        player,
                        "error.join.notInvitedToKingdom",
                        Map.entry("kingdom", kingdomName)
                );
                return;
            }
        }

        final KDPlayer invitee = invite.getInvitee();
        database.getKingdomRepository().setKingdomForPlayer(invite.getKingdom(), invitee);
        playerRepository.removeAllPendingInvites(player.getUniqueId().toString());

        final List<KDPlayer> kdPlayers = playerRepository.getPlayersForKingdom(invite.getKingdom());
        final String message = KDMessageUtil.getMessage("success.join", Map.entry("player", invitee.getName()));
        for (final KDPlayer kingdomPlayer : kdPlayers) {
            final Player member = Bukkit.getPlayer(UUID.fromString(kingdomPlayer.getId()));
            if (member != null) {
                KDMessageUtil.sendRawMessage(member, message);
            }
        }
    }
}
