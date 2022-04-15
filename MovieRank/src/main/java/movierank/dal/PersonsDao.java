package movierank.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import movierank.model.Persons;
import movierank.utils.ConnectionManager;

public class PersonsDao {

  protected ConnectionManager connectionManager;

  // Single pattern: instantiation is limited to one object.
  private static PersonsDao instance = null;
  protected PersonsDao() {
    connectionManager = new ConnectionManager();
  }
  public static PersonsDao getInstance() {
    if(instance == null) {
        instance = new PersonsDao();
    }
    return instance;
  }


  public Persons create(Persons person) throws SQLException{
    String insertPerson = "INSERT INTO Persons(name_id,name_,birth_year,death_year) VALUES(?,?,?,?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;

    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insertPerson);

      insertStmt.setString(1, person.getName_id());
      insertStmt.setString(2, person.getName_());
      insertStmt.setInt(3, person.getBirth_year());
      insertStmt.setInt(4, person.getDeath_year());

      insertStmt.executeUpdate();
      return person;
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

  public Persons getPersonByName_id(String name_id) throws SQLException{
    String selectPerson = "SELECT name_id,name_,birth_year,death_year FROM Persons WHERE name_id=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;

    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectPerson);
      selectStmt.setString(1, name_id);
      results = selectStmt.executeQuery();

      if(results.next()) {

        String resultName_id = results.getString("name_id");
        String name_ = results.getString("name_");
        int birth_year = results.getInt("birth_year");
        int death_year = results.getInt("death_year");

        Persons person = new Persons(resultName_id, name_, birth_year, death_year);
        return person;
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
  
  public Persons updatePersonDeath_year(Persons person, int newDeath_year) throws SQLException {
	  String updatePerson = "UPDATE Persons SET death_year=? WHERE name_id=?;";
	  Connection connection = null;
	  PreparedStatement updateStmt = null;
	  try {
		  connection = connectionManager.getConnection();
		  updateStmt = connection.prepareStatement(updatePerson);
		  updateStmt.setInt(1, newDeath_year);
		  updateStmt.setString(2, person.getName_id());
		  updateStmt.executeUpdate();
			
		  person.setDeath_year(newDeath_year);
		  return person;
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

  public Persons delete(Persons person) throws SQLException{
    String deletePerson = "DELETE FROM Persons WHERE name_id=?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;

    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(deletePerson);
      deleteStmt.setString(1, person.getName_id());
      deleteStmt.executeUpdate();

      // Return null so the caller can no longer operate on the Persons instance.
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
