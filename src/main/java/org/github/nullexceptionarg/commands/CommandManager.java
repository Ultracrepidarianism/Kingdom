package org.github.nullexceptionarg.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.github.nullexceptionarg.Kingdom;
import org.github.nullexceptionarg.commands.subcommands.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.List;
import java.util.stream.Collectors;

public class CommandManager implements TabExecutor {
    private Kingdom instance;
    private FileConfiguration cfg;
    private List<SubCommand> lstSubCommands;
    private Dictionary<SubCommand, SubCommand> mapSub;

    public CommandManager(Kingdom kingdom) {
        instance = kingdom;
        cfg = instance.getConfig();
        lstSubCommands = new ArrayList<>();
        lstSubCommands.add(new Claim());
        lstSubCommands.add(new Create());
        lstSubCommands.add(new Invite());
        lstSubCommands.add(new Join());
        lstSubCommands.add(new Leave());
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(commandSender instanceof Player){
            Player p = (Player) commandSender;
            if(command.getLabel().equalsIgnoreCase("kingdom")){
                if(args.length > 0){
                    SubCommand subCommand = lstSubCommands.stream().filter(x -> x.getLabel().equalsIgnoreCase(args[0])).findFirst().orElse(null);
                    if(subCommand != null){
                        subCommand.perform(p,args);
                    }else{
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
        if(s.equalsIgnoreCase("kd")){
            if(strings.length == 1)
                return lstSubCommands.stream().map(SubCommand::getLabel).collect(Collectors.toList());
        }

        return null;
    }
}
