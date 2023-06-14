package ca.ultracrepidarianism.utils;

import ca.ultracrepidarianism.Kingdom;
import ca.ultracrepidarianism.model.KDClaim;
import ca.ultracrepidarianism.model.KDPlayer;
import ca.ultracrepidarianism.model.KDTown;
import ca.ultracrepidarianism.services.sqlutil.SqlInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.FlushModeType;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;

import java.util.Properties;
import java.util.function.Consumer;

public class HibernateUtil {
    private static SessionFactory sessionFactory;
    private static EntityManager entityManager;

    private static final SqlInfo sqlInfo = new SqlInfo();

    private HibernateUtil() {
        // This is a utility class, so it should not be instantiated.
    }

    /**
     * This method is used to build and configure a Hibernate SessionFactory.
     * A SessionFactory in Hibernate is used to create Sessions, which provide an interface between the application and the database.
     *
     * @return SessionFactory This returns a SessionFactory object that is fully configured to interact with the defined database.
     */
    public static SessionFactory buildSessionFactory() {
        Thread.currentThread().setContextClassLoader(Kingdom.class.getClassLoader());
        Configuration configuration = new Configuration();

        Properties settings = new Properties();
        settings.put(AvailableSettings.DRIVER, "com.mysql.cj.jdbc.Driver");
        settings.put(AvailableSettings.URL, sqlInfo.getUrl());
        settings.put(AvailableSettings.USER, sqlInfo.getUsername());
        settings.put(AvailableSettings.PASS, sqlInfo.getPassword());
        settings.put(AvailableSettings.DIALECT, "org.hibernate.dialect.MySQLDialect");
        settings.put(AvailableSettings.SHOW_SQL, "false");
        settings.put(AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        settings.put(AvailableSettings.HBM2DDL_AUTO, "update");
        settings.put(AvailableSettings.AUTOCOMMIT, "true");

        configuration.addProperties(settings);
        configuration.addAnnotatedClass(KDClaim.class);
        configuration.addAnnotatedClass(KDPlayer.class);
        configuration.addAnnotatedClass(KDTown.class);

        return configuration.buildSessionFactory();
    }

    /**
     * This method is used to get the SessionFactory object. If the SessionFactory object has not been instantiated yet, it will be instantiated.
     *
     * @return SessionFactory This returns a SessionFactory object that is fully configured to interact with the defined database.
     */
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = buildSessionFactory();
        }
        return sessionFactory;
    }

    public static EntityManager getEntityManager() {
        if (entityManager == null) {
            entityManager = getSessionFactory().createEntityManager();
            entityManager.setFlushMode(FlushModeType.COMMIT);
        }
        return entityManager;
    }

    public static void doInTransaction(Consumer<Session> action) {
        Transaction tx = null;
        try (Session session = getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            action.accept(session);

            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public static <T> T getSingleResultOrNull(TypedQuery<T> query) {
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
