package se.iths;

import java.sql.SQLException;
import java.util.Collection;

public interface CRUDInterface<T> {

    public Collection<T> findAll() throws SQLException;

    public T findById(int id) throws SQLException;
    public T create(T object);

    public T update(T object);

    public boolean delete(T object);
}
