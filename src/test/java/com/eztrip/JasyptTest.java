package com.eztrip;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.junit.jupiter.api.Test;

public class JasyptTest {

    @Test
    public void jasyptTest(){

        /*
        Password + Encoding Algorithm + content -> 아예 다른 String
         */
        String password = "mySuperSecretPassword123!";

        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setPoolSize(4);
        encryptor.setPassword(password);
        encryptor.setAlgorithm("PBEWithMD5AndTripleDES");

        String content = "";
        String encryptedContent = encryptor.encrypt(content);
        String decryptedContent = encryptor.decrypt(encryptedContent);

        System.out.println("Enc : " + encryptedContent + ", Dec : " + decryptedContent);
    }
}