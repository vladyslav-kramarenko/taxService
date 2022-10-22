package com.my.kramarenko.taxService.db;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbUtil {
    private static final Logger LOG = Logger.getLogger(DbUtil.class);

    /**
     * Close the given connection.
     *
     * @param connection Connection to be committed and closed.
     */
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOG.error("Can't close the connection");
            }
        }
    }

    /**
     * Close statement
     *
     * @param statement statement
     */
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOG.error("Can't close the statement");
            }
        }
    }

    /**
     * Close resultSet
     *
     * @param resultSet ResultSet
     */
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOG.error("Can't close the resultSet");
            }
        }
    }

    /**
     * Rollbacks the given connection.
     *
     * @param con Connection to rollback.
     */
    public static void rollback(Connection con) {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                LOG.error("Cannot rollback transaction", ex);
            }
        }
    }
}
