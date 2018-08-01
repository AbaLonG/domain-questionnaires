package org.abalong.controller.daos;

import org.abalong.utilities.SessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * This class is extended by other DAOs. <br>
 * It has an instance of {@code SessionFactory} class. <br>
 * It has the only method that returns/creates hibernate {@code Session}
 * This class is not abstract because {@code sessionFactory} instance has to be created
 * by protected constructor of this class in classes which extends this class. Don't make this class abstract.
 */
class AbstractDao {

    SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();

    /**
     * Returns a session factory if already exists. Otherwise opens new session factory.
     * @return
     */
    Session checkSession() {
        try {
            return sessionFactory.getCurrentSession();
        }
        catch (Exception e) {
            return sessionFactory.openSession();
        }
    }

    protected AbstractDao() {

    }
}
