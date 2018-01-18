package il.ac.jct.michaelzalman.takeandgoclient.model.entities;

import android.content.ContentValues;
import android.provider.ContactsContract;

/**
 * Client class
 */

public class Client
{
    private String firstName;
    private String lastName;
    private String id;
    private String phoneNumber;
    private String email;
    private String creditCard;
    private String password;

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getId() {
        return id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //convertors
}
