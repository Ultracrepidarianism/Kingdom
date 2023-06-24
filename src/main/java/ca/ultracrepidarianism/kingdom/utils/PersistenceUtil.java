package ca.ultracrepidarianism.kingdom.utils;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class PersistenceUtil {
    public static <T> T getSingleResultOrNull(TypedQuery<T> query) {
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
