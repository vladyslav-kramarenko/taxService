package com.my.kramarenko.taxService.db.mySQL.dao;

import com.my.kramarenko.taxService.db.dao.TypeDAO;
import com.my.kramarenko.taxService.db.mySQL.TypeManager;
import com.my.kramarenko.taxService.exception.DBException;
import com.my.kramarenko.taxService.db.entity.Type;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class TypeDAOMySQL implements TypeDAO {
    private static final Logger LOG = Logger.getLogger(TypeDAOMySQL.class);
    DataSource ds;

    public TypeDAOMySQL(DataSource ds) {
        this.ds = ds;
    }

    public List<Type> getAllTypes() throws DBException {
        try (Connection con = ds.getConnection()) {
            con.setAutoCommit(true);
            return TypeManager.getAllTypes(con);
        } catch (SQLException e) {
            LOG.error(e.getMessage(),e);
            throw new DBException("Cannot obtain all types", e);
        }
    }
//
//    public Type getType(String id) throws DBException {
//        try (Connection con = ds.getConnection()) {
//            con.setAutoCommit(true);
//            return TypeManager.getType(con, id);
//        } catch (SQLException e) {
//            LOG.error(e.getMessage());
//            throw new DBException("Cannot obtain the type with id " + id, e);
//        }
//    }
}
