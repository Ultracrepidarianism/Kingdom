package ca.ultracrepidarianism.kingdom.utils;

import ca.ultracrepidarianism.kingdom.Kingdom;
import ca.ultracrepidarianism.kingdom.database.connections.ConnectionFactory;
import ca.ultracrepidarianism.kingdom.database.connections.ConnectionInfo;
import ca.ultracrepidarianism.kingdom.database.connections.enums.ConnectionType;
import ca.ultracrepidarianism.kingdom.database.models.KDClaim;
import ca.ultracrepidarianism.kingdom.database.models.KDKingdom;
import ca.ultracrepidarianism.kingdom.database.models.KDPlayer;
import jakarta.persistence.EntityManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

public class HibernateUtil {
    private static final ConnectionInfo sqlInfo = ConnectionFactory.get(ConnectionType.valueOf(JavaPlugin.getPlugin(Kingdom.class).getConfig().getString("DBserver.type").toUpperCase()));
    private static SessionFactory sessionFactory;
    private static EntityManager entityManager;

    private HibernateUtil() {
        // This is a utility class, so it should not be instantiated.
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
        if (entityManager == null || !entityManager.isOpen()) {
            entityManager = getSessionFactory().createEntityManager();
        }
        return entityManager;
    }

    /**
     * This method is used to build and configure a Hibernate SessionFactory.
     * A SessionFactory in Hibernate is used to create Sessions, which provide an interface between the application and the database.
     *
     * @return SessionFactory This returns a SessionFactory object that is fully configured to interact with the defined database.
     */
    private static SessionFactory buildSessionFactory() {
        Thread.currentThread().setContextClassLoader(Kingdom.class.getClassLoader());
        final Configuration configuration = new Configuration();

        final Properties settings = getProperties();

        configuration.addProperties(settings);
        configuration.addAnnotatedClass(KDClaim.class);
        configuration.addAnnotatedClass(KDPlayer.class);
        configuration.addAnnotatedClass(KDKingdom.class);

        return configuration.buildSessionFactory();
    }

    private static Properties getProperties() {
        final Properties settings = new Properties();
        settings.put(AvailableSettings.DRIVER, "com.mysql.cj.jdbc.Driver");
        settings.put(AvailableSettings.URL, sqlInfo.getUrl());
        settings.put(AvailableSettings.USER, sqlInfo.getUsername());
        settings.put(AvailableSettings.PASS, sqlInfo.getPassword());
        settings.put(AvailableSettings.DIALECT, "org.hibernate.dialect.MySQLDialect");
        settings.put(AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        settings.put(AvailableSettings.HBM2DDL_AUTO, "update");
        settings.put(AvailableSettings.AUTOCOMMIT, "false");
        return settings;
    }

}
