package com.beorn.javafxchallenge;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class Controller {

    private ContactData data;

    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private TableView<Contact> contactsTableView;

    public void initialize() {
        data = new ContactData();
        data.loadContacts();

        contactsTableView.setItems(data.getContacts());
        contactsTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        contactsTableView.getSelectionModel().selectFirst();
//
//        contactsTableView.getColumns().get(0).setCellFactory(new Callback<TableColumn<Contact, ?>, TableCell<Contact, ?>>() {
//            @Override
//            public TableCell<Contact, ?> call(TableColumn<Contact, ?> param) {
//                TableCell<Contact, ?> cell = new TableCell<>();
//                cell.updateTableView(contactsTableView);
//
//            }
//            return cell;
//        });
    }

    @FXML
    public void showNewItemDialogue() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Add New Contact");
        dialog.setHeaderText("Use this dialog to add a new contact");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("contactDialog.fxml"));
        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());

        } catch(IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get()==ButtonType.OK) {
            DialogController controller = fxmlLoader.getController();
            Contact newItem = controller.processResults();
            data.addContact(newItem);
            data.saveContacts();
            contactsTableView.getSelectionModel().select(newItem);
        }
    }

    @FXML
    public void showEditDialogue() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Edit Contact");
        dialog.setHeaderText("Use this dialog to add a edit contact");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("contactDialog.fxml"));
        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());

        } catch(IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        int index = contactsTableView.getSelectionModel().getSelectedIndex();

        DialogController controller = fxmlLoader.getController();

        controller.setFirstNameField(contactsTableView.getItems().get(index).getFirstName());
        controller.setLastNameField(contactsTableView.getItems().get(index).getLastName());
        controller.setPhoneNumberField(contactsTableView.getItems().get(index).getPhoneNumber());
        controller.setNotesField(contactsTableView.getItems().get(index).getNotes());

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get()==ButtonType.OK) {

            Contact newItem = controller.processResults();
            data.editContact(index, newItem);
            data.saveContacts();
            contactsTableView.getSelectionModel().select(newItem);
        }
    }

    @FXML
    public void handleDelete() {
        Contact contact = contactsTableView.getSelectionModel().getSelectedItem();
        data.deleteContact(contact);
        data.saveContacts();
    }

    @FXML
    private void handleExit(){
        Platform.exit();
    }
}
