package infrastructure;

import java.util.List;

public interface IRepository<ID, T> {
    ID add(T t) throws RepositoryException;
    T find(ID id) throws RepositoryException;
    void update(T t) throws RepositoryException;
    void delete(ID id) throws RepositoryException;
    List<T> getAll() throws RepositoryException;
}
