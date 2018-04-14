package com.beorn.javafxchallenge;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class Controller {

    private ObservableList<Contact> contacts;

    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private TableView<Contact> contactsTableView;
    @FXML
    private TableColumn<Contact,String> firstNameColumn;
    @FXML
    private TableColumn<Contact,String> lastNameColumn;
    @FXML
    private TableColumn<Contact,String> phoneNumberColumn;
    @FXML
    private TableColumn<Contact,String> notesColumn;

    public void initialize() {
//        contactsTableView.setItems(contacts);
//        firstNameColumn.setCellFactory(new PropertyValueFactory("firstName"));
//        lastNameColumn.setCellFactory(new PropertyValueFactory("lastName"));
//        phoneNumberColumn.setCellFactory(new PropertyValueFactory("phoneNumber"));
//        notesColumn.setCellFactory(new PropertyValueFactory("notes"));
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
            contactsTableView.getSelectionModel().select(newItem);
        }
    }
}
