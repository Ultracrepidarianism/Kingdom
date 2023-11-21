package ca.ultracrepidarianism.kingdom.listener;

import ca.ultracrepidarianism.kingdom.database.DataFacade;
import ca.ultracrepidarianism.kingdom.database.models.KDPlayer;
import ca.ultracrepidarianism.kingdom.utils.KDMessageUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Map;

public class KingdomListener implements Listener {

    public KingdomListener() {
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        KDPlayer kdPlayer = DataFacade.getInstance().getPlayerRepository().getPlayerFromBukkitPlayer(e.getPlayer());
        if (kdPlayer == null) {
            kdPlayer = DataFacade.getInstance().getPlayerRepository().createPlayer(e.getPlayer());
        }
        DataFacade.getInstance().getPlayerRepository().updatePlayerName(kdPlayer, e.getPlayer().getName());
    }

    @EventHandler
    public void onLeave(PlayerJoinEvent e) {
        // TODO During caching implementation
        // database.removePlayerfromMap(e.getPlayer());
    }
}
