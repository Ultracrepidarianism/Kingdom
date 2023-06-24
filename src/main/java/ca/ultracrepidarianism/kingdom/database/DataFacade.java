package ca.ultracrepidarianism.kingdom.database;

import ca.ultracrepidarianism.kingdom.database.connections.ConnectionInfo;
import ca.ultracrepidarianism.kingdom.database.repositories.ClaimRepository;
import ca.ultracrepidarianism.kingdom.database.repositories.KingdomRepository;
import ca.ultracrepidarianism.kingdom.database.repositories.PlayerRepository;
import ca.ultracrepidarianism.kingdom.utils.HibernateUtil;

public class DataFacade {


    public static DataFacade getInstance() {
        if(instance == null){
            instance = new DataFacade();
        }
        return instance;
    }

    private static DataFacade instance;


    public ClaimRepository claims() {
        return claimRepository;
    }

    private final ClaimRepository claimRepository;

    public KingdomRepository kingdoms() {
        return kingdomRepository;
    }

    private final KingdomRepository kingdomRepository;

    public PlayerRepository players() {
        return playerRepository;
    }

    private final PlayerRepository playerRepository;

    private DataFacade() {
        HibernateUtil.buildSessionFactory();
        DataFacade.instance = this;
        claimRepository = new ClaimRepository();
        kingdomRepository = new KingdomRepository();
        playerRepository = new PlayerRepository();

    }

}
