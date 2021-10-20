package repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;


import io.agroal.api.AgroalDataSource;
import io.quarkus.agroal.DataSource;
import model.WorkOrder;

@ApplicationScoped
public class RepositoryWorkOrder { //implements Repository<WorkOrder> {

  private final String INSERT_QUERY = "INSERT INTO WORK_ORDERS (id, description,author) VALUES(null,?,?,?)";
  private final String UPDATE_QUERY = "UPDATE  WorkOrders SET name=?,description=?,author=? WHERE id=?";
  private final String DELETE_QUERY = "DELETE FROM WorkOrders where id=?";
  private final String SELECT_ALL_QUERY = "SELECT * FROM WorkOrders";
  private final String SELECT_BY_ID_QUERY = "SELECT * FROM WorkOrders where id=?";
/*
  @Inject
  @DataSource("library")
  AgroalDataSource dataSource;

  @Override
  public WorkOrder get(int id) throws SQLException {
    WorkOrder WorkOrder = null;
    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(SELECT_BY_ID_QUERY);) {
      stmt.setInt(1, id);
      try (ResultSet rs = stmt.executeQuery();) {
        while (rs.next()) {
          WorkOrder = new WorkOrder(rs.getInt("id"), rs.getString("name"), rs.getString("description"), rs.getString("author"));
        }
      }
    } catch (SQLException e) {
      throw new SQLException("Get WorkOrder failed.");
    }
    return WorkOrder;
  }

  @Override
  public List<WorkOrder> getAll() throws SQLException {
    List<WorkOrder> WorkOrders = new ArrayList<>();
    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(SELECT_ALL_QUERY);) {
      try (ResultSet rs = stmt.executeQuery();) {
        while (rs.next()) {
          WorkOrders.add(
              new WorkOrder(rs.getInt("id"), rs.getString("name"), rs.getString("description"), rs.getString("author")));
        }
      }
    } catch (SQLException e) {
      throw new SQLException("Get WorkOrders failed.");

    }
    return WorkOrders;
  }

  @Override
  public void insert(WorkOrder WorkOrder) throws SQLException {
    try (PreparedStatement ps = dataSource.getConnection().prepareStatement(INSERT_QUERY, new String[] { "id" })) {
      ps.setString(1, WorkOrder.getName());
      ps.setString(2, WorkOrder.getDescription());
      ps.setString(3, WorkOrder.getAuthor());
      int numRowsAffected = ps.executeUpdate();

      if (numRowsAffected == 0) {
        throw new SQLException("Creating WorkOrder failed, no rows affected.");
      }
      try (ResultSet rs = ps.getGeneratedKeys()) {
        if (rs.next()) {
          WorkOrder.setId(Integer.parseInt(rs.getString(1)));
        }
      } catch (SQLException s) {
        throw new SQLException("Creating WorkOrder failed, no ID obtained.");
      }
    } catch (SQLException e) {
      throw new SQLException("Creating WorkOrder failed.");
    }

  }

  @Override
  public void update(int id, WorkOrder WorkOrder) throws SQLException {
    try (PreparedStatement ps = dataSource.getConnection().prepareStatement(UPDATE_QUERY)) {
      ps.setString(1, WorkOrder.getName());
      ps.setString(2, WorkOrder.getDescription());
      ps.setString(3, WorkOrder.getAuthor());
      ps.setInt(4, id);

      int numRowsAffected = ps.executeUpdate();

      if (numRowsAffected == 0) {
        throw new SQLException("Update WorkOrder failed, no rows affected.");
      }

    } catch (SQLException e) {
      throw new SQLException("update WorkOrder failed.");
    }

  }

  @Override
  public void delete(int id) throws SQLException {
    try (PreparedStatement ps = dataSource.getConnection().prepareStatement(DELETE_QUERY)) {
      ps.setInt(1, id);

      int numRowsAffected = ps.executeUpdate();

      if (numRowsAffected == 0) {
        throw new SQLException("Delete WorkOrder failed, no rows affected.");
      }

    } catch (SQLException e) {
      throw new SQLException("Delete WorkOrder failed.");
    }

  }
*/
}
