package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.*;
import service.Services;

public class EditBugController {
    @FXML
    TextField titleField;
    @FXML
    ChoiceBox<Status> statusChoiceBox;
    @FXML
    TextArea descriptionField;
    @FXML
    Button deleteBtn;
    Services service;
    Integer id;
    User user;

    public void setService(Services service, Integer id, User user) {
        this.service = service;
        this.id = id;
        this.user = user;
        Bug bug = service.findBug(id);
        titleField.setText(bug.getName());
        descriptionField.setText(bug.getDescription());
        statusChoiceBox.setValue(bug.getStatus());
        for (var i = 1; i <= 4; i++) {
            statusChoiceBox.getItems().add(Status.valueOf(i));
        }
        if (user.getRole() == RoleType.PROGRAMMER) {
            titleField.setDisable(true);
            descriptionField.setDisable(true);
            deleteBtn.setDisable(true);
        } else {
            titleField.setDisable(false);
            descriptionField.setDisable(false);
            deleteBtn.setDisable(false);
        }
    }

    public void editClicked() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        try {
            String titleString = titleField.getText();
            if (titleString.length() == 0) {
                throw new Exception("Please fill in the Title Field");
            }
            String descriptionString = descriptionField.getText();
            service.editBug(id, titleString, descriptionString, statusChoiceBox.getValue());
        } catch (Exception e) {
            e.printStackTrace();
            alert.setHeaderText(e.getMessage());

            alert.setTitle("Error");
            alert.show();
        }
    }

    public void deleteClicked() {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        try {
            service.deleteBug(id);
            titleField.clear();
            descriptionField.clear();
            statusChoiceBox.getSelectionModel().clearSelection();
        } catch (Exception ex) {
            ex.printStackTrace();
            alert.setHeaderText(ex.getMessage());

            alert.setTitle("Error");
            alert.show();
        }
    }
}
