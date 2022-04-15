// Qiushi Liang


package movierank.dal;

import movierank.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrincipalsDao extends PersonsDao{
	  private static PrincipalsDao instance = null;
	  protected PrincipalsDao() {
	    super();
	  }
	  public static PrincipalsDao getInstance() {
	    if(instance == null) {
	      instance = new PrincipalsDao();
	    }
	    return instance;
	  }

	  public Principals create(Principals principal) throws SQLException {
	    create(new Persons(principal.getName_id(), principal.getName_(), principal.getBirth_year(), principal.getDeath_year()));
	    String insertPrincipal = "INSERT INTO Principals(title_id, ordering, name_id, job_category, job) VALUES(?, ?, ?, ?, ?);";
	    Connection connection = null;
	    PreparedStatement insertStmt = null;

	    try {
	      connection = connectionManager.getConnection();
	      insertStmt = connection.prepareStatement(insertPrincipal);
	      insertStmt.setString(1, principal.getTitleId());
	      insertStmt.setInt(2, principal.getOrdering());
	      insertStmt.setString(3, principal.getName_id());
	      insertStmt.setString(4, principal.getJobCategory());
	      insertStmt.setString(5, principal.getJob());
	      insertStmt.executeUpdate();

	      return principal;
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

	  public Principals getPrincipalByTidOrderingNid(String titleId, int ordering, String nameId) throws SQLException{
	    String selectPrincipal = "SELECT * "
	        + "FROM Principals INNER JOIN Persons "
	        + "ON Persons.name_id=Principals.name_id "
	        + "WHERE Principals.title_id=? AND Principals.ordering=? AND Principals.name_id=?;";
	    Connection connection = null;
	    PreparedStatement selectStmt = null;
	    ResultSet results = null;

	    try {
	      MoviesDao moviesDao = MoviesDao.getInstance();
	      PersonsDao personsDao = PersonsDao.getInstance();
	      connection = connectionManager.getConnection();
	      selectStmt = connection.prepareStatement(selectPrincipal);
	      selectStmt.setString(1, titleId);
	      selectStmt.setInt(2, ordering);
	      selectStmt.setString(3, nameId);

	      results = selectStmt.executeQuery();

	      if(results.next()) {
	        String jobCategory = results.getString("job_category");
	        String job = results.getString("job");
	        Movies movie = moviesDao.getMovieByTitleId(titleId);
	        Persons person  = personsDao.getPersonByName_id(nameId);
	        String name_ = person.getName_();
	        int birthDate = person.getBirth_year();
	        int deathDate = person.getDeath_year();
	        Principals principals = new Principals(titleId, ordering, nameId, jobCategory, job, name_, birthDate, deathDate, movie);

	        return principals;
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

	  public List<Principals> getPrincipalsByTitleId(String titleId) throws SQLException{
		List<Principals> principals = new ArrayList<Principals>();
	    String selectPrincipal = "SELECT * "
	        + "FROM Persons INNER JOIN Principals "
	        + "ON Persons.name_id=Principals.name_id "
	        + "HAVING Principals.title_id=?;";
	    Connection connection = null;
	    PreparedStatement selectStmt = null;
	    ResultSet results = null;
	    MoviesDao moviesDao = MoviesDao.getInstance();
	    PersonsDao personsDao = PersonsDao.getInstance();

	    try {
	      connection = connectionManager.getConnection();
	      selectStmt = connection.prepareStatement(selectPrincipal);
	      selectStmt.setString(1, titleId);
	      results = selectStmt.executeQuery();


	      while(results.next()) {
	        int ordering = results.getInt("ordering");
	        String nameId = results.getString("name_id");
	        String jobCategory = results.getString("job_category");
	        String job = results.getString("job");
	        Movies movie = moviesDao.getMovieByTitleId(titleId);
	        Persons person = personsDao.getPersonByName_id(nameId);
	        String name_ = person.getName_();
	        int birthDate = person.getBirth_year();
	        int deathDate = person.getDeath_year();
	        Principals principal = new Principals(titleId, ordering, nameId, jobCategory, job, name_, birthDate, deathDate, movie);
	        principals.add(principal);
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

	    return principals;
	  }

	  public Principals updateJobCategory(Principals principal, String jobCategory) throws SQLException{
	    String updatePrincipal = "UPDATE Principals SET job_category=? WHERE title_id=? AND ordering=? AND name_id=?;";
	    Connection connection = null;
	    PreparedStatement updateStmt = null;

	    try {
	      connection = connectionManager.getConnection();
	      updateStmt = connection.prepareStatement(updatePrincipal);
	      updateStmt.setString(1, jobCategory);
	      updateStmt.setString(2, principal.getTitleId());
	      updateStmt.setInt(3, principal.getOrdering());
	      updateStmt.setString(4, principal.getName_id());
	      updateStmt.executeUpdate();
	      principal.setJobCategory(jobCategory);

	      return principal;
	    } catch (SQLException e) {
	      e.printStackTrace();
	      throw e;
	    } finally {
	      if(connection != null) {
	        connection.close();
	      }
	      if(updateStmt != null) {
	        updateStmt.close();
	      }
	    }
	  }

	  public Principals updateJob(Principals principal, String job) throws SQLException{
	    String updatePrincipal = "UPDATE Principals SET job=? WHERE title_id=? AND ordering=? AND name_id=?;";
	    Connection connection = null;
	    PreparedStatement updateStmt = null;

	    try {
	      connection = connectionManager.getConnection();
	      updateStmt = connection.prepareStatement(updatePrincipal);
	      updateStmt.setString(1, job);
	      updateStmt.setString(2, principal.getTitleId());
	      updateStmt.setInt(3, principal.getOrdering());
	      updateStmt.setString(4, principal.getName_id());
	      updateStmt.executeUpdate();
	      principal.setJob(job);

	      return principal;
	    } catch (SQLException e) {
	      e.printStackTrace();
	      throw e;
	    } finally {
	      if(connection != null) {
	        connection.close();
	      }
	      if(updateStmt != null) {
	        updateStmt.close();
	      }
	    }
	  }

	  public Principals delete(Principals principal) throws SQLException{
		    String deletePrincipal = "DELETE FROM Principals WHERE title_id=? AND ordering=? AND name_id=?;";
	        Connection connection = null;
	        PreparedStatement deleteStmt = null;

	        try {
	            connection = connectionManager.getConnection();
	            deleteStmt = connection.prepareStatement(deletePrincipal);
	            deleteStmt.setString(1, principal.getTitleId());
	            deleteStmt.setInt(2, principal.getOrdering());
	            deleteStmt.setString(3, principal.getName_id());
	            deleteStmt.executeUpdate();
	            super.delete(principal);
	            
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