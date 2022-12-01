package com.my.kramarenko.taxService.db;

import com.my.kramarenko.taxService.db.dao.ReportDAO;
import com.my.kramarenko.taxService.db.dao.TypeDAO;
import com.my.kramarenko.taxService.db.dao.UserDAO;
import com.my.kramarenko.taxService.db.mySQL.dao.ReportDAOMySQL;
import com.my.kramarenko.taxService.db.mySQL.dao.TypeDAOMySQL;
import com.my.kramarenko.taxService.db.mySQL.dao.UserDAOMySQL;
import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Create and control database connection
 *
 * @author Vlad Kramarenko
 */
public final class DBManager {
    private final ReportDAO reportManager;
    private final TypeDAO typeManager;
    private final UserDAO userManager;

    public ReportDAO getReportDAO() {
        return reportManager;
    }

    public TypeDAO getTypeDAO() {
        return typeManager;
    }

    public UserDAO getUserDAO() {
        return userManager;
    }

//    @Resource(name="jdbc/testDB")
//    DataSource ds;

    private DBManager() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/taxService");
            reportManager = new ReportDAOMySQL(ds);
            typeManager = new TypeDAOMySQL(ds);
            userManager = new UserDAOMySQL(ds);
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
}
