package com.my.kramarenko.taxService.db.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatusTest {

    @Test
    void getName() {
        assertEquals("draft", Status.DRAFT.getName());
    }

    @Test
    void getId() {
        assertEquals(1, Status.DRAFT.getId());
        assertNotEquals(4,Status.DRAFT.getId());
    }

    @Test
    void values() {
        Status[] statuses = {Status.DRAFT, Status.SENT, Status.ACCEPTED, Status.REFUSED};
        assertEquals(statuses.length, Status.values().length);
        assertEquals(statuses[0], Status.values()[0]);
        assertEquals(statuses[1], Status.values()[1]);
        assertEquals(statuses[2], Status.values()[2]);
        assertEquals(statuses[3], Status.values()[3]);
    }

    @Test
    void valueOf() {
        String status = "DRAFT";
        assertEquals(Status.DRAFT, Status.valueOf(status));
    }
}