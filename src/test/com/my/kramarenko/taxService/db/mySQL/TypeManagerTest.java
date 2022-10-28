package com.my.kramarenko.taxService.db.mySQL;

import com.my.kramarenko.taxService.db.entity.Type;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TypeManagerTest {
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/taxservicedb?user=user&password=user";
    private static Connection con;

    @Test
    void typeTests() throws SQLException {
        try {
            con = DriverManager.getConnection(CONNECTION_URL);
            List<Type> types = TypeManager.getAllTypes(con);
            assertTrue(types.size() > 0);

            Type type1 = types.get(0);
            Type type2 = TypeManager.getType(con, type1.getId());
            assertEquals(type1, type2);
        } finally {
            con.close();
        }
    }
}