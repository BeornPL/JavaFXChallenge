package com.beorn.javafxchallenge;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

import java.io.IOException;
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
        contactsTableView.setRowFactory(new Callback<TableView<Contact>, TableRow<Contact>>() {
            @Override
            public TableRow<Contact> call(TableView<Contact> param) {
                final TableRow<Contact> row = new TableRow<>();
                row.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        final int index = row.getIndex();
                        if (index >= 0 && index < contactsTableView.getItems().size() && contactsTableView.getSelectionModel().isSelected(index)) {
                            contactsTableView.getSelectionModel().clearSelection();
                            event.consume();
                        }
                    }
                });
                contactsTableView.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        final int index = row.getIndex();
                        if(event.getCode().equals(KeyCode.ESCAPE)) {
                            if (index >= 0 && index < contactsTableView.getItems().size() && contactsTableView.getSelectionModel().isSelected(index)) {
                                contactsTableView.getSelectionModel().clearSelection();
                                event.consume();
                            }
                        }
                    }
                });
                return row;
            }
        });

    }

    @FXML
    public void deselecting() {
        System.out.println("test");
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
        Contact selectedItem = contactsTableView.getSelectionModel().getSelectedItem();
        if(selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Contact Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select the contact you want to edit.");
            alert.showAndWait();
            return;
        }

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

//        int index = contactsTableView.getSelectionModel().getSelectedIndex();

        DialogController controller = fxmlLoader.getController();
        controller.editContact(selectedItem);

//        controller.setFirstNameField(contactsTableView.getItems().get(index).getFirstName());
//        controller.setLastNameField(contactsTableView.getItems().get(index).getLastName());
//        controller.setPhoneNumberField(contactsTableView.getItems().get(index).getPhoneNumber());
//        controller.setNotesField(contactsTableView.getItems().get(index).getNotes());

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get()==ButtonType.OK) {

//            Contact newItem = controller.processResults();
//            data.editContact(index, newItem);
            controller.updateContact(selectedItem);
            data.saveContacts();
            contactsTableView.getSelectionModel().select(selectedItem);
        }
    }

    @FXML
    public void handleDelete() {
        Contact contact = contactsTableView.getSelectionModel().getSelectedItem();
        if(contact == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Contact Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select the contact you want to delete.");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Contact");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure to delete?");
        Optional<ButtonType> results = alert.showAndWait();

        if(results.isPresent() && (results.get() == ButtonType.OK)) {
            data.deleteContact(contact);
            data.saveContacts();
        }
    }

//    @FXML
//    private void handleDeselect(KeyEvent keyEvent) {
//        Contact selectedContact = contactsTableView.getSelectionModel().getSelectedItem();
//        if(selectedContact == null) {
//            return;
//        }
//        if(keyEvent.getCode().equals(KeyCode.ESCAPE)) {
//            contactsTableView.getSelectionModel().clearSelection();
//        }
//    }

    @FXML
    private void handleExit(){
        Platform.exit();
    }
}
