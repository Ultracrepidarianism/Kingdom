package ca.ultracrepidarianism.utils;

import ca.ultracrepidarianism.Kingdom;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class KDUtil {
    public static String getMessage(String section) {
        File file = new File(JavaPlugin.getPlugin(Kingdom.class).getDataFolder() + File.separator + "messages.yml");
        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);

        final String message = fileConfiguration.getString(section);
        if (message != null) {
            return message;
        }

        return section;
    }
}
