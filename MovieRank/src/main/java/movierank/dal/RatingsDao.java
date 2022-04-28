// Qiushi Liang


package movierank.dal;

import movierank.model.*;
import movierank.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RatingsDao {
    protected ConnectionManager connectionManager;
    private static RatingsDao instance = null;
    protected RatingsDao() {
        connectionManager = new ConnectionManager();
    }
    public static RatingsDao getInstance() {
        if(instance == null) {
            instance = new RatingsDao();
        }
        return instance;
    }
    
    public Ratings create(Ratings rating) throws SQLException {
        String insertRating = "INSERT INTO Ratings(title_id, average_rating, num_votes) VALUES(?, ?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;

        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertRating);
            insertStmt.setString(1, rating.getTitleId());
            insertStmt.setDouble(2, rating.getAverageRating());
            insertStmt.setInt(3, rating.getNumVotes());
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
        return rating;
    }

    public List<Ratings> getRatingsByTitleId(String titleId) throws SQLException{
    	List<Ratings> ratings = new ArrayList<Ratings>();
        String selectRating = "SELECT title_id, average_rating, num_votes FROM Ratings WHERE title_id=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        MoviesDao moviesDao = MoviesDao.getInstance();

        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectRating);
            selectStmt.setString(1, titleId);
            results = selectStmt.executeQuery();
            
            while(results.next()) {
                String resultRatingTitleId = results.getString("title_id");
                Double averageRating = results.getDouble("average_rating");
                int numVotes = results.getInt("num_votes");
                Movies movie;
				movie = moviesDao.getMovieByTitleId(titleId);
                Ratings rating = new Ratings(resultRatingTitleId, averageRating, numVotes, movie);
                ratings.add(rating);
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

        return ratings;
    }

    public List<Ratings> getRatingsByAverageRating(String averageRating,String type,String year) throws SQLException{
    	List<Ratings> ratings = new ArrayList<Ratings>();
    	StringBuffer stringBuffer = new StringBuffer();
    	stringBuffer.append("select * FROM ratings A INNER JOIN (select title_id from moviegenres  ");
    	if(!("".equals(type)||null == type)) {
    		stringBuffer.append("WHERE genre = '");
    		stringBuffer.append(type);
    		stringBuffer.append("'");
    	}
    	stringBuffer.append("  GROUP BY title_id )   B on A.title_id = B.title_id");
    	if(averageRating != null) {
    		stringBuffer.append(" WHERE A.average_rating>= '");
    		stringBuffer.append(averageRating);
    		stringBuffer.append("'");
    	}
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
       
            selectStmt = connection.prepareStatement(stringBuffer.toString());
            results = selectStmt.executeQuery();
            MoviesDao moviesDao = MoviesDao.getInstance();

            while(results.next()) {
                String titleId = results.getString("title_id");
                Double resultAverageRating = results.getDouble("average_rating");
                //String genre = results.getString("genre");
                int numVotes = results.getInt("num_votes");
                Ratings rating = new Ratings(titleId, resultAverageRating, numVotes, null);
                ratings.add(rating);
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

        return ratings;
    }
    
    /* 
     * get Objects by votes & average ratings
     */
    public List<Ratings> getRatingsByNumOfVotes(Integer numOfVotes) throws SQLException{
    	List<Ratings> ratings = new ArrayList<Ratings>();
    	String selectRating = "SELECT title_id, average_rating, num_votes FROM Ratings WHERE num_votes>=?;";
    	Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectRating);
            selectStmt.setInt(1, numOfVotes);
            results = selectStmt.executeQuery();
            MoviesDao moviesDao = MoviesDao.getInstance();

            while(results.next()) {
                String titleId = results.getString("title_id");
                Double resultAverageRating = results.getDouble("average_rating");
                int numVotes = results.getInt("num_votes");
                Movies movie = moviesDao.getMovieByTitleId(titleId);
                Ratings rating = new Ratings(titleId, resultAverageRating, numVotes, movie);
                ratings.add(rating);
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
        return ratings;
    }
    
    
    /*
     * Functions needed for search movies by ratings and votes
     */
    public List<Ratings> getRatingsByRatingsAndVotes(Double averageRating, Integer numOfVotes) throws SQLException{
    	List<Ratings> ratings = new ArrayList<Ratings>();
    	String selectRating = "SELECT title_id, average_rating, num_votes FROM Ratings WHERE average_rating>=? AND num_votes>=?;";
    	Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectRating);
            selectStmt.setDouble(1, averageRating);
            selectStmt.setInt(2, numOfVotes);
            results = selectStmt.executeQuery();
            MoviesDao moviesDao = MoviesDao.getInstance();

            while(results.next()) {
                String titleId = results.getString("title_id");
                Double resultAverageRating = results.getDouble("average_rating");
                int numVotes = results.getInt("num_votes");
                Movies movie = moviesDao.getMovieByTitleId(titleId);
                Ratings rating = new Ratings(titleId, resultAverageRating, numVotes, movie);
                ratings.add(rating);
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
        return ratings;
    }

    public Ratings updateNumVotes(Ratings rating, int numVotes) throws SQLException{
        String updateRating = "UPDATE Ratings SET num_votes=? WHERE title_id=? AND average_rating=? AND num_votes=?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;

        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(updateRating);
            updateStmt.setInt(1, numVotes);
            updateStmt.setString(2, rating.getTitleId());
            updateStmt.setDouble(3, rating.getAverageRating());
            updateStmt.setInt(4, rating.getNumVotes());
            updateStmt.executeUpdate();
            rating.setNumVotes(numVotes);

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
        return rating;
    }

    public Ratings delete(Ratings rating) throws SQLException{
        String deleteRating = "DELETE FROM Ratings WHERE title_id=? AND average_rating=? AND num_votes=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;

        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteRating);
            deleteStmt.setString(1, rating.getTitleId());
            deleteStmt.setDouble(2, rating.getAverageRating());
            deleteStmt.setInt(3, rating.getNumVotes());
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