package service;

import infrastructure.BugRepository;
import infrastructure.RepositoryException;
import infrastructure.UserRepository;
import model.Bug;
import model.User;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class Services {
    private UserRepository userRepository;
    private BugRepository bugRepository;

    public Services() {
        var props = new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find test.config " + e);
        }
        userRepository = new UserRepository(props);
        bugRepository = new BugRepository(props);
    }

    public User login(String username, String password) throws RepositoryException {
        User user = userRepository.findByUsername(username);
        if (user.getPassword().equals(password)) {
            return user;
        }
        throw new RepositoryException("Invalid credentials");
    }

    public List<Bug> getAllBugs() throws RepositoryException {
        return bugRepository.getAll();
    }

    public User findUser(Integer id) throws RepositoryException {
        return userRepository.find(id);
    }
}
