package ca.ultracrepidarianism.kingdom.commands.subcommands;

import ca.ultracrepidarianism.kingdom.commands.SubCommand;
import ca.ultracrepidarianism.kingdom.database.models.KDPlayer;
import ca.ultracrepidarianism.kingdom.database.models.enums.PermissionLevelEnum;
import ca.ultracrepidarianism.kingdom.utils.KDMessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class DemoteCommand extends SubCommand {
    @Override
    public String getPermission() {
        return "kingdom.demote";
    }

    @Override
    public String getLabel() {
        return "demote";
    }

    @Override
    public String getUsage() {
        return "/kd demote <user>";
    }

    @Override
    public String getDescription() {
        return "Demote a player in your kingdom";
    }

    @Override
    public void perform(Player player, String[] args) {
        final KDPlayer kdPlayer = database.getPlayerRepository().getPlayerFromBukkitPlayer(player);
        if(kdPlayer.getKingdom() == null){
            player.sendMessage(KDMessageUtil.getMessage("error.global.nokingdom"));
            return;
        }

        if(!kdPlayer.getPermissionLevel().hasPermission(PermissionLevelEnum.OWNER)){
            player.sendMessage(KDMessageUtil.getMessage("error.global.permissionLevel"));
            return;
        }

        if(args.length != 2){
            player.sendMessage(KDMessageUtil.getMessage(getUsage()));
            return;
        }

        final Player targetPlayer = Bukkit.getPlayer(args[1]);
        final KDPlayer targetKdPlayer;
        if (targetPlayer == null) {
            targetKdPlayer = database.getPlayerRepository().getPlayerByName(args[1]);
        }else{
            targetKdPlayer= database.getPlayerRepository().getPlayerFromBukkitPlayer(targetPlayer);
        }

        if(targetKdPlayer == null){
            player.sendMessage(ChatColor.RED + "The player " + args[1] + " doesn't exist.");
            return;
        }

        if (targetKdPlayer.getKingdom().getId() != kdPlayer.getKingdom().getId()) {
            player.sendMessage(KDMessageUtil.getMessage("error.promote.notInKingdom"));
            return;
        }
        PermissionLevelEnum currentPermissionLevel = targetKdPlayer.getPermissionLevel();
        if (currentPermissionLevel.hasPermission(kdPlayer.getPermissionLevel()) || currentPermissionLevel.getLowerPermissionLevel() == null) {
            player.sendMessage(KDMessageUtil.getMessage("error.promote.cantDemote"));
            return;
        }

        database.getPlayerRepository().updatePermissionLevelForPlayer(targetKdPlayer, currentPermissionLevel.getLowerPermissionLevel());
        if(targetPlayer != null){
            targetPlayer.sendMessage(KDMessageUtil.getMessage("success.demote.target"));
        }
        player.sendMessage(KDMessageUtil.getMessage("success.demote.sender"));
    }
}
