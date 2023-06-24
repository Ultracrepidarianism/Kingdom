package ca.ultracrepidarianism.kingdom.database.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class KDKingdom {
    private Long id;
    private String name;
    private KDPlayer owner;

    public KDKingdom(final String name, final KDPlayer owner) {
        this.name = name;
        this.owner = owner;
    }

    public KDKingdom(final Long id, final String name, final KDPlayer owner) {
        this(name, owner);
        this.id = id;
    }

    public long getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public KDPlayer getOwner() {
        return owner;
    }
}
