package movierank.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import movierank.model.*;
import movierank.utils.ConnectionManager;

public class MoviesDao {
	protected static ConnectionManager connectionManager;
	private static MoviesDao instance = null;
	protected MoviesDao() {
		connectionManager = new ConnectionManager();
	}
	
	public static MoviesDao getInstance() {
		if (instance == null) instance = new MoviesDao();
		return instance;
	}
	
	public Movies create(Movies movie) throws SQLException{
		String insertMovies = "INSERT INTO Movies(title_id, primary_title, title_type, original_title, is_Adult, start_year, end_year, runtime_minutes) VALUES(?,?,?,?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertMovies);
			insertStmt.setString(1, movie.getTitle_id());
			insertStmt.setString(2, movie.getPrimary_title());
			insertStmt.setString(3, movie.getTitle_type());
			insertStmt.setString(4, movie.getOriginal_title());
			insertStmt.setBoolean(5, movie.is_Adult());
			insertStmt.setInt(6, movie.getStart_year());
			insertStmt.setInt(7, movie.getEnd_year());
			insertStmt.setInt(8, movie.getRuntime_minutes());
			insertStmt.executeUpdate();
			
			return movie;
			
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
	
	public Movies getMovieByTitleId(String title_id) throws SQLException {
		String selectMovies = "SELECT title_id, primary_title, title_type, original_title, is_Adult, start_year, end_year, runtime_minutes FROM Movies WHERE title_id=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectMovies);
			selectStmt.setString(1, title_id);
			results = selectStmt.executeQuery();
			if(results.next()) {
				String resultMovieId = results.getString("title_id");
				String primaryTitle = results.getString("primary_title");
				String titleType = results.getString("title_type");
				String originalType = results.getString("original_title");
				boolean isAdult = results.getBoolean("is_Adult");
				int startYear = results.getInt("start_year");
				int endYear = results.getInt("end_year");
				int runtimeMinutes = results.getInt("runtime_minutes");
				Movies movie = new Movies(resultMovieId, primaryTitle, titleType, originalType, isAdult, startYear, endYear, runtimeMinutes);
				return movie;
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
	
	public Movies updateMovieEndyear(Movies movie, int newEndYear) throws SQLException {
		String updateMovie = "UPDATE Movies SET end_year=? WHERE title_id=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateMovie);
			updateStmt.setInt(1, newEndYear);
			updateStmt.setString(2, movie.getTitle_id());
			updateStmt.executeUpdate();
			
			movie.setEnd_year(newEndYear);
			return movie;
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
	
	
	public Movies delete(Movies movie) throws SQLException {
		String deleteMovie = "DELETE FROM Movies WHERE title_id=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteMovie);
			deleteStmt.setString(1, movie.getTitle_id());
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
