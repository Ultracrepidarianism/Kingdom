package ca.ultracrepidarianism.kingdom;

import ca.ultracrepidarianism.kingdom.commands.CommandManager;
import ca.ultracrepidarianism.kingdom.database.DataFacade;
import ca.ultracrepidarianism.kingdom.database.connections.ConnectionFactory;
import ca.ultracrepidarianism.kingdom.database.connections.enums.ConnectionTypes;
import ca.ultracrepidarianism.kingdom.listener.ClaimListener;
import ca.ultracrepidarianism.kingdom.listener.KingdomListener;
import ca.ultracrepidarianism.kingdom.database.models.KDChunk;
import ca.ultracrepidarianism.kingdom.database.models.KDClaim;
import ca.ultracrepidarianism.kingdom.database.models.KDKingdom;
import ca.ultracrepidarianism.kingdom.database.models.KDPlayer;
import ca.ultracrepidarianism.kingdom.database.models.PermissionLevelEnum;
import org.bukkit.plugin.java.JavaPlugin;

public class Kingdom extends JavaPlugin {

    @Override
    public void onEnable() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
            saveDefaultConfig();
        }
        ConnectionTypes type = ConnectionTypes.valueOf(getConfig().getString("DBserver.type"));
        new DataFacade(ConnectionFactory.get(type));

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
