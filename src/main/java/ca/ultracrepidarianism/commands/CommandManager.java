package ca.ultracrepidarianism.commands;

import ca.ultracrepidarianism.commands.subcommands.*;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

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
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player p) {
            if (command.getLabel().equalsIgnoreCase("kingdom")) {
                if (args.length > 0) {
                    SubCommand subCommand = subCommands.stream().filter(x -> x.getLabel().equalsIgnoreCase(args[0])).findFirst().orElse(null);
                    if (subCommand != null) {
                        subCommand.perform(p, args);
                    } else {
                        p.sendMessage("This command does not exist");
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
