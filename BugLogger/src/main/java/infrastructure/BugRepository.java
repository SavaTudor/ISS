package infrastructure;

import model.*;
import utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BugRepository implements IRepository<Integer, Bug> {

    private JdbcUtils dbUtils;

    public BugRepository(Properties props) {
        this.dbUtils = new JdbcUtils(props);
    }

    @Override
    public Integer add(Bug bug) throws RepositoryException {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement statement = con.prepareStatement("INSERT INTO bugs(name, description, severity, status, assignedTo) VALUES(?,?,?,?,?);")) {
            statement.setString(1, bug.getName());
            statement.setString(2, bug.getDescription());
            statement.setInt(3, bug.getSeverity().getValue());
            statement.setInt(4, bug.getStatus().getValue());
            statement.setInt(5, bug.getAssignedTo());
            statement.executeUpdate();
            return 0;
            //To be implemented: return the inserted bug's id
        } catch (SQLException ex) {
            throw new RepositoryException("This competitor already exists!");
        }
    }

    @Override
    public Bug find(Integer integer) throws RepositoryException {
        Connection con = dbUtils.getConnection();
        List<Bug> bugs = new ArrayList<>();
        try (PreparedStatement statement = con.prepareStatement("SELECT * FROM bugs WHERE id=?;")) {
            statement.setInt(1, integer);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Integer id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String description = resultSet.getString("description");
                    Severity severity = Severity.valueOf(resultSet.getInt("severity"));
                    Status status = Status.valueOf(resultSet.getInt("status"));
                    Integer assignedTo = resultSet.getInt("assignedTo");
                    Bug bug = new Bug(name, description, status, severity, assignedTo);
                    bug.setId(id);
                    return bug;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error DB:" + ex);
        }
        throw new RepositoryException("The specified user does not exist!");
    }

    @Override
    public void update(Bug bug) throws RepositoryException {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement statement = con.prepareStatement("UPDATE bugs SET name=?, status=? WHERE id=?;")) {
            statement.setString(1, bug.getName());
            statement.setInt(2, bug.getStatus().getValue());
            statement.setInt(3, bug.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new RepositoryException("The specified signUp does not exist!");
        }
    }

    @Override
    public void delete(Integer integer) throws RepositoryException {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement statement = con.prepareStatement("DELETE FROM bugs WHERE id=?;")) {
            Bug toBeDeleted = this.find(integer);
            statement.setInt(1, integer);
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error DB:" + ex);
        }
    }

    @Override
    public List<Bug> getAll() throws RepositoryException {
        Connection con = dbUtils.getConnection();
        List<Bug> bugs = new ArrayList<>();
        try (PreparedStatement statement = con.prepareStatement("SELECT * FROM bugs;")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Integer id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String description = resultSet.getString("description");
                    Severity severity = Severity.valueOf(resultSet.getInt("severity"));
                    Status status = Status.valueOf(resultSet.getInt("status"));
                    Integer assignedTo = resultSet.getInt("assignedTo");
                    Bug bug = new Bug(name, description, status, severity, assignedTo);
                    bug.setId(id);
                    bugs.add(bug);
                }
            }
        } catch (SQLException ex) {

            System.out.println("Error DB:" + ex);
        }
        if (bugs.isEmpty()) {
            throw new RepositoryException("There are no users in the data base!");
        }
        return bugs;
    }
}
