package com.my.kramarenko.taxService.db.enums;


import com.my.kramarenko.taxService.db.entity.User;

import java.io.Serializable;
import java.util.Arrays;

public enum Role implements Serializable {
    INSPECTOR(1), USER(2), ADMIN(3);
    final int id;

    Role(int id) {
        this.id = id;
    }

    public static Role getRole(User user) {
        int roleId = user.getRoleId();
        return Arrays.stream(Role.values())
                .filter(c -> c.getId() == roleId)
                .findFirst().get();
    }

    public String getName() {
        return name().toLowerCase();
    }

    public int getId() {
        return id;
    }

}
