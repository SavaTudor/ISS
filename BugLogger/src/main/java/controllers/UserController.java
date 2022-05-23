package controllers;

import infrastructure.RepositoryException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.RoleType;
import model.User;
import service.Services;
import utils.IObserver;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;


public class UserController implements Initializable, IObserver {
    private Services service;
    public User user;

    @FXML
    public Label username;

    @FXML
    public TableView<BugModel> tableView;
    @FXML
    public TableColumn<BugModel, String> id;
    @FXML
    public TableColumn<BugModel, String> name;
    @FXML
    public TableColumn<BugModel, String> status;
    @FXML
    public TableColumn<BugModel, String> severity;

    @FXML
    public Button addNewBugBtn;

    @FXML
    public Button addNewUser;
    @FXML
    public TableColumn<BugModel, String> assignedTo;

    public void setService(Services service, User user) {
        this.service = service;
        this.user = user;
        username.setText(user.getName());
        addNewBugBtn.setDisable(user.getRole() == RoleType.PROGRAMMER);
        addNewUser.setDisable(user.getRole() != RoleType.ADMIN);
        try {
            tableView.setItems(loadTable());
        } catch (Exception ignored) {

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        severity.setCellValueFactory(new PropertyValueFactory<>("severity"));
        assignedTo.setCellValueFactory(new PropertyValueFactory<>("assignedTo"));
        try {
//            tableView.setItems(loadTable());
        } catch (Exception ignored) {
        }
    }

    private ObservableList<BugModel> loadTable() throws RepositoryException {
        LinkedList<BugModel> bugList = new LinkedList<>();
        var allBugs = service.getAllBugs();
        allBugs.forEach(x -> {
            try {
                User assignedTo = service.findUser(x.getAssignedTo());
                BugModel bugModel = new BugModel(x.getId(), x.getName(), x.getStatus(), x.getSeverity(), assignedTo.getName());
                bugList.add(bugModel);
            } catch (RepositoryException e) {
                e.printStackTrace();
            }
        });
        return FXCollections.observableArrayList(bugList);
    }


    @FXML
    private void addNewBugClicked() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/AddNewBugPopup.fxml"));
            AnchorPane root = loader.load();
            AddBugController addBugController = (AddBugController) loader.getController();
            addBugController.setService(service);
            service.addObserver(this);
            Stage stage = new Stage();
            stage.setTitle("Add Bug");
            stage.setScene(new Scene(root, 450, 450));
            stage.show();
            tableView.setItems(loadTable());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void editBugClicked() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/EditBugPopup.fxml"));
            AnchorPane root = loader.load();
            EditBugController editBugController = (EditBugController) loader.getController();
            BugModel selectedBug = tableView.getSelectionModel().getSelectedItem();
            editBugController.setService(service, Integer.parseInt(selectedBug.id.getValue()), user);
            service.addObserver(this);
            Stage stage = new Stage();
            stage.setTitle("Edit Bug");
            stage.setScene(new Scene(root, 450, 450));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addNewUserClicked() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/AddNewEmployeePopup.fxml"));
            AnchorPane root = loader.load();
            NewUserController newUserController = (NewUserController) loader.getController();
            newUserController.setService(service);
            Stage stage = new Stage();
            stage.setTitle("New User");
            stage.setScene(new Scene(root, 450, 450));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update() {
        try {
            tableView.setItems(loadTable());
        } catch (Exception ignored) {
        }
    }
}
