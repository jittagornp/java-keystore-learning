/*
 * Copyright 2020-Current jittagornp.me
 */
package me.jittagornp.learning.java.keystore.learning;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author jitta
 */
public class JavaKeyStoreLearning {

    public static void main(String[] args) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableEntryException {

        final String KEY_STORE_FILE = "/keystore/auth.keystore";
        final String KEY_STORE_PASSWORD = "TZxuW3Kr7pAJw9pf";
        final String ALIAS = "OAUTH_SECRET_KEY";

        //1. create and load keystore from file
        final KeyStore keyStore = loadKeyStore(KEY_STORE_FILE, KEY_STORE_PASSWORD);

        //2. get entry from keystore
        final KeyStore.ProtectionParameter parameter = new KeyStore.PasswordProtection(KEY_STORE_PASSWORD.toCharArray());
        KeyStore.Entry entry = keyStore.getEntry(ALIAS, parameter);

        System.out.println("entry = " + entry);

        //3. set entry to keystore
        if (entry == null) {
            entry = newEntry();
            keyStore.setEntry(ALIAS, entry, parameter);
        }

        //4. save keystore to file
        try ( OutputStream outputStream = new FileOutputStream(KEY_STORE_FILE)) {
            keyStore.store(outputStream, KEY_STORE_PASSWORD.toCharArray());
        }

    }

    private static KeyStore loadKeyStore(final String keyStoreFile, final String keyStorePassword) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
        final KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        try ( InputStream inputStream = new FileInputStream(keyStoreFile)) {
            keyStore.load(inputStream, keyStorePassword.toCharArray());
        } catch (FileNotFoundException e) {
            //create empty keystore, if file not found
            keyStore.load(null, null);
        }
        return keyStore;
    }

    private static KeyStore.Entry newEntry() {
        final SecretKey secretKey = new SecretKeySpec("4kx2v9P5vUFYFTbV".getBytes(), "AES");
        return new KeyStore.SecretKeyEntry(secretKey);
    }

}
