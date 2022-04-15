package movierank.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import movierank.model.*;
import movierank.utils.ConnectionManager;

public class AliasAttributesDao {

protected ConnectionManager connectionManager;
	
	private static AliasAttributesDao instance = null;
	protected AliasAttributesDao() {
		connectionManager = new ConnectionManager();
	}
	public static AliasAttributesDao getInstance() {
		if(instance == null) {
			instance = new AliasAttributesDao();
		}
		return instance;
	}
	
	public AliasAttributes create(AliasAttributes aliasAttributes) throws SQLException {
		String insertAliasAttributes = "INSERT INTO AliasAttributes(title_id,ordering,Attribute) VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertAliasAttributes);

			insertStmt.setString(1, aliasAttributes.getAlias().getMovie().getTitle_id());
			insertStmt.setInt(2, aliasAttributes.getAlias().getOrdering());
			insertStmt.setString(3, aliasAttributes.getAttribute());

			insertStmt.executeUpdate();

			return aliasAttributes;
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
	
	public AliasAttributes updateAttribute(AliasAttributes aliasAttributes, String newAttribute) throws SQLException {
		String updateAliasAttributes = "UPDATE AliasAttributes SET Attribute=? WHERE title_id=? And ordering=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateAliasAttributes);
			updateStmt.setString(1, newAttribute);
			updateStmt.setString(2, aliasAttributes.getAlias().getMovie().getTitle_id());
			updateStmt.setInt(3, aliasAttributes.getAlias().getOrdering());
			updateStmt.executeUpdate();
			
			aliasAttributes.setAttribute("newAttribute");
			return aliasAttributes;
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
	
	public AliasAttributes delete(AliasAttributes aliasAttributes) throws SQLException {
		String deleteAliasAttributes = "DELETE FROM AliasAttributes WHERE title_id=? And ordering=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteAliasAttributes);
			deleteStmt.setString(1, aliasAttributes.getAlias().getMovie().getTitle_id());
			deleteStmt.setInt(2, aliasAttributes.getAlias().getOrdering());
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
	
	public AliasAttributes getAliasAttributesFromTitleIdAndOrdering(String titleId, int ordering) throws SQLException {
		String selectAliasAttributes = "SELECT title_id,ordering,Attribute FROM AliasAttributes WHERE title_id=? And ordering=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectAliasAttributes);
			selectStmt.setString(1, titleId);
			selectStmt.setInt(2, ordering);
			AliasDao aliasDao = AliasDao.getInstance();

			results = selectStmt.executeQuery();

			if(results.next()) {
				String resultTitleId = results.getString("title_id");
				int resultOrdering = results.getInt("ordering");
				String attribute = results.getString("Attribute");
				Alias alias = aliasDao.getAliasFromTitleIdAndOrdering(resultTitleId, resultOrdering);
				AliasAttributes aliasAttributes = new AliasAttributes(alias, attribute);
				return aliasAttributes;
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
	
	public List<AliasAttributes> getAliasAttributesFromAttribute(String Attribute) throws SQLException {
		List<AliasAttributes> aliasAttributes = new ArrayList<AliasAttributes>();
		String selectAliasAttributes = "SELECT title_id,ordering,Attribute"
				+ " FROM AliasAttributes"
				+ " WHERE Attribute=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectAliasAttributes);
			selectStmt.setString(1, Attribute);
			results = selectStmt.executeQuery();
			AliasDao aliasDao = AliasDao.getInstance();
			
			while(results.next()) {
				String titleId = results.getString("title_id");
				int ordering = results.getInt("ordering");
				String attribute = results.getString("Attribute");
				Alias alias = aliasDao.getAliasFromTitleIdAndOrdering(titleId, ordering);
				
				AliasAttributes aliasAttribute = new AliasAttributes(alias, attribute);
				
				aliasAttributes.add(aliasAttribute);
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
		return aliasAttributes;
	}
}
