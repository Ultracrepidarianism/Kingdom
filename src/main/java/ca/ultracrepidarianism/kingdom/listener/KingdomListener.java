package ca.ultracrepidarianism.kingdom.listener;

import ca.ultracrepidarianism.kingdom.database.DataFacade;
import ca.ultracrepidarianism.kingdom.model.KDPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class KingdomListener implements Listener {

    public KingdomListener() {
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        KDPlayer kdp = DataFacade.getInstance().Players().getPlayer(e.getPlayer(), false);
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
