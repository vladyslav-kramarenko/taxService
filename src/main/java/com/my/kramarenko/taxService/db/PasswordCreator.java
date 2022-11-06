package com.my.kramarenko.taxService.db;

import com.my.kramarenko.taxService.web.VerifyRecaptcha;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.chainsaw.Main;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.EncryptableProperties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PasswordCreator {
    private static final Logger LOG = Logger.getLogger(PasswordCreator.class);

    public static String generatePassword() {
        // Creates a 32 chars length of random ascii string.
//        return RandomStringUtils.randomAscii(10);
        return RandomStringUtils.random(10, "0123456789qazwsxedcrfvtgbyhnujmikolp");
    }

    public static String getPassword(String password) {
        if (password == null || password.length() == 0) return "";
        return DigestUtils.sha256Hex(password);
    }

    public static String getProperty(String property) throws IOException, NamingException {
        LOG.trace("start command");
        LOG.trace("input property = " + property);
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword("jasypt");
        LOG.trace("encryptor created");
        Properties props = new EncryptableProperties(encryptor);

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

//        props.load(new FileInputStream("/WEB-INF/credentials.properties"));
        props.load(classLoader.getResourceAsStream("credentials.properties"));
        String datasourceProperty = props.getProperty(property);
        LOG.trace("output property = " + datasourceProperty);
        return datasourceProperty;
    }

    public static void main(String[] args) throws IOException {
        /*
         * First, create (or ask some other component for) the adequate encryptor for
         * decrypting the values in our .properties file.
         */
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword("jasypt"); // could be got from web, env variable...
        /*
         * Create our EncryptableProperties object and load it the usual way.
         */
        Properties props = new EncryptableProperties(encryptor);
        props.load(new FileInputStream("/WEB-INF/credentials.properties"));

        /*
         * To get a non-encrypted value, we just get it with getProperty...
         */
        String datasourceUsername = props.getProperty("secret");
        System.out.println(datasourceUsername);

        /*
         * ...and to get an encrypted value, we do exactly the same. Decryption will
         * be transparently performed behind the scenes.
         */
//        String datasourcePassword = props.getProperty("datasource.password");

        // From now on, datasourcePassword equals "reports_passwd"...
    }
}
