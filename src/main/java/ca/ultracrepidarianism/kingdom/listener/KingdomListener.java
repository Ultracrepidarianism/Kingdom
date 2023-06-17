package ca.ultracrepidarianism.kingdom.listener;

import ca.ultracrepidarianism.kingdom.model.KDPlayer;
import ca.ultracrepidarianism.kingdom.services.Database;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class KingdomListener implements Listener {
    private final Database database = Database.getInstance();

    public KingdomListener() {}

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        KDPlayer kdp = database.getPlayer(e.getPlayer());
        if(kdp != null)
            database.addPlayerToMap(kdp);
    }

    @EventHandler
    public void onLeave(PlayerJoinEvent e){
        database.removePlayerfromMap(e.getPlayer());
    }
}
