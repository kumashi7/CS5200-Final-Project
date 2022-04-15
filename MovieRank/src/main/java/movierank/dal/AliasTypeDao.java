package movierank.dal;

import movierank.model.*;
import movierank.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class AliasTypeDao extends AliasDao{

protected ConnectionManager connectionManager;
	
	private static AliasTypeDao instance = null;
	protected AliasTypeDao() {
		connectionManager = new ConnectionManager();
	}
	public static AliasTypeDao getInstance() {
		if(instance == null) {
			instance = new AliasTypeDao();
		}
		return instance;
	}
	
	public AliasType create(AliasType aliasType) throws SQLException {
		String insertAliasType = "INSERT INTO AliasType(title_id,ordering,type) VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertAliasType);

			insertStmt.setString(1, aliasType.getAlias().getMovie().getTitle_id());
			insertStmt.setInt(2, aliasType.getAlias().getOrdering());
			insertStmt.setString(3, aliasType.getType());

			insertStmt.executeUpdate();

			return aliasType;
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
	
	public AliasType updateType(AliasType aliasType, String newType) throws SQLException {
		String updateAliasType = "UPDATE AliasType SET Type=? WHERE title_id=? And ordering=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateAliasType);
			updateStmt.setString(1, newType);
			updateStmt.setString(2, aliasType.getAlias().getMovie().getTitle_id());
			updateStmt.setInt(3, aliasType.getAlias().getOrdering());
			updateStmt.executeUpdate();
			
			aliasType.setType(newType);
			return aliasType;
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
	
	public AliasType delete(AliasType aliasType) throws SQLException {
		String deleteAliasType = "DELETE FROM AliasType WHERE title_id=? And ordering=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteAliasType);
			deleteStmt.setString(1, aliasType.getAlias().getMovie().getTitle_id());
			deleteStmt.setInt(2, aliasType.getAlias().getOrdering());
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
	
	public AliasType getAliasTypeFromTitleIdAndOrdering(String titleId, int ordering) throws SQLException {
		String selectAliasType = "SELECT title_id,ordering,type FROM AliasType WHERE title_id=? And ordering=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectAliasType);
			selectStmt.setString(1, titleId);
			selectStmt.setInt(2, ordering);
			AliasDao aliasDao = AliasDao.getInstance();

			results = selectStmt.executeQuery();

			if(results.next()) {
				String resultTitleId = results.getString("title_id");
				int resultOrdering = results.getInt("ordering");
				String type = results.getString("type");
				Alias alias = aliasDao.getAliasFromTitleIdAndOrdering(resultTitleId, resultOrdering);
				AliasType aliasType = new AliasType(alias, type);
				return aliasType;
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
	
	public List<AliasType> getAliasTypeFromType(String type) throws SQLException {
		List<AliasType> aliasTypes = new ArrayList<AliasType>();
		String selectAliasType = "SELECT title_id,ordering,type"
				+ " FROM AliasType"
				+ " WHERE type=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectAliasType);
			selectStmt.setString(1, type);
			AliasDao aliasDao = AliasDao.getInstance();
			results = selectStmt.executeQuery();
			
			while(results.next()) {
				String titleId = results.getString("title_id");
				int ordering = results.getInt("ordering");
				String resultType = results.getString("type");
				Alias alias = aliasDao.getAliasFromTitleIdAndOrdering(titleId, ordering);
				
				AliasType aliasType = new AliasType(alias, resultType);
				
				aliasTypes.add(aliasType);
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
		return aliasTypes;
	}
}
