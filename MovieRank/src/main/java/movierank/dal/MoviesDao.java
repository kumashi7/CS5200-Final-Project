package movierank.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

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
		
		return movie;
	}
	
	public Movies getMovieByTitleId(String title_id) throws SQLException {
		String selectMovies = "SELECT title_id, primary_title, title_type, original_title, is_Adult, start_year, end_year, runtime_minutes FROM Movies WHERE title_id=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		Movies movie = null;
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
				movie = new Movies(resultMovieId, primaryTitle, titleType, originalType, isAdult, startYear, endYear, runtimeMinutes);
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
		return movie;
	}
	
	public List<Movies> getMovieByTitleName(String title) throws SQLException {
		String selectMovies = "SELECT title_id, primary_title, title_type, original_title, is_Adult, start_year, end_year, runtime_minutes FROM Movies WHERE primary_title like ?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		List<Movies> ret = new ArrayList<>();
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectMovies);
			selectStmt.setString(1, "%" + title + "%");
			results = selectStmt.executeQuery();
			while(results.next()) {
				String resultMovieId = results.getString("title_id");
				String primaryTitle = results.getString("primary_title");
				String titleType = results.getString("title_type");
				String originalType = results.getString("original_title");
				boolean isAdult = results.getBoolean("is_Adult");
				int startYear = results.getInt("start_year");
				int endYear = results.getInt("end_year");
				int runtimeMinutes = results.getInt("runtime_minutes");
				Movies movie = new Movies(resultMovieId, primaryTitle, titleType, originalType, isAdult, startYear, endYear, runtimeMinutes);
				ret.add(movie);
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
		return ret;
	}
	
	public List<Movies> getMovieByPersonName(String person) throws SQLException {
		String selectMovies = "SELECT movies.title_id, primary_title, name_, title_type, original_title, is_Adult, start_year, end_year, runtime_minutes FROM movies\r\n"
				+ "LEFT OUTER JOIN principals ON movies.title_id = principals.title_id\r\n"
				+ "LEFT OUTER JOIN persons ON principals.name_id = persons.name_id\r\n"
				+ "WHERE persons.name_ LIKE ?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		List<Movies> ret = new ArrayList<>();
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectMovies);
			selectStmt.setString(1, "%" + person + "%");
			results = selectStmt.executeQuery();
			while(results.next()) {
				String resultMovieId = results.getString("title_id");
				String primaryTitle = results.getString("primary_title");
				String personName = results.getString("name_");
				String titleType = results.getString("title_type");
				String originalType = results.getString("original_title");
				
				boolean isAdult = results.getBoolean("is_Adult");
				int startYear = results.getInt("start_year");
				int endYear = results.getInt("end_year");
				int runtimeMinutes = results.getInt("runtime_minutes");
				Movies movie = new Movies(resultMovieId, primaryTitle, titleType, originalType, isAdult, startYear, endYear, runtimeMinutes);
				movie.setPersonName(personName);
				ret.add(movie);
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
		return ret;
	}
	
	public Movies getMovieByTitleId(String title_id,String year) throws SQLException {
		String selectMovies = "SELECT title_id, primary_title, title_type, original_title, is_Adult, start_year, end_year, runtime_minutes FROM  Movies WHERE title_id=?";
		if(!("".equals(year)|| null==year)) {
			selectMovies+="and start_year = "+year;
		}
		selectMovies+=";";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		Movies movie = null;
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
				movie = new Movies(resultMovieId, primaryTitle, titleType, originalType, isAdult, startYear, endYear, runtimeMinutes);
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
		return movie;
	}
	
	public List<Movies> getMovieBy_titleId_year_type(String averageRating,String year, String type)  throws SQLException {
		List<Movies> movies = new ArrayList<Movies>();
		
		StringBuffer selectMovies = new StringBuffer("Select mo.title_id ,mo.primary_title, mo.title_type, mo.original_title, mo.is_Adult, mo.start_year, mo.end_year, mo.runtime_minutes FROM ratings ra join movies mo on ra.title_id = mo.title_id ");
		if (type != null && !"".equals(type)) {
			selectMovies.append(" join moviegenres mg on mo.title_id=mg.title_id ");
		}
		selectMovies.append(" where 1=1");
		if (averageRating != null && !"".equals(averageRating)) {
			selectMovies.append(" and ra.average_rating >= '")
			.append(averageRating)
			.append("'");
		}
		
		if (year != null && !"".equals(year)) {
			selectMovies.append(" and mo.start_year = '")
			.append(year)
			.append("'");
		}
		
		if (type != null && !"".equals(type)) {
			selectMovies.append(" and mg.genre = '")
			.append(type)
			.append("'");
		}
		selectMovies.append(";");
		
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		Movies movie = null;
		try {
		
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectMovies.toString());
			results = selectStmt.executeQuery();
			while(results.next()) {
				String resultMovieId = results.getString("title_id");
	
				String primaryTitle = results.getString("primary_title");
				String titleType = results.getString("title_type");
				String originalType = results.getString("original_title");
				boolean isAdult = results.getBoolean("is_Adult");
				int startYear = results.getInt("start_year");
				int endYear = results.getInt("end_year");
				int runtimeMinutes = results.getInt("runtime_minutes");
				
				movie = new Movies(resultMovieId, primaryTitle, titleType, originalType, isAdult, startYear, endYear, runtimeMinutes);
				movies.add(movie);
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
		return movies;
	}

	
	public String getGenreByTitleId(String titleId) throws SQLException {
		String sql = "select * from moviegenres where title_id = ?";
		StringJoiner joiner = new StringJoiner(",");
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		Movies movie = null;
		try {
		
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(sql);
			selectStmt.setString(1, titleId);
			results = selectStmt.executeQuery();
			while(results.next()) {
				String genre = results.getString("genre");
				joiner.add(genre);
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
		
		return joiner.toString();
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
		return movie;
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
		return null;
	}
}
