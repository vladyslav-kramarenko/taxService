package com.my.kramarenko.taxService.db.enums;

import com.my.kramarenko.taxService.db.entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    @Test
    void getRole() {
        User user = new User();
        user.setRoleId(1);
        assertEquals(Role.getRole(1), Role.getRole(user));
    }


    @Test
    void getName() {
        assertEquals("inspector", Role.INSPECTOR.getName());
    }

    @Test
    void getId() {
        assertEquals(1, Role.INSPECTOR.getId());
        assertNotEquals(Role.INSPECTOR.getId(),2);
    }

    @Test
    void values() {
        Role[] roles = {Role.INSPECTOR, Role.USER, Role.ADMIN};
        assertEquals(roles.length, Role.values().length);
        assertEquals(roles[0], Role.values()[0]);
        assertEquals(roles[1], Role.values()[1]);
        assertEquals(roles[2], Role.values()[2]);
    }

    @Test
    void valueOf() {
        String role = "INSPECTOR";
        assertEquals(Role.INSPECTOR, Role.valueOf(role));
    }
}