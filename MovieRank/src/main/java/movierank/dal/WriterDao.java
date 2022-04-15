package movierank.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import movierank.model.Movies;
import movierank.model.Persons;
import movierank.model.Writer;
import movierank.utils.ConnectionManager;


public class WriterDao extends PersonsDao {
	protected ConnectionManager connectionManager;
	
	private static WriterDao instance = null;
	protected WriterDao() {
		connectionManager = new ConnectionManager();
	}
	public static WriterDao getInstance() {
		if(instance == null) {
			instance = new WriterDao();
		}
		return instance;
	}

	/**
	 * Save the Persons instance by storing it in your MySQL instance.
	 * This runs a INSERT statement.
	 */
	public Writer create(Writer writer) throws SQLException {
			create(new Persons(writer.getNameId(), writer.getName_(), writer.getBirth_year(), writer.getDeath_year()));

			String insertWriter = "INSERT INTO Writers(title_id,name_id) VALUES(?,?);";
			Connection connection = null;
			PreparedStatement insertStmt = null;
			try {
				connection = connectionManager.getConnection();
				insertStmt = connection.prepareStatement(insertWriter);
				insertStmt.setString(1, writer.getMovie().getTitle_id());
				insertStmt.setString(2, writer.getNameId());
				insertStmt.executeUpdate();
				return writer;
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
	public Writer updateTitleId(Writer writer, String newTitleId) throws SQLException {
		String updatePerson = "UPDATE Writers SET title_id=? WHERE name_id=? and title_id=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			MoviesDao movieDao = MoviesDao.getInstance();
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updatePerson);
			updateStmt.setString(1, newTitleId);
			updateStmt.setString(2, writer.getNameId());
			updateStmt.setString(3, writer.getMovie().getTitle_id());
			updateStmt.executeUpdate();
			
			Movies newMovie = movieDao.getMovieByTitleId(newTitleId);
			writer.setMovie(newMovie);
			return writer;
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
	public Writer delete(Writer writer) throws SQLException {
		String deleteWriter = "DELETE FROM Writers WHERE name_id=? and title_id=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteWriter);
			deleteStmt.setString(1, writer.getNameId());
			deleteStmt.setString(2, writer.getMovie().getTitle_id());
			int affectedRows = deleteStmt.executeUpdate();
			
			if (affectedRows == 0) {
				throw new SQLException("No records available to delete for writer: " + writer.getNameId());
			}

			super.delete(writer);
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
	public Writer getWriterFromNameIdAndTitleId(String titleId, String nameId) throws SQLException {
		String selectWriter = "SELECT title_id,name_id FROM Writers WHERE name_id=? and title_id=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			MoviesDao movieDao = MoviesDao.getInstance();
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectWriter);
			selectStmt.setString(1, nameId);
			selectStmt.setString(2, titleId);
			results = selectStmt.executeQuery();
			if(results.next()) {
				String resultNameId = results.getString("name_id");
				String resultTitleId = results.getString("title_id");
				Persons person = super.getPersonByName_id(nameId);
				Movies newMovie = movieDao.getMovieByTitleId(resultTitleId);
				
				Writer writer = new Writer(resultNameId, person.getName_(), person.getBirth_year(), person.getDeath_year(), newMovie);
				return writer;
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
}
