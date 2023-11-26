package ca.ultracrepidarianism.kingdom.database.repositories;

import ca.ultracrepidarianism.kingdom.utils.HibernateUtil;
import jakarta.persistence.EntityManager;

public abstract class Repository {
    protected EntityManager getEntityManager() {
        return HibernateUtil.getEntityManager();
    }

}
