package org.abalong.controller.cdi;

import org.abalong.controller.email.Sender;
import org.abalong.controller.services.ProfilesService;
import org.abalong.model.entities.Profile;
import org.apache.commons.lang3.StringUtils;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * This class is CDI bean, using it you can register your {@code Profile} on signUp page.<br>
 * It contains all fields that {@code Profile} entity do, too. This bean users {@code ProfileBean}
 * instance to do CRUD operations with {@code Profile}.
 * It also has {@code Inject}ed {@code ProfileBean} instance to fill its field if registration was successful.
 * This bean is {@code RequestScoped} because it's being used only during registration process.
 */

@Named
@RequestScoped
public class RegisterBean {

    private String email;
    private String password;
    private String confirmPassword;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private boolean created;

    @Inject
    private ProfileBean profileBean;
    private ProfilesService service = new ProfilesService();

    public RegisterBean() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isCreated() {
        return created;
    }

    public void setCreated(boolean created) {
        this.created = created;
    }

    public ProfileBean getProfileBean() {
        return profileBean;
    }

    public void setProfileBean(ProfileBean profileBean) {
        this.profileBean = profileBean;
    }

    /**
     * This method tries to save new {@code Profile} instance and fill {@code ProfileBean}.<br>
     * First of all, it checks whether the {@code email}, {@code password}, {@code confirmPassword} are not empty.
     * Then, it checks whether {@code password} and {@code confirmPassword} are equal.
     * Finally, it checks whether there is such {@code Profile} entity in the database.
     * In success case, it creates new {@code Profile} entity and saves it.
     */
    public void tryRegister() {
        if (StringUtils.isEmpty(email)
                || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(confirmPassword)) {
            created = false;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Wrong username or password."));
            return;
        }

        if (!password.equals(confirmPassword)) {
            created = false;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Passwords are not equal."));
            return;
        }

        if (service.exists(email)) {
            created = false;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Such user already exists."));
            return;
        }

        Profile profile = new Profile(email, password);
        if (firstName != null)
            profile.setFirstName(firstName);

        if (lastName != null)
            profile.setLastName(lastName);

        if (phoneNumber != null) {
            profile.setPhoneNumber(phoneNumber);
        }

        created = service.save(profile);
        if (created) {
            profileBean.setEmail(email);
            profileBean.setPassword(password);
            profileBean.setFirstName(firstName);
            profileBean.setLastName(lastName);
            profileBean.setPhoneNumber(phoneNumber);

            // Send email notification
            new Sender("noreply.questionnaires@gmail.com", "questions1")
                    .send("Registration notification", "You've bean registered on questionnaires", email);

            profileBean.doLogin();

        }
    }
}
