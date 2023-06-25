package ca.ultracrepidarianism.kingdom.database.connections;

import ca.ultracrepidarianism.kingdom.Kingdom;
import ca.ultracrepidarianism.kingdom.database.connections.enums.ConnectionType;
import org.bukkit.plugin.java.JavaPlugin;

public class MySqlInfo extends ConnectionInfo {

    public MySqlInfo() {
        super(
            ConnectionType.MYSQL,
            JavaPlugin.getPlugin(Kingdom.class).getConfig().getString("DBserver.ip"),
            JavaPlugin.getPlugin(Kingdom.class).getConfig().getString("DBserver.port"),
            JavaPlugin.getPlugin(Kingdom.class).getConfig().getString("DBserver.database"),
            JavaPlugin.getPlugin(Kingdom.class).getConfig().getString("DBserver.username"),
            JavaPlugin.getPlugin(Kingdom.class).getConfig().getString("DBserver.password")
        );
    }

    @Override
    public String getUrl() {
        return "jdbc:mysql://" + address + ":" + port + "/" + database;
    }
}
