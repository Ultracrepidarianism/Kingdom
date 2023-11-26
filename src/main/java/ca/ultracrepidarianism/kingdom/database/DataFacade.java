package ca.ultracrepidarianism.kingdom.database;

import ca.ultracrepidarianism.kingdom.database.repositories.ClaimRepository;
import ca.ultracrepidarianism.kingdom.database.repositories.KingdomRepository;
import ca.ultracrepidarianism.kingdom.database.repositories.PlayerRepository;

public class DataFacade {
    private static DataFacade instance;
    private final ClaimRepository claimRepository;
    private final KingdomRepository kingdomRepository;
    private final PlayerRepository playerRepository;

    private DataFacade() {
        DataFacade.instance = this;
        claimRepository = new ClaimRepository();
        kingdomRepository = new KingdomRepository();
        playerRepository = new PlayerRepository();
    }

    public static DataFacade getInstance() {
        if (instance == null) {
            instance = new DataFacade();
        }
        return instance;
    }

    public ClaimRepository getClaimRepository() {
        return claimRepository;
    }

    public KingdomRepository getKingdomRepository() {
        return kingdomRepository;
    }

    public PlayerRepository getPlayerRepository() {
        return playerRepository;
    }
}
