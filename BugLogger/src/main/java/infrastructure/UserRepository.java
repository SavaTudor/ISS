package infrastructure;

import model.RoleType;
import model.User;
import utils.JdbcUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UserRepository implements IRepository<Integer, User>, IUserRepository{
    private JdbcUtils dbUtils;

    public UserRepository(Properties props) {
        this.dbUtils = new JdbcUtils(props);
    }

    @Override
    public Integer add(User user) throws RepositoryException {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement statement = con.prepareStatement("INSERT INTO Users(username, name, role, password) VALUES(?,?,?,?);")) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getName());

            statement.setInt(3, user.getRole().getValue());
            statement.setString(4, user.getPassword());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();

            if (rs.next()) {
                return rs.getInt(1);
            }
            throw new RepositoryException("");
        } catch (SQLException ex) {
            throw new RepositoryException("This user already exists!");
        }
    }

    @Override
    public User find(Integer integer) throws RepositoryException {
        Connection con = dbUtils.getConnection();
        List<User> competitors = new ArrayList<>();
        try (PreparedStatement statement = con.prepareStatement("SELECT * FROM users WHERE id=?;")) {
            statement.setInt(1, integer);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String username = resultSet.getString("username");
                    String name = resultSet.getString("name");
                    RoleType role = RoleType.valueOf(resultSet.getInt("role"));
                    String password = resultSet.getString("password");
                    User user = new User(username, name, role, password);
                    user.setId(integer);
                    return user;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error DB:" + ex);
        }
        throw new RepositoryException("The specified user does not exist!");
    }

    @Override
    public User findByUsername(String username) throws RepositoryException {
        Connection con = dbUtils.getConnection();
        List<User> competitors = new ArrayList<>();
        try (PreparedStatement statement = con.prepareStatement("SELECT * FROM users WHERE username=?;")) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Integer id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    RoleType role = RoleType.valueOf(resultSet.getInt("role"));
                    String password = resultSet.getString("password");
                    User user = new User(username, name, role, password);
                    user.setId(id);
                    return user;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error DB:" + ex);
        }
        throw new RepositoryException("The specified user does not exist!");
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public List<User> getAll() {
        return null;
    }
}
