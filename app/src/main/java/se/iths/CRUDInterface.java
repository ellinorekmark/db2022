package se.iths;

import java.sql.SQLException;
import java.util.Collection;

public interface CRUDInterface<T> {

    Collection<T> findAll() throws SQLException;

    T findById(int id) throws SQLException;

    T update(T object) throws SQLException;

    boolean delete(T object) ;
}
