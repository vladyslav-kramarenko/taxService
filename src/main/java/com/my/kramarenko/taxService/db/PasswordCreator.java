package com.my.kramarenko.taxService.db;

import org.apache.commons.codec.digest.DigestUtils;

public class PasswordCreator {

    public static String getPassword(String password) {
        return DigestUtils.sha256Hex(password);
    }

    public static void main(String[] args) {
        System.out.println(getPassword("root"));
        System.out.println(getPassword("inspector"));
        System.out.println(getPassword("user@user.com"));
    }
}
