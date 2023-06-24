package ca.ultracrepidarianism.kingdom.commands;

import ca.ultracrepidarianism.kingdom.commands.subcommands.*;
import ca.ultracrepidarianism.kingdom.database.models.KDInvite;
import ca.ultracrepidarianism.kingdom.utils.KDMessageUtil;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandManager implements TabExecutor {

    private final List<SubCommand> subCommands;

    public CommandManager() {
        subCommands = new ArrayList<>();
        subCommands.add(new ClaimCommand());
        subCommands.add(new UnclaimCommand());
        subCommands.add(new CreateCommand());
        subCommands.add(new InviteCommand());
        subCommands.add(new JoinCommand());
        subCommands.add(new LeaveCommand());
        subCommands.add(new DisbandCommand());
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player player) {
            if (command.getLabel().equalsIgnoreCase("kingdom")) {
                if (args.length > 0) {
                    final SubCommand subCommand = subCommands.stream().filter(x -> x.getLabel().equalsIgnoreCase(args[0])).findFirst().orElse(null);
                    if (subCommand != null) {
//                        if (player.hasPermission(subCommand.getPermission())) {
                            subCommand.perform(player, args);
//                        } else {
//                            player.sendMessage(ChatColor.RED + KDMessageUtil.getMessage("error.global.permissionLevel"));
////                            player.sendMessage(ChatColor.RED + "You do not have the permission to use this command.");
//                        }
                    } else {
                        player.sendMessage("This command does not exist");
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String prefix, String[] strings) {
        if (StringUtils.equalsAnyIgnoreCase(prefix, "kd", "kingdom")) {
            if (strings.length == 1) {
                return subCommands
                        .stream()
                        .map(SubCommand::getLabel)
                        .filter(c -> StringUtils.startsWithIgnoreCase(c, strings[0]))
                        .toList();
            }
        }

        return null;
    }
}
