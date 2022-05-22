package model;

public class Bug extends Entity<Integer> {
    private String name;
    private String description;
    private Status status;
    private Severity severity;
    private Integer assignedTo;

    public Bug(String name, String description, Status status, Severity severity, Integer assignedTo) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.severity = severity;
        this.assignedTo = assignedTo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public Integer getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Integer assignedTo) {
        this.assignedTo = assignedTo;
    }


}
