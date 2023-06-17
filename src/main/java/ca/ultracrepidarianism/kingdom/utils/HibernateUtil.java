package ca.ultracrepidarianism.kingdom.utils;

import ca.ultracrepidarianism.kingdom.Kingdom;
import ca.ultracrepidarianism.kingdom.model.KDClaim;
import ca.ultracrepidarianism.kingdom.model.KDKingdom;
import ca.ultracrepidarianism.kingdom.model.KDPlayer;
import ca.ultracrepidarianism.kingdom.services.sqlutil.SqlInfo;
import jakarta.persistence.EntityManager;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

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
        settings.put(AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        settings.put(AvailableSettings.HBM2DDL_AUTO, "update");
        settings.put(AvailableSettings.AUTOCOMMIT, "true");

        configuration.addProperties(settings);
        configuration.addAnnotatedClass(KDClaim.class);
        configuration.addAnnotatedClass(KDPlayer.class);
        configuration.addAnnotatedClass(KDKingdom.class);

        return configuration.buildSessionFactory();
    }

    /**
     * This method is used to get the SessionFactory object. If the SessionFactory object has not been instantiated yet, it will be instantiated.
     *
     * @return SessionFactory This returns a SessionFactory object that is fully configured to interact with the defined database.
     */
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null || sessionFactory.isClosed()) {
            sessionFactory = buildSessionFactory();
        }
        return sessionFactory;
    }

    public static EntityManager getEntityManager() {
        if (entityManager == null) {
            entityManager = getSessionFactory().createEntityManager();
        }
        return entityManager;
    }

}
