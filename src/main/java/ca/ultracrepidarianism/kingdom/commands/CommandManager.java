package ca.ultracrepidarianism.kingdom.commands;

import ca.ultracrepidarianism.kingdom.commands.subcommands.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

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
        subCommands.add(new RevokeCommand());
        subCommands.add(new PromoteCommand());
        subCommands.add(new DemoteCommand());
        subCommands.add(new KickCommand());
        subCommands.add(new InfoCommand());
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, String[] args) {
        if (commandSender instanceof Player player) {
            if (command.getLabel().equalsIgnoreCase("kingdom")) {
                if (args.length > 0) {
                    final String commandName = args[0];
                    final String[] filteredArgs = ArrayUtils.remove(args, 0);

                    final SubCommand subCommand = subCommands.stream().filter(x -> x.getLabel().equalsIgnoreCase(commandName)).findFirst().orElse(null);
                    if (subCommand != null) {
//                        if (player.hasPermission(subCommand.getPermission())) {
                        subCommand.perform(player, filteredArgs);
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
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String prefix, String[] strings) {
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
