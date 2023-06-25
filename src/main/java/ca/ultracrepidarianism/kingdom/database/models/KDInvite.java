package ca.ultracrepidarianism.kingdom.database.models;

public class KDInvite {
    private final KDPlayer inviter;
    private final KDPlayer invitee;
    private final KDKingdom kingdom;

    public KDInvite(final KDPlayer inviter, final KDPlayer invitee, final KDKingdom kingdom) {
        this.inviter = inviter;
        this.invitee = invitee;
        this.kingdom = kingdom;
    }

    public KDPlayer getInviter() {
        return inviter;
    }

    public KDPlayer getInvitee() {
        return invitee;
    }

    public KDKingdom getKingdom() {
        return kingdom;
    }
}
