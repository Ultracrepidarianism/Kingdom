package ca.ultracrepidarianism.utils;

import ca.ultracrepidarianism.model.KDClaim;
import ca.ultracrepidarianism.model.KDPlayer;
import ca.ultracrepidarianism.model.KDTown;
import ca.ultracrepidarianism.services.sqlutil.SqlInfo;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

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
        Configuration configuration = new Configuration();

        // Hibernate settings equivalent to hibernate.cfg.xml's properties
        Properties settings = new Properties();
        settings.put(AvailableSettings.DRIVER, "com.mysql.cj.jdbc.Driver");
        settings.put(AvailableSettings.URL, sqlInfo.getUrl());
        settings.put(AvailableSettings.USER, sqlInfo.getUsername());
        settings.put(AvailableSettings.PASS, sqlInfo.getPassword());
        settings.put(AvailableSettings.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
        settings.put(AvailableSettings.SHOW_SQL, "false");
        settings.put(AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        settings.put(AvailableSettings.HBM2DDL_AUTO, "update");

        configuration.setProperties(settings);
        configuration.addAnnotatedClass(KDClaim.class);
        configuration.addAnnotatedClass(KDPlayer.class);
        configuration.addAnnotatedClass(KDTown.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();

        return configuration.buildSessionFactory(serviceRegistry);
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
}
