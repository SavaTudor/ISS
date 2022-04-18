package controllers;

import infrastructure.RepositoryException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.User;
import service.Services;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;


public class UserController implements Initializable {
    private Services service;
    public User user;

    @FXML
    public Label username;

    @FXML
    public TableView<BugModel> tableView;
    @FXML
    public TableColumn<BugModel, String> name;
    @FXML
    public TableColumn<BugModel, String> status;
    @FXML
    public TableColumn<BugModel, String> severity;

    @FXML
    public TableColumn<BugModel, String> assignedTo;

    public void setService(Services service, User user) {
        this.service = service;
        this.user = user;
        username.setText(user.getName());
        try {
            tableView.setItems(loadTable());
        } catch (Exception ignored) {

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        severity.setCellValueFactory(new PropertyValueFactory<>("severity"));
        assignedTo.setCellValueFactory(new PropertyValueFactory<>("assignedTo"));
        try {
            tableView.setItems(loadTable());
        } catch (Exception ignored) {
        }
    }

    private ObservableList<BugModel> loadTable() throws RepositoryException {
        LinkedList<BugModel> bugList = new LinkedList<>();
        var allBugs = service.getAllBugs();
        allBugs.forEach(x -> {
            try {
                User assignedTo = service.findUser(x.getAssignedTo());
                BugModel bugModel = new BugModel(x.getName(), x.getStatus(), x.getSeverity(), assignedTo.getName());
                bugList.add(bugModel);
            } catch (RepositoryException e) {
                e.printStackTrace();
            }
        });
        return FXCollections.observableArrayList(bugList);
    }
}
