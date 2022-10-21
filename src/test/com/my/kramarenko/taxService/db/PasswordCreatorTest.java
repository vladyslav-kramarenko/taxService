package com.my.kramarenko.taxService.db;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordCreatorTest {

    @Test
    void getPasswordTest() {
        assertEquals("4813494d137e1631bba301d5acab6e7bb7aa74ce1185d456565ef51d737677b2", PasswordCreator.getPassword("root"));
        assertEquals("98fe442255035a1459bb5b86fda03d7c34c23d512b1b5bf3a5ecb7a802601895", PasswordCreator.getPassword("inspector"));
        assertEquals("b238c595b84321b35b8e57610c49523d4e3b9b5b5d090923e9e54f4b929bedba", PasswordCreator.getPassword("user@user.com"));
        assertEquals("", PasswordCreator.getPassword(""));
        assertEquals("", PasswordCreator.getPassword(null));
    }
}