package infrastructure;

import model.User;

public interface IUserRepository {
    User findByUsername(String username) throws RepositoryException;
}
