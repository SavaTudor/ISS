package controllers;

import javafx.beans.property.SimpleStringProperty;
import model.Severity;
import model.Status;

public class BugModel {
    private SimpleStringProperty name;
    SimpleStringProperty id;
    private SimpleStringProperty status;
    private SimpleStringProperty severity;
    private SimpleStringProperty assignedTo;

    public BugModel(SimpleStringProperty id, SimpleStringProperty name, SimpleStringProperty status, SimpleStringProperty severity, SimpleStringProperty assignedTo) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.severity = severity;
        this.assignedTo = assignedTo;
    }

    public BugModel(int id, String name, Status status, Severity severity, String assignedTo) {
        this.id = new SimpleStringProperty(String.valueOf(id));
        this.name = new SimpleStringProperty(name);
        this.status = new SimpleStringProperty(status.getStatusString());
        this.severity = new SimpleStringProperty(severity.getSeverityString());
        this.assignedTo = new SimpleStringProperty(assignedTo);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public String getSeverity() {
        return severity.get();
    }

    public SimpleStringProperty severityProperty() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity.set(severity);
    }

    public String getAssignedTo() {
        return assignedTo.get();
    }

    public SimpleStringProperty assignedToProperty() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo.set(assignedTo);
    }

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }
}
