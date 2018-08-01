package org.abalong.controller.cdi;

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
 * This class is CDI bean. It contains information that user wants to change in his {@code Profile} entity.
 */
@Named
@RequestScoped
public class EditProfile {

    @Inject
    private ProfileBean profileBean;
    private ProfilesService service = new ProfilesService();

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    public EditProfile() {

    }

    public String getFirstName() {
        return !StringUtils.isEmpty(firstName) ? firstName : profileBean.getFirstName();
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return !StringUtils.isEmpty(lastName) ? lastName : profileBean.getLastName();
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return !StringUtils.isEmpty(email) ? email : profileBean.getEmail();
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return !StringUtils.isEmpty(phoneNumber) ? phoneNumber : profileBean.getPhoneNumber();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * This is main method of {@code EditProfile} class, it tries to get {@code Profile} instance from database,
     * then, attempts to update edited values on the {@code editProfile} page.
     * If there is no {@code Profile} with such email in database and new email is not empty, the profile is updated.
     */
    public void edit() {
        String profileMail = profileBean.getEmail();
        Profile profile = service.getProfile(profileMail);
        if (profile == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/domainname/login.xhtml");
            }
            catch (Exception ignored) {
            }
            return;
        }

        // If user wants to change email, we need to recreate his entity in database
        boolean recreate = false;

        if (email == null || email.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Email can not be empty"));
            return;
        }

        if (!StringUtils.isEmpty(email) && !email.equals(profileMail)) {
            if (service.exists(email)) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Such email already exists"));
                return;
            }
            recreate = true;
            service.delete(profileBean.getEmail());
            profile = new Profile(email, profileBean.getPassword());
            profileBean.setEmail(email);
        }

        if (!StringUtils.isEmpty(firstName)) {
            profileBean.setFirstName(firstName);
            profile.setFirstName(firstName);
        }

        if (!StringUtils.isEmpty(lastName)) {
            profileBean.setLastName(lastName);
            profile.setLastName(lastName);
        }

        if (!StringUtils.isEmpty(phoneNumber)) {
            profileBean.setPhoneNumber(phoneNumber);
            profile.setPhoneNumber(phoneNumber);
        }

        if (recreate)
            service.save(profile);
        else
            service.update(profile);

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/domainname/fields.xhtml");
        }
        catch (Exception ignored) {
        }
    }
}
