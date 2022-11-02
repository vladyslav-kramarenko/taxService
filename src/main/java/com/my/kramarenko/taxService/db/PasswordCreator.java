package com.my.kramarenko.taxService.db;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

public class PasswordCreator {
    public static String generatePassword() {
        // Creates a 32 chars length of random ascii string.
//        return RandomStringUtils.randomAscii(10);
        return RandomStringUtils.random(10,"0123456789qazwsxedcrfvtgbyhnujmikolp");
    }
    public static String getPassword(String password) {
        if (password == null || password.length() == 0) return "";
        return DigestUtils.sha256Hex(password);
    }
}
