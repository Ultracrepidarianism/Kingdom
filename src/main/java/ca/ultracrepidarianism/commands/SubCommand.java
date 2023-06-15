package ca.ultracrepidarianism.commands;

import ca.ultracrepidarianism.Kingdom;
import ca.ultracrepidarianism.services.Database;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public abstract class SubCommand {

    protected Kingdom kingdom = JavaPlugin.getPlugin(Kingdom.class);
    protected Database database = Database.getInstance();

    public abstract String getPermission();

//    public String getPermissionError() {
//        File file = new File(kingdom.getDataFolder().getAbsolutePath() + File.separator + "strings.yml");
//        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
//        return ChatColor.DARK_RED + "[" + ChatColor.RED + "Kingdom" + ChatColor.DARK_RED + "] " + fileConfiguration.getString("error.permission") + ChatColor.YELLOW + ChatColor.BOLD + getLabel();
//    }

    public abstract String getLabel();

    public abstract String getUsage();

    public abstract String getDescription();

    public abstract void perform(Player ply, String[] args);
}
