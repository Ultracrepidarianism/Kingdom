package ca.ultracrepidarianism.model;

import ca.ultracrepidarianism.Kingdom;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class KDUtil {
    public static String getMessage(String section){
        File file = new File(JavaPlugin.getPlugin(Kingdom.class).getDataFolder() + File.separator + "strings.yml");
        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
        return fileConfiguration.getString(section);
    }


}
