package ca.ultracrepidarianism.listener;

import ca.ultracrepidarianism.services.Database;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class KingdomListener implements Listener {
    private final Database database = Database.getInstance();

    public KingdomListener() {}

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        database.createPlayer(p.getUniqueId().toString());
        database.addPlayerToMap(p);
    }

    @EventHandler
    public void onLeave(PlayerJoinEvent e){
        database.removePlayerfromMap(e.getPlayer());
    }
}
