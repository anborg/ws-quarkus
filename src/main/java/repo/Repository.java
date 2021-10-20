package repo;

import java.sql.SQLException;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

public interface Repository<T> {

  T get(int id) throws SQLException;

  List<T> getAll() throws SQLException;

  void insert(T item) throws SQLException;

  void update(int id, T item) throws SQLException;

  void delete(int id) throws SQLException;
}
