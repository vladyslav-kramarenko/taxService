package com.my.kramarenko.taxService.db.entity;

import org.apache.commons.codec.digest.DigestUtils;

public class PasswordCreator {
    public static String getPassword(String pasword) {
        return DigestUtils.sha256Hex(pasword);
    }

    public static void main(String[] args) {
//        System.out.println(getPassword("admin@admin.com", "root"));
//        System.out.println(getPassword("user@user.com", "user@user.com"));
        System.out.println(getPassword("root"));
        System.out.println(getPassword("user@user.com"));
    }
}
