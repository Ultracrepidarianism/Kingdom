package ca.ultracrepidarianism.kingdom.services.sqlutil;

import ca.ultracrepidarianism.kingdom.Kingdom;
import org.bukkit.plugin.java.JavaPlugin;

@Deprecated
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

    public String getUrl() {
        return "jdbc:mysql://" + getIP() + ":" + getPort() + "/" + getDatabase() + "?verifyServerCertificate=false&useSSL=true&requireSSL=true";
    }
    public String getIP() {
        return IP;
    }
    public int getPort() {
        return Port;
    }
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
