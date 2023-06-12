package ca.ultracrepidarianism;

import org.bukkit.plugin.java.JavaPlugin;
import ca.ultracrepidarianism.commands.CommandManager;
import ca.ultracrepidarianism.listener.ClaimListener;
import ca.ultracrepidarianism.listener.KingdomListener;
import ca.ultracrepidarianism.services.Database;

public class Kingdom extends JavaPlugin {

    @Override
    public void onEnable() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        saveDefaultConfig();
        Database.getInstance(); // Initialize DB to make sure its properly setup
        getServer().getPluginManager().registerEvents(new KingdomListener(),this);
        getServer().getPluginManager().registerEvents(new ClaimListener(),this);
        getCommand("kingdom").setExecutor(new CommandManager(this));
    }
}
