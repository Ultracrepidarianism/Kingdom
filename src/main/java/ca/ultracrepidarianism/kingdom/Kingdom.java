package ca.ultracrepidarianism.kingdom;

import ca.ultracrepidarianism.kingdom.commands.CommandManager;
import ca.ultracrepidarianism.kingdom.model.KDChunk;
import ca.ultracrepidarianism.kingdom.model.KDClaim;
import ca.ultracrepidarianism.kingdom.model.KDKingdom;
import ca.ultracrepidarianism.kingdom.model.KDPlayer;
import ca.ultracrepidarianism.kingdom.model.enums.PermissionLevelEnum;
import ca.ultracrepidarianism.kingdom.services.Database;
import ca.ultracrepidarianism.kingdom.listener.ClaimListener;
import ca.ultracrepidarianism.kingdom.listener.KingdomListener;
import ca.ultracrepidarianism.kingdom.utils.DataSource;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.SQLException;

public class Kingdom extends JavaPlugin {

    @Override
    public void onEnable() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
            saveDefaultConfig();
        }
        Database.getInstance(); // Initialize DB to make sure its properly setup
        getServer().getPluginManager().registerEvents(new KingdomListener(), this);
        getServer().getPluginManager().registerEvents(new ClaimListener(), this);
        getCommand("kingdom").setExecutor(new CommandManager());
    }

    private void runDbTests() {
        KDPlayer player = new KDPlayer("TEST", null, null);
        KDKingdom town = new KDKingdom("TEST", player);
        player.setKingdom(town);
        player.setPermissionLevel(PermissionLevelEnum.OWNER);
        KDClaim claim = new KDClaim(new KDChunk("world", 1, 1), town);
    }

}
