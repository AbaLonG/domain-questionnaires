package org.abalong.utilities;

import org.abalong.model.entities.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * Common sessionFactory util that creates or returns {@code SessionFactory} instance.
 */
public class SessionFactoryUtil {

    private static SessionFactory sessionFactory;

    /**
     * This method returns a sessionFactory if it already exists.<br>
     * In the other case, in makes configuration, fills it by annotated entities and builds new SessionFactory.
     * @return
     */
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(Profile.class);
                configuration.addAnnotatedClass(Field.class);
                configuration.addAnnotatedClass(Option.class);
                configuration.addAnnotatedClass(Response.class);
                configuration.addAnnotatedClass(Answer.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
                builder.applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    private SessionFactoryUtil() {

    }
}
