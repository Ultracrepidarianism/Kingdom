package ca.ultracrepidarianism.commands.subcommands;

import ca.ultracrepidarianism.model.KDClaim;
import ca.ultracrepidarianism.model.KDPlayer;
import ca.ultracrepidarianism.model.KDUtil;
import ca.ultracrepidarianism.utils.HibernateUtil;
import org.bukkit.entity.Player;
import ca.ultracrepidarianism.commands.SubCommand;

import java.util.List;

public class UnclaimCommand extends SubCommand {
    @Override
    public String getPermission() {
        return null;
    }

    @Override
    public String getLabel() {
        return null;
    }

    @Override
    public String getUsage() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public void perform(Player ply, String[] args) {
        KDPlayer kdPlayer = database.getPlayer(ply);
        if(kdPlayer == null){
            ply.sendMessage(KDUtil.getMessage("error.kingdom.nokingdom"));
            return;
        }
//        List<KDClaim> claims = HibernateUtil.getEntityManager().createQuery("SELECT * FROM claims WHERE townId = " )
    }
}
