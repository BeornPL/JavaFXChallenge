package com.beorn.javafxchallenge;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;

/**
 * Created by Beorn on 2018-04-14.
 */
public class DialogController {
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField notesField;

    @FXML
    public Contact processResults() {
        Contact newContact = new Contact();

        String firstName = firstNameField.getText().trim();
        newContact.setFirstName(firstName);
        String lastName = lastNameField.getText().trim();
        newContact.setFirstName(lastName);
        String phoneNumber = phoneNumberField.getText().trim();
        newContact.setFirstName(phoneNumber);
        String notes = notesField.getText().trim();
        newContact.setFirstName(notes);

        ContactData.getInstance().addContact(newContact);
        return newContact;
    }
}
