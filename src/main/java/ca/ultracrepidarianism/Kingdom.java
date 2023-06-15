package ca.ultracrepidarianism;

import ca.ultracrepidarianism.commands.CommandManager;
import ca.ultracrepidarianism.listener.ClaimListener;
import ca.ultracrepidarianism.listener.KingdomListener;
import ca.ultracrepidarianism.model.KDChunk;
import ca.ultracrepidarianism.model.KDClaim;
import ca.ultracrepidarianism.model.KDKingdom;
import ca.ultracrepidarianism.model.KDPlayer;
import ca.ultracrepidarianism.model.enums.PermissionLevelEnum;
import ca.ultracrepidarianism.services.Database;
import ca.ultracrepidarianism.utils.PersistenceUtil;
import org.bukkit.plugin.java.JavaPlugin;

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
        PersistenceUtil.doInTransaction(session -> {
            session.persist(player);
            session.persist(town);
            town.addClaim(claim);
            session.persist(claim);
        });
    }

}
