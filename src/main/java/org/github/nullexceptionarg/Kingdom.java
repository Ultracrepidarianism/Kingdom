package org.github.nullexceptionarg;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;
import org.github.nullexceptionarg.commands.CommandManager;
import org.github.nullexceptionarg.listener.ClaimListener;
import org.github.nullexceptionarg.listener.KingdomListener;
import org.github.nullexceptionarg.model.Claim;
import org.github.nullexceptionarg.services.DbManager;

import java.io.File;

public class Kingdom extends JavaPlugin {


    public static DbManager DB = new DbManager();

    @Override
    public void onEnable() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new KingdomListener(this),this);
        getServer().getPluginManager().registerEvents(new ClaimListener(this),this);
        getCommand("kingdom").setExecutor(new CommandManager(this));
    }
}
