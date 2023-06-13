package ca.ultracrepidarianism;

import ca.ultracrepidarianism.commands.CommandManager;
import ca.ultracrepidarianism.listener.ClaimListener;
import ca.ultracrepidarianism.listener.KingdomListener;
import ca.ultracrepidarianism.model.KDPlayer;
import ca.ultracrepidarianism.services.Database;
import ca.ultracrepidarianism.utils.HibernateUtil;
import jakarta.transaction.Transactional;
import org.bukkit.plugin.java.JavaPlugin;

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
