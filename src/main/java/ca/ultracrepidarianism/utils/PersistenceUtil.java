package ca.ultracrepidarianism.utils;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.function.Consumer;

public class PersistenceUtil {
    public static void doInTransaction(Consumer<Session> action) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        final Transaction tx = session.beginTransaction();
        action.accept(session);
        tx.commit();
    }

    public static <T> T getSingleResultOrNull(TypedQuery<T> query) {
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
