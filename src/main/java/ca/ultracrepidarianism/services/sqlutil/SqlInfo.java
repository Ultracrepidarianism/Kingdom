package ca.ultracrepidarianism.services.sqlutil;

import ca.ultracrepidarianism.Kingdom;
import org.bukkit.plugin.java.JavaPlugin;

public class SqlInfo {
    private final String IP;
    private final int Port;
    private final String Database;
    private final String username;
    private final String password;

    public SqlInfo() {
        IP = JavaPlugin.getPlugin(Kingdom.class).getConfig().getString("DBserver.ip");
        Port = JavaPlugin.getPlugin(Kingdom.class).getConfig().getInt("DBserver.port");
        Database = JavaPlugin.getPlugin(Kingdom.class).getConfig().getString("DBserver.database");
        username = JavaPlugin.getPlugin(Kingdom.class).getConfig().getString("DBserver.username");
        password = JavaPlugin.getPlugin(Kingdom.class).getConfig().getString("DBserver.password");
    }

    public String getIP() {
        return IP;
    }
    public int getPort() {
        return Port;
    }
    public String getTablePrefix() {return "kingdom_";}
    public String getDatabase() {
        return Database;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
}
