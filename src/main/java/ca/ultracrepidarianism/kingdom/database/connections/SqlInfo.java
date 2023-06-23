package ca.ultracrepidarianism.kingdom.database.connections;

import ca.ultracrepidarianism.kingdom.Kingdom;
import ca.ultracrepidarianism.kingdom.database.connections.enums.ConnectionTypes;
import org.bukkit.plugin.java.JavaPlugin;

public class SqlInfo extends ConnectionInfo {

    public SqlInfo() {
        super(ConnectionTypes.MYSQL,
                JavaPlugin.getPlugin(Kingdom.class).getConfig().getString("DBserver.ip"),
                JavaPlugin.getPlugin(Kingdom.class).getConfig().getString("DBserver.port"),
                JavaPlugin.getPlugin(Kingdom.class).getConfig().getString("DBserver.database"),
                JavaPlugin.getPlugin(Kingdom.class).getConfig().getString("DBserver.username"),
                JavaPlugin.getPlugin(Kingdom.class).getConfig().getString("DBserver.password")
        );
    }
}
