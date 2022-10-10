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
 */
public final class DBManager {
    private final String dbName = "taxService";
    private final DataSource ds;
    private final ReportManager reportManager;
    private final StatusManager statusManager;
    private final TypeManager typeManager;

    private final UserManager userManager;
    public ReportManager getReportManager() {
        return reportManager;
    }

    public StatusManager getStatusManager() {
        return statusManager;
    }

    public TypeManager getTypeManager() {
        return typeManager;
    }

    public UserManager getUserManager() {
        return userManager;
    }


    private DBManager() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            ds = (DataSource) envContext.lookup("jdbc/" + dbName);
            reportManager = new ReportManager();
            statusManager = new StatusManager();
            typeManager = new TypeManager();
            userManager = new UserManager();
        } catch (NamingException e) {
            throw new IllegalStateException("Cannot obtain a datasource", e);
        }
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
     * Returns a DB connection from the Pool Connections.
     *
     * @return A DB connection.
     */
    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    /**
     * Rollbacks the given connection.
     *
     * @param con Connection to rollback.
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
     * @param con Connection to be committed and closed.
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
     * @param rs ResultSet
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
     * @param stmt statement
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
}
