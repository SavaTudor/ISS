package infrastructure;

import model.Bug;
import model.Severity;
import model.Status;
import model.User;
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
        return null;
    }

    @Override
    public Bug find(Integer integer) throws RepositoryException {
        return null;
    }

    @Override
    public void update(Bug bug) throws RepositoryException {

    }

    @Override
    public void delete(Integer integer) throws RepositoryException {

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
