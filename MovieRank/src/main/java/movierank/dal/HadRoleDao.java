package movierank.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import movierank.model.*;
import movierank.utils.ConnectionManager;

public class HadRoleDao {
  protected ConnectionManager connectionManager;

  private static HadRoleDao instance = null;
  protected HadRoleDao() {
    connectionManager = new ConnectionManager();
  }
  public static HadRoleDao getInstance() {
    if(instance == null) {
      instance = new HadRoleDao();
    }
    return instance;
  }

  public HadRole create(HadRole hadRole) throws SQLException {
    String insertHadRole = "INSERT INTO HadRole(title_id,name_id,role_) VALUES(?,?,?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;
    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insertHadRole);

      insertStmt.setString(1, hadRole.getMovies().getTitle_id());
      insertStmt.setString(2, hadRole.getPersons().getName_id());
      insertStmt.setString(3, hadRole.getRole_());

      insertStmt.executeUpdate();

      return hadRole;
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if(connection != null) {
        connection.close();
      }
      if(insertStmt != null) {
        insertStmt.close();
      }
    }
  }

  public HadRole getHadRoleByTitle_id(String title_id) throws SQLException{

    String selectHadRole = "SELECT title_id,name_id,role_ FROM hadrole WHERE title_id=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectHadRole);
      selectStmt.setString(1, title_id);
      results = selectStmt.executeQuery();

      MoviesDao moviesDao = MoviesDao.getInstance();
      PersonsDao personsDao = PersonsDao.getInstance();

      if(results.next()) {
    	String ttid = results.getString("title_id");
    	String nmid = results.getString("name_id");
        Movies movie = moviesDao.getMovieByTitleId(ttid);
        Persons person = personsDao.getPersonByName_id(nmid);
        String role_ = results.getString("role_");

        HadRole hadRole = new HadRole(movie,person,role_);
        return hadRole;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if(connection != null) {
        connection.close();
      }
      if(selectStmt != null) {
        selectStmt.close();
      }
      if(results != null) {
        results.close();
      }
    }
    return null;

  }

  public List<HadRole> getHadRoleByRole_(String role_) throws SQLException{

    List<HadRole> hadRoles = new ArrayList<HadRole>();
    String selectHadRole = "SELECT title_id,name_id,role_ FROM hadrole WHERE role_ LIKE ?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectHadRole);
      selectStmt.setString(1, role_);
      results = selectStmt.executeQuery();
      MoviesDao moviesDao = MoviesDao.getInstance();
      PersonsDao personsDao = PersonsDao.getInstance();
      while(results.next()) {
    	String ttid = results.getString("title_id");
      	String nmid = results.getString("name_id");
        Movies movie = moviesDao.getMovieByTitleId(ttid);
        Persons person = personsDao.getPersonByName_id(nmid);
        String resultRole_ = results.getString("role_");

        HadRole hadRole = new HadRole(movie,person,resultRole_);
        hadRoles.add(hadRole);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if(connection != null) {
        connection.close();
      }
      if(selectStmt != null) {
        selectStmt.close();
      }
      if(results != null) {
        results.close();
      }
    }
    return hadRoles;
  }

  public HadRole delete(HadRole hadRole) throws SQLException{
    String deleteHadRole = "DELETE FROM hadrole WHERE title_id=? AND name_id=? AND role_=?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(deleteHadRole);
      deleteStmt.setString(1, hadRole.getMovies().getTitle_id());
      deleteStmt.setString(2, hadRole.getPersons().getName_id());
      deleteStmt.setString(3, hadRole.getRole_());
      deleteStmt.executeUpdate();

      return null;
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if(connection != null) {
        connection.close();
      }
      if(deleteStmt != null) {
        deleteStmt.close();
      }
    }
  }


}
