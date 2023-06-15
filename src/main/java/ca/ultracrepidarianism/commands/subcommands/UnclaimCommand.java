package ca.ultracrepidarianism.commands.subcommands;

import ca.ultracrepidarianism.model.KDPlayer;
import ca.ultracrepidarianism.utils.KDUtil;
import org.bukkit.entity.Player;
import ca.ultracrepidarianism.commands.SubCommand;

public class UnclaimCommand extends SubCommand {
    @Override
    public String getPermission() {
        return null;
    }

    @Override
    public String getLabel() {
        return "unclaim";
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
