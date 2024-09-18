package com.eztrip.global.config;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableEncryptableProperties
public class JasyptConfig {

    @Value("${jasypt.encryptor.password}")
    private String password;

    @Bean
    public PooledPBEStringEncryptor jasyptStringEncryptor(){

        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();

//         SimpleStringPBEConfig config = new SimpleStringPBEConfig();

        encryptor.setPoolSize(4);
        encryptor.setPassword(password);
        encryptor.setAlgorithm("PBEWithMD5AndTripleDES"); // 암호화 알고리즘 설정



        System.out.println(encryptor.toString());

        return encryptor;
    }
}
