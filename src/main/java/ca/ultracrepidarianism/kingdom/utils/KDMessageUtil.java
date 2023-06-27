package ca.ultracrepidarianism.kingdom.utils;

import ca.ultracrepidarianism.kingdom.Kingdom;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Map;


public class KDMessageUtil {
    public static final String PREFIX = JavaPlugin.getPlugin(Kingdom.class).getConfig().getString("prefix");

    public static String getMessage(final String section) {
        final String message = getMessageFromSection(section);
        if (message != null) {
            return PREFIX + " " + message;
        }
        return PREFIX + " " + section;
    }

    @SafeVarargs
    public static String getMessage(final String section, final Map.Entry<String, String>... args) {
        String message = getMessageFromSection(section);
        if (message == null) {
            return PREFIX + " " + section;
        }

        message = PREFIX + " " + message;

        for (Map.Entry<String, String> entry : args) {
            message = message.replace("%" + entry.getKey() + "%", entry.getValue());
        }

        return message;
    }

    private static String getMessageFromSection(final String section) {
        final File file = new File(JavaPlugin.getPlugin(Kingdom.class).getDataFolder() + File.separator + "messages.yml");
        final YamlConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);

        return fileConfiguration.getString(section);
    }
}
