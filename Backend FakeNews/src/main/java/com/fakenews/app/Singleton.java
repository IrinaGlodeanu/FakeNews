package com.fakenews.app;

import org.springframework.stereotype.Component;

@Component("Singleton")
public class Singleton {
    private static Singleton single_instance = null;

    private String privateKey;

    public String publicKey;

    private Singleton() {
        privateKey = "pkey";
        publicKey = "publicKey";
    }

    public static Singleton getInstance() {
        if (single_instance == null)
            single_instance = new Singleton();

        return single_instance;
    }

    public static String Encrypt(String plainText) {
        return "Encrypted text " + plainText;
    }

    public static String Decrypt(String criptoText) {
        return "Plain text " + criptoText;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
