package com.my.kramarenko.taxService.db.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TypeTest {

    @Test
    void typeTests() {
        Type type=new Type();
        String name="name";
        type.setName(name);
        assertEquals(name,type.getName());

        String id="1";
        type.setId(id);
        assertEquals(id,type.getId());
    }
}