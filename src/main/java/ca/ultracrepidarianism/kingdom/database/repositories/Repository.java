package ca.ultracrepidarianism.kingdom.database.repositories;

import ca.ultracrepidarianism.kingdom.database.dal.DAL;

public abstract class Repository {

    protected final DAL dal;

    protected Repository(final DAL dal) {
        this.dal = dal;
    }
}
