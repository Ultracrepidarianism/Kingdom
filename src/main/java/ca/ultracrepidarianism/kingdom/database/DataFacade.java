package ca.ultracrepidarianism.kingdom.database;

import ca.ultracrepidarianism.kingdom.database.connections.ConnectionInfo;
import ca.ultracrepidarianism.kingdom.database.dal.DALFactory;
import ca.ultracrepidarianism.kingdom.database.repositories.ClaimRepository;
import ca.ultracrepidarianism.kingdom.database.repositories.KingdomRepository;
import ca.ultracrepidarianism.kingdom.database.repositories.PlayerRepository;

public class DataFacade {


    public static DataFacade getInstance() {
        return DataFacade.instance;
    }

    private static DataFacade instance;

    private final DALFactory dalFactory;

    public ClaimRepository Claims() {
        return claimRepository;
    }

    private final ClaimRepository claimRepository;

    public KingdomRepository Kingdoms() {
        return kingdomRepository;
    }

    private final KingdomRepository kingdomRepository;

    public PlayerRepository Players() {
        return playerRepository;
    }

    private final PlayerRepository playerRepository;

    public DataFacade(ConnectionInfo config) {
        DataFacade.instance = this;
        dalFactory = new DALFactory(config);
        claimRepository = new ClaimRepository(dalFactory.getDal());
        kingdomRepository = new KingdomRepository(dalFactory.getDal());
        playerRepository = new PlayerRepository(dalFactory.getDal());

    }

}
