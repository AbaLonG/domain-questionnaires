package org.abalong.model.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class represents profile entity in the database.
 * You are able to register your own profile in the application via creating instance of this type on domainname.com/signUp page.
 * The email of a Profile instance is the unique value and can not be repeated in other instances of this class.
 * You has to create a password as well.
 * Other fields are not nesessary, but you may define or change them on /editProfile page.
 * You can also chage password and even email, worry not to change the email to value that database already contains.
 */

@Entity
@Table(name = "profiles")
public class Profile {

    // Private fields
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    public Profile() {

    }

    public Profile(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters and Setters

    /**
     * The field that represents an email of the profile. It is unique.
     * It is ID of the profile.
     * @return
     */
    @Id
    @Column(name = "email", unique = true, nullable = false)
    public String getEmail() {
        return email;
    }

    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }

    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    @Column(name = "phone_number")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return String.format(
                "%s %s%nEmail: %s%nNumber: %s%n",
                firstName, lastName, email, phoneNumber
        );
    }

    // Prifles are equal if their email's also equal
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return Objects.equals(email, profile.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
