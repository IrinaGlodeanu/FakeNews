package com.fakenews.service;

import com.fakenews.security.RSA;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    private RSA rsa;

    public SecurityService() {
        this.rsa = new RSA();
    }

    public String encryptString(String plainText) {
        return rsa.encryptString(plainText);
    }

    public String decryptString(String cryptoText) {
        return rsa.decryptString(cryptoText);
    }
}