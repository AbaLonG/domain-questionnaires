package org.abalong.controller.cdi;

import org.abalong.controller.email.Sender;
import org.abalong.controller.services.ProfilesService;
import org.abalong.model.entities.Profile;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * This class is CDI bean. It is being used on changePassword page.
 * It contains old and new password. It tries to change password of {@code Profile} entity.
 */

@Named
@RequestScoped
public class ChangeBean {

    private String currentPassword;
    private String newPassword;
    private String confirmPassword;

    @Inject
    private ProfileBean profileBean;
    private ProfilesService service = new ProfilesService();

    public ChangeBean() {

    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    /**
     * This method tries to change password of {@code Profile} entity.
     * First, it checks whether the old password is equal to the password in the database.
     * Then, it checks whether new password and confirm password are equal.
     * After that, it tries to get {@code Profile} and {@code ProfileBean} instances to change their passwords.
     * New password will be assigned to the database.
     */
    public void changePassword() {
        String actualPassword = profileBean.getPassword();
        if (!actualPassword.equals(currentPassword)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Current password is wrong"));
            return;
        }


        if (!newPassword.equals(confirmPassword)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Passwords are not equal."));
            return;
        }

        String profileMail = profileBean.getEmail();
        Profile profile = service.getProfile(profileMail);
        if (profile == null) {
            profileBean.doLogin();
            return;
        }

        profile.setPassword(newPassword);
        service.update(profile);
        new Sender("noreply.questionnaires@gmail.com", "questions1")
                .send("Notification", "You've changed password on questionnaires.com", profileBean.getEmail());
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Password has been changed"));
    }
}
