package com.beorn.javafxchallenge;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Beorn on 2018-04-12.
 */
public class Contact {
    private StringProperty firstName;
    private StringProperty lastName;
    private StringProperty phoneNumber;
    private StringProperty notes;

    public Contact() {

    }

    public Contact(String firstName, String lastName, String phoneNumber, String notes) {
        this.firstName.set(firstName);
        this.lastName.set(lastName);
        this.phoneNumber.set(phoneNumber);
        this.notes.set(notes);
    }

    public void setFirstName(String firstName) {
        firstNameProperty().set(firstName);
    }

    public void setLastName(String lastName) {
        lastNameProperty().set(lastName);
    }

    public void setPhoneNumber(String phoneNumber) {
        phoneNumberProperty().set(phoneNumber);
    }

    public void setNotes(String notes) {
        notesProperty().set(notes);
    }

    public String getFirstName() {
        return firstNameProperty().get();
    }

    public String getLastName() {
        return lastNameProperty().get();
    }

    public String getPhoneNumber() {
        return phoneNumberProperty().get();
    }

    public String getNotes() {
        return notesProperty().get();
    }

    public StringProperty firstNameProperty() {
        if(firstName == null) {
            firstName = new SimpleStringProperty(this, "firstName");
        }
        return firstName;
    }

    public StringProperty lastNameProperty() {
        if(lastName == null) {
            lastName = new SimpleStringProperty(this, "lastName");
        }
        return lastName;
    }

    public StringProperty phoneNumberProperty() {
        if(phoneNumber == null) {
            phoneNumber = new SimpleStringProperty(this, "phoneNumber");
        }
        return phoneNumber;
    }

    public StringProperty notesProperty() {
        if(notes == null) {
            notes = new SimpleStringProperty(this, "notes");
        }
        return notes;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "firstName=" + firstName +
                ", lastName=" + lastName +
                ", phoneNumber=" + phoneNumber +
                ", notes=" + notes +
                '}';
    }
}
