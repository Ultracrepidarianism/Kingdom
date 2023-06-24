package ca.ultracrepidarianism.kingdom.utils;

import ca.ultracrepidarianism.kingdom.Kingdom;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Map;


public class KDMessageUtil {
    public static String getMessage(final String section) {
        final String message = getTranslation(section);
        if (message != null) {
            return message;
        }
        return section;
    }

    @SafeVarargs
    public static String getMessage(final String section, final Map.Entry<String, String>... args) {
        String translation = getTranslation(section);
        if (translation == null) {
            return section;
        }

        for (Map.Entry<String, String> entry : args) {
            translation = translation.replace("%" + entry.getKey(), entry.getValue());
        }

        return translation;
    }

    private static String getTranslation(final String section) {
        final File file = new File(JavaPlugin.getPlugin(Kingdom.class).getDataFolder() + File.separator + "messages.yml");
        final FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);

        return fileConfiguration.getString(section);
    }
}
