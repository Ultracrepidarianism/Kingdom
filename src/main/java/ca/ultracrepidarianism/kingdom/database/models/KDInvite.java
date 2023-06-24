package ca.ultracrepidarianism.kingdom.database.models;

import org.bukkit.entity.Player;

public class KDInvite {
    private final KDPlayer inviter;
    private final Player invitee;
    private final KDKingdom kingdom;

    public KDInvite(final KDPlayer inviter, final Player invitee, final KDKingdom kingdom) {
        this.inviter = inviter;
        this.invitee = invitee;
        this.kingdom = kingdom;
    }

    public KDPlayer getInviter() {
        return inviter;
    }

    public Player getInvitee() {
        return invitee;
    }

    public KDKingdom getKingdom() {
        return kingdom;
    }
}
