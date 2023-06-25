package ca.ultracrepidarianism.kingdom.commands.subcommands;

import ca.ultracrepidarianism.kingdom.database.DataFacade;
import ca.ultracrepidarianism.kingdom.database.models.KDInvite;
import ca.ultracrepidarianism.kingdom.database.models.KDPlayer;
import ca.ultracrepidarianism.kingdom.database.models.enums.SuccessMessageEnum;
import ca.ultracrepidarianism.kingdom.utils.KDMessageUtil;
import com.mysql.cj.util.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ca.ultracrepidarianism.kingdom.commands.SubCommand;

import java.util.List;
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
    public void perform(final Player player,final String[] args) {
        final KDPlayer kdPlayer = database.getPlayerRepository().getPlayerFromBukkitPlayer(player);
        if (kdPlayer != null && kdPlayer.getKingdom() != null) {
            player.sendMessage("Please leave your town first.");
            return;
        }

        final List<KDInvite> kdInvites = database.getPlayerRepository().getPendingInvites(player.getUniqueId().toString());
        if (kdInvites == null) {
            player.sendMessage("You have no pending invitations");
            return;
        }

        KDInvite invite;
        if (args.length == 1) {
            invite = kdInvites.get(kdInvites.size() - 1);
        } else if (args.length == 2) {
            invite = kdInvites.stream().filter(x -> StringUtils.indexOfIgnoreCase(x.getKingdom().getName(),args[1]) >=0).findFirst().orElse(null);
            if (invite == null) {
                player.sendMessage("You haven't been invited to any kingdom named " + args[1]);
                return;
            }
        } else {
            player.sendMessage(getUsage());
            return;
        }

        database.getKingdomRepository().setKingdomForPlayer(invite.getKingdom(),invite.getInvitee());
        DataFacade.getInstance().getPlayerRepository().removePendingInvite(player.getUniqueId().toString(), null);
        final List<KDPlayer> kdPlayers = database.getPlayerRepository().getPlayersForKingdom(invite.getKingdom());
        for (KDPlayer kdP : kdPlayers) {
            Player ply = Bukkit.getPlayer(UUID.fromString(kdP.getId()));
            if(ply != null){
                ply.sendMessage(KDMessageUtil.getMessage(SuccessMessageEnum.KINGDOM_JOIN));
            }
        }
    }
}
