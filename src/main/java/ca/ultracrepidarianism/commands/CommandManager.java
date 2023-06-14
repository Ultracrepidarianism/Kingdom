package ca.ultracrepidarianism.commands;

import ca.ultracrepidarianism.commands.subcommands.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CommandManager implements TabExecutor {
    private final List<SubCommand> subCommands;

    public CommandManager() {
        subCommands = new ArrayList<>();
        subCommands.add(new ClaimCommand());
        subCommands.add(new CreateCommand());
        subCommands.add(new InviteCommand());
        subCommands.add(new JoinCommand());
        subCommands.add(new LeaveCommand());
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
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (s.equalsIgnoreCase("kd")) {
            if (strings.length == 1)
                return subCommands.stream().map(SubCommand::getLabel).collect(Collectors.toList());
        }

        return null;
    }
}
