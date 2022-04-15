package movierank.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import movierank.model.Alias;
import movierank.model.Movies;
import movierank.utils.ConnectionManager;

public class AliasDao {

protected ConnectionManager connectionManager;
	
	private static AliasDao instance = null;
	protected AliasDao() {
		connectionManager = new ConnectionManager();
	}
	public static AliasDao getInstance() {
		if(instance == null) {
			instance = new AliasDao();
		}
		return instance;
	}

	/**
	 * Save the Persons instance by storing it in your MySQL instance.
	 * This runs a INSERT statement.
	 */
	public Alias create(Alias alias) throws SQLException {
		String insertAlias = "INSERT INTO Alias(title_id,ordering,title,region,language,is_original_title) "
				+ "VALUES(?,?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertAlias);
			
			insertStmt.setString(1, alias.getMovie().getTitle_id());
			insertStmt.setInt(2, alias.getOrdering());
			insertStmt.setString(3, alias.getTitle());
			insertStmt.setString(4, alias.getRegion());
			insertStmt.setString(5, alias.getLanguage());
			insertStmt.setBoolean(6, alias.isOriginalTitle());
			
			insertStmt.executeUpdate();
			
			return alias;
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

	/**
	 * Update the LastName of the Persons instance.
	 * This runs a UPDATE statement.
	 */
	public Alias updateTitle(Alias alias, String newTitle) throws SQLException {
		String updatePerson = "UPDATE Alias SET title=? WHERE title_id=? AND ordering=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updatePerson);
			updateStmt.setString(1, newTitle);
			updateStmt.setString(2, alias.getMovie().getTitle_id());
			updateStmt.setInt(3, alias.getOrdering());
			updateStmt.executeUpdate();
			
			alias.setTitle(newTitle);
			return alias;
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

	/**
	 * Delete the Persons instance.
	 * This runs a DELETE statement.
	 */
	public Alias delete(Alias alias) throws SQLException {
		String deletePerson = "DELETE FROM Alias WHERE title_id=? AND ordering=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deletePerson);
			deleteStmt.setString(1, alias.getMovie().getTitle_id());
			deleteStmt.setInt(2, alias.getOrdering());
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

	/**
	 * Get the Persons record by fetching it from your MySQL instance.
	 * This runs a SELECT statement and returns a single Persons instance.
	 */
	public Alias getAliasFromTitleIdAndOrdering(String titleId, int ordering) throws SQLException {
		String selectPerson = "SELECT title_id,ordering,title,region,language,is_original_title "
				+ "FROM Alias WHERE title_id=? AND ordering=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			MoviesDao movieDao = MoviesDao.getInstance();
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectPerson);
			selectStmt.setString(1, titleId);
			selectStmt.setInt(2, ordering);
			
			results = selectStmt.executeQuery();

			if(results.next()) {
				String resultTitleId = results.getString("title_id");
				int resultOrdering = results.getInt("ordering");
				String title = results.getString("title");
				String region = results.getString("region");
				String language = results.getString("language");
				boolean isOriginalTitle = results.getBoolean("is_original_title");
				
				Movies newMovie = movieDao.getMovieByTitleId(resultTitleId);
				
				Alias alias = new Alias(newMovie, resultOrdering, title, region, language,isOriginalTitle);
				return alias;
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

	/**
	 * Get the matching Persons records by fetching from your MySQL instance.
	 * This runs a SELECT statement and returns a list of matching Persons.
	 */
	public List<Alias> getAliasFromTitle(String title) throws SQLException {
		List<Alias> aliasList = new ArrayList<>();
		String selectPersons =
			"SELECT title_id,ordering,title,region,language,is_original_title "
			+ "FROM Alias WHERE title=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			MoviesDao movieDao = MoviesDao.getInstance();
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectPersons);
			selectStmt.setString(1, title);
			results = selectStmt.executeQuery();
			while(results.next()) {
				String resultTitleId = results.getString("title_id");
				int resultOrdering = results.getInt("ordering");
				String resultTitle = results.getString("title");
				String region = results.getString("region");
				String language = results.getString("language");
				boolean isOriginalTitle = results.getBoolean("is_original_title");
				Movies newMovie = movieDao.getMovieByTitleId(resultTitleId);
				Alias alias = new Alias(newMovie, resultOrdering, resultTitle, region, language,isOriginalTitle);
				aliasList.add(alias);
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
		return aliasList;
	}
}
