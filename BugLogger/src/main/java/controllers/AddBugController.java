package controllers;

import infrastructure.RepositoryException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Severity;
import model.User;
import service.Services;
import utils.IObservable;
import utils.IObserver;

import java.util.ArrayList;
import java.util.List;


public class AddBugController{
    @FXML
    TextField title;
    @FXML
    TextArea description;

    @FXML
    ChoiceBox<Severity> severity;

    @FXML
    ChoiceBox<User> assignedTo;
    Services service;



    public void setService(Services service) {
        this.service = service;
        for (var i = 1; i <= 4; i++) {
            severity.getItems().add(Severity.valueOf(i));
        }
        for (var i : service.getProgrammers()) {
            assignedTo.getItems().add(i);
        }
    }

    @FXML
    void addClicked() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        try {
            String titleString = title.getText();
            if (titleString.length() == 0) {
                throw new Exception("Please fill in the Title Field");
            }
            String descriptionString = description.getText();
            service.addNewBug(titleString, descriptionString, severity.getValue(), assignedTo.getValue());
            title.clear();
            description.clear();
            severity.getSelectionModel().clearSelection();
            assignedTo.getSelectionModel().clearSelection();
        } catch (Exception e) {
//            e.printStackTrace();
            alert.setHeaderText(e.getMessage());

            alert.setTitle("Error");
            alert.show();
        }
    }


}
