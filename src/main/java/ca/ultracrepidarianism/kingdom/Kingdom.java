package ca.ultracrepidarianism.kingdom;

import ca.ultracrepidarianism.kingdom.commands.CommandManager;
import ca.ultracrepidarianism.kingdom.database.DataFacade;
import ca.ultracrepidarianism.kingdom.listener.ClaimListener;
import ca.ultracrepidarianism.kingdom.listener.KingdomListener;
import org.bukkit.plugin.java.JavaPlugin;

public class Kingdom extends JavaPlugin {

    @Override
    public void onEnable() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
            saveDefaultConfig();
        }

        DataFacade.getInstance();
        getServer().getPluginManager().registerEvents(new KingdomListener(), this);
        getServer().getPluginManager().registerEvents(new ClaimListener(), this);
        getCommand("kingdom").setExecutor(new CommandManager());
    }

    private void runDbTests() {
    }

}
