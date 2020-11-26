package org.github.nullexceptionarg.model;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.github.nullexceptionarg.Kingdom;

import java.io.File;

public class Util {
    public static String getMessage(String section){
        File file = new File(JavaPlugin.getPlugin(Kingdom.class).getDataFolder() + File.separator + "strings.yml");
        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
        return fileConfiguration.getString(section);
    }


}
