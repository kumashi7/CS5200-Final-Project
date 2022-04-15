package movierank.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import movierank.model.*;
import movierank.utils.ConnectionManager;

public class DirectorsDao extends PersonsDao{

protected ConnectionManager connectionManager;
	
	private static DirectorsDao instance = null;
	protected DirectorsDao() {
		connectionManager = new ConnectionManager();
	}
	public static DirectorsDao getInstance() {
		if(instance == null) {
			instance = new DirectorsDao();
		}
		return instance;
	}
	
	public Directors create(Directors directors) throws SQLException {
		create(new Persons(directors.getName_id(), directors.getName_(), directors.getBirth_year(), directors.getDeath_year()));
		String insertDirectors = "INSERT INTO Directors(title_id,name_id) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertDirectors);

			insertStmt.setString(1, directors.getMovie().getTitle_id());
			insertStmt.setString(2, directors.getName_id());

			insertStmt.executeUpdate();

			return directors;
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
	
	public Directors delete(Directors directors) throws SQLException {
		String deleteDirectors = "DELETE FROM Directors WHERE title_id=? And name_id=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteDirectors);
			deleteStmt.setString(1, directors.getMovie().getTitle_id());
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
	
	public Directors getDirectorsFromTitleIdAndNameId(String titleId, String nameId) throws SQLException {
		String selectDirectors = "SELECT title_id,name_id FROM Directors WHERE title_id=? And name_id=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectDirectors);
			selectStmt.setString(1, titleId);
			selectStmt.setString(2, nameId);
			PersonsDao personsDao = PersonsDao.getInstance();
			MoviesDao moviesDao = MoviesDao.getInstance();

			results = selectStmt.executeQuery();

			if(results.next()) {
				String resultTitleId = results.getString("title_id");
				String resultNameId = results.getString("name_id");
				Persons person = personsDao.getPersonByName_id(resultNameId);
				Movies movie = moviesDao.getMovieByTitleId(resultTitleId);
				Directors director = new Directors(person.getName_id(), person.getName_(),
						person.getBirth_year(), person.getDeath_year(), movie);
				return director;
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
