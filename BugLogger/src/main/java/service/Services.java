package service;

import infrastructure.*;
import model.*;
import utils.IObservable;
import utils.IObserver;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Services implements IObservable {
    private UserRepository userRepository;
    private BugRepository bugRepository;
    List<IObserver> observerList;

    public void addObserver(IObserver observer) {
        observerList.add(observer);
    }

    public void removeObserver(IObserver observer) {
        observerList.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (var i : observerList) {
            i.update();
        }
    }

    public Services() {
        observerList = new ArrayList<>();

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
        return userRepository.findById(id);
    }

    public ArrayList<User> getProgrammers() {
        var list = new ArrayList<User>();
        userRepository.getAll().forEach(x -> {
            if (x.getRole() == RoleType.PROGRAMMER) {
                list.add(x);
            }
        });
        return list;
    }

    public void addNewBug(String titleString, String descriptionString, Severity value, User value1) throws
            RepositoryException {

        bugRepository.add(new Bug(titleString, descriptionString, Status.NEW, value, value1.getId()));
        notifyObservers();
    }

    public void editBug(Integer id, String titleString, String descriptionString, Status value) throws RepositoryException {
        Bug bug = bugRepository.find(id);
        Bug newBug = new Bug(titleString, descriptionString, value, bug.getSeverity(), bug.getAssignedTo());
        newBug.setId(id);
        bugRepository.update(newBug);
        notifyObservers();
    }

    public Bug findBug(Integer id) {
        try {
            return bugRepository.find(id);
        } catch (Exception ignored) {
        }
        return null;
    }

    public void deleteBug(Integer id) throws RepositoryException {
        try {
            bugRepository.delete(id);
            notifyObservers();
        }catch (Exception e){
            throw new RepositoryException(e.getMessage());
        }
    }

    public void addUser(String username, String name, String password, RoleType role) throws RepositoryException {
        User user = new User(username, name, role, password);
        userRepository.add(user);
    }
}

