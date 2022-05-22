package infrastructure;

import model.User;

public interface IUserRepository {
    User findByUsername(String username) throws RepositoryException;
    User findById(Integer integer) throws RepositoryException;
}
