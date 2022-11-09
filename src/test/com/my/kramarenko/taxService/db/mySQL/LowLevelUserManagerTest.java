package com.my.kramarenko.taxService.db.mySQL;

import com.my.kramarenko.taxService.db.entity.User;
import com.my.kramarenko.taxService.db.entity.UserDetails;
import com.my.kramarenko.taxService.db.enums.LegalType;
import com.my.kramarenko.taxService.db.enums.Role;
import com.my.kramarenko.taxService.web.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LowLevelUserManagerTest {

    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/taxservicedb?user=user&password=user";

    private static Connection con;

    @BeforeEach
    void setUp() throws SQLException {
        con = DriverManager.getConnection(CONNECTION_URL);
        con.setAutoCommit(false);
    }

    @Test
    void generalUserTests() throws SQLException {
        try {
            User user = generateUser();

            UserManager.addUser(con, user);
            User user1 = UserManager.getUser(con, user.getId()).get();
            assertEquals(user, user1);

            List<User> users = UserManager.getAllUsers(con);
            assertTrue(users.contains(user));

            UserManager.deleteUser(con, user.getId());
            users = UserManager.getAllUsers(con);
            assertFalse(users.contains(user));
            con.commit();
        } catch (SQLException e) {
            con.rollback();
            e.printStackTrace();
        } finally {
            con.close();
        }
    }

    @Test
    void getUserTest() throws SQLException {
        try {
            User user = generateUser();

            UserManager.addUser(con, user);
            User user1 = UserManager.getUser(con, user.getId()).get();
            assertEquals(user, user1);

            UserManager.deleteUser(con, user.getId());
            assertTrue(UserManager.getUser(con, user.getId()).isEmpty());

            con.commit();
        } catch (SQLException e) {
            con.rollback();
            e.printStackTrace();
        } finally {
            con.close();
        }
    }

    @Test
    void updateUserTest() throws SQLException {
        try {
            User user = generateUser();

            UserManager.addUser(con, user);
            user.setCode("00000000");
            UserManager.updateUser(con, user);
            User user1 = UserManager.getUser(con, user.getId()).get();
            assertEquals(user.getCode(), user1.getCode());
            UserManager.deleteUser(con, user.getId());

            con.commit();
        } catch (SQLException e) {
            con.rollback();
            e.printStackTrace();
        } finally {
            con.close();
        }
    }

    @Test
    void updateUserRoleTest() throws SQLException {
        try {
            User user = generateUser();

            UserManager.addUser(con, user);
            UserManager.updateUserRole(con, user.getId(), Role.INSPECTOR.getId());

            User user1 = UserManager.getUser(con, user.getId()).get();
            assertEquals(Role.INSPECTOR.getId(), user1.getRoleId());

            UserManager.deleteUser(con, user.getId());

            con.commit();
        } catch (SQLException e) {
            con.rollback();
            e.printStackTrace();
        } finally {
            con.close();
        }
    }

    @Test
    void setBannedUserTest() throws SQLException {
        try {
            User user = generateUser();

            UserManager.addUser(con, user);
            UserManager.setBanned(con, user.getId(), true);

            User user1 = UserManager.getUser(con, user.getId()).get();
            assertTrue(user1.isBanned());

            UserManager.deleteUser(con, user.getId());

            con.commit();
        } catch (SQLException e) {
            con.rollback();
            e.printStackTrace();
        } finally {
            con.close();
        }
    }

    @Test
    void FindByEmailUserTest() throws SQLException {
        try {
            User user = generateUser();

            UserManager.addUser(con, user);

            User user1 = UserManager.findUserByEmail(con, user.getEmail()).get();
            assertEquals(user, user1);

            UserManager.deleteUser(con, user.getId());

            con.commit();
        } catch (SQLException e) {
            con.rollback();
            e.printStackTrace();
        } finally {
            con.close();
        }
    }

    @Test
    void FindByPartNameUserTest() throws SQLException {
        try {
            User user = generateUser();

            UserManager.addUser(con, user);

            User user1 = UserManager.getAllUsersThatContainString(con, user.getCompanyName()).get(0);
            assertEquals(user, user1);
            UserManager.deleteUser(con, user.getId());

            con.commit();
        } catch (SQLException e) {
            con.rollback();
            e.printStackTrace();
        } finally {
            con.close();
        }
    }


    public static User generateUser() {
        User user = new User();
        user.setLegalType(LegalType.Physical.getId());
        user.setRoleId(Role.USER.getId());
        user.setEmail("testemail@test.com");
        user.setBanned(false);
        user.setPassword("password");
        user.setCode("123456789");
        UserDetails userDetails = generateUserDetails();
        user.setCompanyName(Util.createCompanyName(userDetails));
        return user;
    }

    public static UserDetails generateUserDetails() {
        UserDetails userDetails = new UserDetails();
        userDetails.setPhone("0987654321");
        userDetails.setFirstName("First name");
        userDetails.setLastName("Last name");
        userDetails.setPatronymic("Patronymic");
        return userDetails;
    }
}