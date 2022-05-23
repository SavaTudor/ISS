package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;
import service.Services;

import java.io.IOException;

public class LoginController {
    private Services service;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    public void setService(Services service) {
        this.service = service;
    }

    @FXML
    public void signInClicked(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
        try {
            User user = service.login(usernameField.getText(), passwordField.getText());
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/MainPageNormalUsers.fxml"));
            AnchorPane root = loader.load();
            UserController mainPageController = (UserController) loader.getController();
            mainPageController.setService(service, user);
            Scene scene = new Scene(root, 800, 400);
            Stage stage;
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Main scene");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
//            e.printStackTrace();
            alert.setHeaderText(e.getMessage());

            alert.setTitle("Error");
            alert.show();
        }
    }

}
