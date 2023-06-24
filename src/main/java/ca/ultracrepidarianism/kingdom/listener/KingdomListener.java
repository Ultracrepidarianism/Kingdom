package ca.ultracrepidarianism.kingdom.listener;

import ca.ultracrepidarianism.kingdom.database.DataFacade;
import ca.ultracrepidarianism.kingdom.database.models.KDPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class KingdomListener implements Listener {

    public KingdomListener() {
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        final KDPlayer kdp = DataFacade.getInstance().players().getPlayer(e.getPlayer());
        if (kdp != null) {
            // TODO
//            database.addPlayerToMap(kdp);
        }
    }

    @EventHandler
    public void onLeave(PlayerJoinEvent e) {
        // TODO
//        database.removePlayerfromMap(e.getPlayer());
    }
}
