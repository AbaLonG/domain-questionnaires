package org.abalong.controller.services;

import org.abalong.controller.daos.ProfilesDao;
import org.abalong.model.entities.Profile;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Instances of this class are used to operate with {@code Profile} instances in the database throw {@code ProfilesDao}.
 */
public class ProfilesService {

    private ProfilesDao dao = new ProfilesDao();

    /**
     *
     * @param email
     * @return true if there is such {@code Profile} in the database.
     */
    public boolean exists(String email) {
        return dao.profileExists(email);
    }

    /**
     * Checks whether the profile is correct.
     * Correct profile has to have not empty password and unique email
     * @param profile
     * @return
     */
    private boolean isCorrect(Profile profile) {
        return
                profile != null &&
                !StringUtils.isEmpty(profile.getEmail()) &&
                !StringUtils.isEmpty(profile.getPassword());
    }

    /**
     * Saves the {@code Profile} instance in the database.
     * @param profile {@code Profile} instance which has to be saved
     * @return logical value the indicates whether the profile was saved.
     */
    public boolean save(Profile profile) {
        if (!isCorrect(profile)) {
            return false;
        }

        if (exists(profile.getEmail())) {
            return false;
        }

        dao.save(profile);
        return true;
    }

    public boolean update(Profile profile) {
        if (!isCorrect(profile)) {
            return false;
        }

        if (!exists(profile.getEmail())) {
            return false;
        }

        dao.update(profile);
        return true;
    }

    /**
     * Deletes profile in the database if exists
     * @param profile
     * @return logical value of result of delete operation
     */
    public boolean delete(Profile profile) {
        if (!isCorrect(profile)) {
            return false;
        }

        if (!exists(profile.getEmail())) {
            return false;
        }

        dao.delete(profile);
        return true;
    }

    /**
     * Removes profile from the database by email
     * @param email
     * @return result of delete operation
     */
    public boolean delete(String email) {
        if (StringUtils.isEmpty(email))
            return false;

        Profile profile = dao.getProfile(email);
        return delete(profile);
    }

    /**
     * This method checks the email and password of the profile.<br>
     *     If there is a profile with such email in the database, it checks the password.
     * @param email
     * @param password
     * @return true if params are correct.
     */
    public boolean checkPassword(String email, String password) {
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password))
            return false;

        Profile profile = dao.getProfile(email);
        if (profile == null)
            return false;

        return profile.getPassword().equals(password);
    }

    public Profile getProfile(String email) {
        if (StringUtils.isEmpty(email))
            return null;

        return dao.getProfile(email);
    }

    public List<Profile> getAllProfiles() {
        return dao.getAllProfiles();
    }
}
