package ca.ultracrepidarianism;

import ca.ultracrepidarianism.model.KDPlayer;
import ca.ultracrepidarianism.utils.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ca.ultracrepidarianism.commands.CommandManager;
import ca.ultracrepidarianism.listener.ClaimListener;
import ca.ultracrepidarianism.listener.KingdomListener;
import ca.ultracrepidarianism.services.Database;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class Kingdom extends JavaPlugin {

    @Override
    public void onEnable() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        saveDefaultConfig();
        createPlayer();
        Database.getInstance(); // Initialize DB to make sure its properly setup
        getServer().getPluginManager().registerEvents(new KingdomListener(),this);
        getServer().getPluginManager().registerEvents(new ClaimListener(),this);
        getCommand("kingdom").setExecutor(new CommandManager(this));
    }

    @Transactional
    public void createPlayer() {
        HibernateUtil.doInTransaction(session -> {
            KDPlayer player = new KDPlayer("671bd100-93fb-417b-9767-2b4ee07605c5",null,null);
            session.persist(player);
        });
    }
}
