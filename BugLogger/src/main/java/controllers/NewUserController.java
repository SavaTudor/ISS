package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import model.RoleType;
import model.Status;
import service.Services;

public class NewUserController {
    @FXML
    public TextField usernameField;

    @FXML
    public TextField passwordField;

    @FXML
    public TextField nameField;

    @FXML
    public ChoiceBox<RoleType> roleTypeChoiceBox;
    Services services;

    public void setService(Services services) {
        this.services = services;
        for (var i = 1; i <= 3; i++) {
            roleTypeChoiceBox.getItems().add(RoleType.valueOf(i));
        }
    }

    public void addClicked(){
        Alert alert = new Alert(Alert.AlertType.ERROR);

        String username = usernameField.getText();
        String name = nameField.getText();
        String password = passwordField.getText();
        RoleType role = roleTypeChoiceBox.getValue();
        try {
            services.addUser(username, name, password, role);
            usernameField.clear();
            nameField.clear();
            passwordField.clear();
            roleTypeChoiceBox.getSelectionModel().clearSelection();
        }catch (Exception e){
//            e.printStackTrace();
            alert.setHeaderText(e.getMessage());

            alert.setTitle("Error");
            alert.show();
        }
    }
}
