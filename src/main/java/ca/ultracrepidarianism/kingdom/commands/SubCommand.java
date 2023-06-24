package ca.ultracrepidarianism.kingdom.commands;

import ca.ultracrepidarianism.kingdom.Kingdom;
import ca.ultracrepidarianism.kingdom.database.DataFacade;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class SubCommand {

    protected Kingdom kingdom = JavaPlugin.getPlugin(Kingdom.class);
    protected DataFacade database = DataFacade.getInstance();

    public abstract String getPermission();

    public abstract String getLabel();

    public abstract String getUsage();

    public abstract String getDescription();

    public abstract void perform(Player player, String[] args);
}
