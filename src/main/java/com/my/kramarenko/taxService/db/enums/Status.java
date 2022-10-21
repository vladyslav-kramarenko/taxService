package com.my.kramarenko.taxService.db.enums;

import java.io.Serializable;
import java.util.Arrays;

public enum Status implements Serializable {
    DRAFT(1), SENT(2), ACCEPTED(3), REFUSED(4);
    final int id;
    final String name;

    Status(int id) {
        this.id = id;
        this.name=this.toString().toLowerCase();
    }

    public static Status getStatus(int id) {
        return Arrays.stream(Status.values())
                .filter(c -> c.getId() == id)
                .findFirst().get();
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
