package ca.ultracrepidarianism.kingdom.utils;

import ca.ultracrepidarianism.kingdom.Kingdom;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Map;


public class KDMessageUtil {
    public static final String PREFIX = JavaPlugin.getPlugin(Kingdom.class).getConfig().getString("prefix").replaceAll("&", "§");

    public static void sendRawMessage(final Player player, final String message) {
        player.sendMessage(PREFIX + " " + message);
    }

    public static void sendMessage(final Player player, final String section) {
        final String message = PREFIX + " " + getMessage(section);
        player.sendMessage(message);
    }

    @SafeVarargs
    public static void sendMessage(final Player player, final String section, final Map.Entry<String, String>... args) {
        final String message = PREFIX + " " + getMessage(section, args);
        player.sendMessage(message);
    }

    public static String getMessage(final String section) {
        final String message = getMessageFromSection(section);
        System.out.println(message);
        if (message != null) {
            return message.replaceAll("&", "§");
        }
        return section;
    }

    @SafeVarargs
    public static String getMessage(final String section, final Map.Entry<String, String>... args) {
        String message = getMessage(section);
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
