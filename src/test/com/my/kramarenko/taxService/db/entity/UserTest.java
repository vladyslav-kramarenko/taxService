package com.my.kramarenko.taxService.db.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void IndividualTest() {
        User user = new User();
        user.setIndividual("true");
        assertTrue(user.isIndividual());
        user.setIndividual(0);
        assertFalse(user.isIndividual());
        user.setIndividual(true);
        assertTrue(user.isIndividual());
    }


    @Test
    void testEquals() {
        User user1 = new User();
        user1.setEmail("email");

        User user2 = user1;

        assertEquals(user1, user2);

        user1.setId(0);

        user2 = new User();
        user2.setEmail("email");
        user2.setId(1);

        assertNotEquals(user1.hashCode(),user2.hashCode());
        assertNotEquals(user1,user2);

        user2.setId(0);
        assertEquals(user1, user2);
    }
}