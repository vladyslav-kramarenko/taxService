package com.my.kramarenko.taxService.db.enums;

import java.io.Serializable;

public enum LegalType implements Serializable {
    Physical(1), Company(2);
    final int id;

    LegalType(int id) {
        this.id = id;
    }

    public String getName() {
        return name().toLowerCase();
    }

    public int getId() {
        return id;
    }
}
