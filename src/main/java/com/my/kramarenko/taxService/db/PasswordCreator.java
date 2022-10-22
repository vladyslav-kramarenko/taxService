package com.my.kramarenko.taxService.db;

import org.apache.commons.codec.digest.DigestUtils;

public class PasswordCreator {

    public static String getPassword(String password) {
        if (password == null || password.length() == 0) return "";
        return DigestUtils.sha256Hex(password);
    }
}
