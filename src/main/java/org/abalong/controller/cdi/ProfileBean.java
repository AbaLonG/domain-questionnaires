package org.abalong.controller.cdi;

import org.abalong.controller.services.ProfilesService;
import org.abalong.model.entities.Profile;
import org.apache.commons.lang3.StringUtils;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;

/**
 * This is CDI bean that contains information about profile.<br>
 * It represents {@code Profile} entity from the database.
 *  This bean is {@code SessionScoped} because the application has to store profile information
 * during all his work with the application.<br>
 *  This bean also contains {@code ProfileService} instance to be able to
 *  save, update, delete or get {@code Profile} entities from the database.
 */

@Named
@SessionScoped
public class ProfileBean implements Serializable {

    private String email;
    private String password;
    private String confirmPassword;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    private boolean isLogged;
    private boolean remember;

    private ProfilesService service = new ProfilesService();

    public ProfileBean() {

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

    public boolean isLogged() {
        return isLogged;
    }

    public boolean isRemember() {
        return remember;
    }

    public void setRemember(boolean remember) {
        this.remember = remember;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setLogged(boolean logged) {
        isLogged = logged;
    }

    /**
     * After submittin Log In Button in the login.xhtml, this method is being invoked.
     * It checks profiles {@code email} and {@code password} via {@code ProfileService} instance.<br>
     * If there is such profile in the database, {@code ProfileBean} is being filled with the {@code Profile}'s
     * entity fields and being redirected to editProfile page.
     */
    public void doLogin() {
        isLogged = service.checkPassword(email, password);
        if (isLogged) {
            Profile profile = service.getProfile(email);
            // Settings up fields of ProfileBean
            firstName = profile.getFirstName();
            lastName = profile.getLastName();
            phoneNumber = profile.getPhoneNumber();
            redirect();
        }
        else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Wrong username or password."));
        }
    }

    /**
     * This method is being invoked every time user visits login.xhtml page.
     * If the user has been already logged, he would redirected to editProfile page.
     */
    public void redirect() {
        if (isLogged) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/domainname/editProfile.xhtml");
            }
            catch (Exception ignored) {

            }
        }
    }

    /**
     * This method logs out user by setting his {@code isLogged} field to false.
     * User can invoke this method by clicking Log Out link in profile settings.
     */
    public void logOut() {
        isLogged = false;
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/domainname/login.xhtml");
        }
        catch (Exception ignored) {
        }
    }

    /**
     * This method tries to return full name of the profile.<br>
     * If the {@code ProfileBean} has filled {@code firstName} and {@code lastName},
     * method returns concatenation of these values.<br>
     * If there is only {@code firstName} filled, method returns only it.<br>
     * If there are neither {@code firstName} nor {@code lastName} filled in the {@code ProfileBean},
     * method returns {@code email} of the bean.
     * @return Full name or part name, or email.
     */
    public String getFullName() {
        String fullName;
        if (firstName == null)
            fullName = email;
        else {
            fullName = firstName;
            if (lastName != null)
                fullName += " " + lastName;
        }
        return fullName;
    }
}
