package org.abalong.controller.daos;

import org.abalong.model.entities.Profile;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Instances of this class allow all CRUD operations whith {@code Profile} entities.
 */
public class ProfilesDao extends AbstractDao {

    /**
     * Saves profile instance in the database
     * @param profile
     */
    public void save(Profile profile) {
        Session session = checkSession();
        Transaction transaction = session.beginTransaction();
        session.save(profile);
        transaction.commit();
        session.close();
    }

    /**
     * Updates profile instance in the database
     * @param profile
     */
    public void update(Profile profile) {
        Session session = checkSession();
        Transaction transaction = session.beginTransaction();
        session.update(profile);
        transaction.commit();
        session.close();
    }

    /**
     * Removes profile instance from the database
     * @param profile
     */
    public void delete(Profile profile) {
        Session session = checkSession();
        Transaction transaction = session.beginTransaction();
        session.delete(profile);
        transaction.commit();
        session.close();
    }

    /**
     *
     * @param email of the Profile
     * @return true if there is a profile with such email in the database.
     */
    public boolean profileExists(String email) {
        return getProfile(email) != null;
    }

    public boolean checkPassword(String email, String password) {
        Profile profile = null;

        if ((profile = getProfile(email)) == null) {
            return false;
        }

        return profile.getPassword().equals(password);
    }

    public Profile getProfile(String email) {
        Session session = checkSession();
        Profile profile = session.get(Profile.class, email);
        session.close();
        return profile;
    }

    /**
     *
     * @return all the profiles entities which database contains.
     */
    public List<Profile> getAllProfiles() {
        Session session = checkSession();
        List<Profile> profiles = session.createQuery("from Profile ").getResultList();
        session.close();
        return profiles;
    }
}
