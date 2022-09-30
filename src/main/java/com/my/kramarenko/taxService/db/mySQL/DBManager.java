package com.my.kramarenko.taxService.db.mySQL;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Create and control database connection
 * 
 * @author Vlad Kramarenko
 *
 */
public final class DBManager {

	private DBManager() {
	}

	private static final Logger LOG = Logger.getLogger(DBManager.class);

	private static DBManager instance;

	/**
	 * Return instance of DBManager
	 * 
	 * @return DBManager object
	 */
	public static synchronized DBManager getInstance() {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}

	/**
	 * Returns a DB connection from the Pool Connections. Before using this
	 * method you must configure the Date Source and the Connections Pool in
	 * your WEB_APP_ROOT/META-INF/context.xml file.
	 * 
	 * @return A DB connection.
	 */
	public Connection getConnection() throws SQLException {
		Connection con = null;
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");

			String dbName = "taxService";

			DataSource ds = (DataSource) envContext.lookup("jdbc/" + dbName);
			con = ds.getConnection();

		} catch (NamingException ex) {
			LOG.error("Cannot obtain a connection from the pool", ex);
		}
		return con;
	}

	/**
	 * Rollbacks the given connection.
	 * 
	 * @param con
	 *            Connection to be rollbacked.
	 */
	void rollback(Connection con) {
		if (con != null) {
			try {
				con.rollback();
			} catch (SQLException ex) {
				LOG.error("Cannot rollback transaction", ex);
			}
		}
	}

	/**
	 * Close the given connection.
	 * 
	 * @param con
	 *            Connection to be committed and closed.
	 */
	void close(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException ex) {
				LOG.error("Cannot commit transaction and close connection", ex);
			}
		}
	}

	/**
	 * Close resultSet
	 * 
	 * @param rs
	 *            ResultSet
	 */
	void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException ex) {
				LOG.error("Cannot close a result set", ex);
			}
		}
	}

	/**
	 * Close statement
	 * 
	 * @param stmt
	 *            statement
	 */
	void close(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException ex) {
				LOG.error("Cannot close a statement", ex);
			}
		}
	}

	/**
	 * Return max id from table with sql
	 * 
	 * @param con
	 *            database connection
	 * @param sql
	 *            sql query
	 * @return max identificator
	 * @throws SQLException
	 */
	public int getMaxId(Connection con, String sql) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		int maxId = -1;
		DBManager dbManager = DBManager.getInstance();
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();
			maxId = rs.getInt("id");
			con.commit();
			LOG.trace("Obtain max id = " + maxId);
		} finally {
			dbManager.close(rs);
			dbManager.close(stmt);
		}
		return maxId;
	}

	public static void main(String[] args) throws SQLException {
		DBManager manager=new DBManager();
		Connection con=manager.getConnection();
		System.out.println(manager.getMaxId(con,"SELECT * FROM user"));
	}

}
