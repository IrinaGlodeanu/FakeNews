package com.fakenews.security;

import java.io.*;
import java.math.BigInteger;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

public class KeyStorage {

    private static String PUBLIC_KEY_FILE = "\\pubk";
    private static String PRIVATE_KEY_FILE = "\\pk";

    public static void StoreKeys(BigInteger modulus, BigInteger encExp, BigInteger decExp) {
        RSAPrivateKeySpec privateKey = new RSAPrivateKeySpec(modulus, decExp);
        RSAPublicKeySpec publicKey = new RSAPublicKeySpec(modulus, encExp);

        saveToFile(PRIVATE_KEY_FILE, privateKey.getModulus(), privateKey.getPrivateExponent());
        saveToFile(PUBLIC_KEY_FILE, publicKey.getModulus(), publicKey.getPublicExponent());
    }

    private static void saveToFile(String fileName, BigInteger mod, BigInteger exp) {
        try {
            ObjectOutputStream oout = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));

            oout.writeObject(mod);
            oout.writeObject(exp);

            oout.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static RSAPrivateKeySpec readPrivateKey() {
        try {
            InputStream in = new FileInputStream(PRIVATE_KEY_FILE);
            ObjectInputStream oin = new ObjectInputStream(new BufferedInputStream(in));

            BigInteger mod = (BigInteger) oin.readObject();
            BigInteger dec = (BigInteger) oin.readObject();

            return new RSAPrivateKeySpec(mod, dec);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public static RSAPublicKeySpec readPublicKey() {
        try {
            InputStream in = new FileInputStream(PUBLIC_KEY_FILE);
            ObjectInputStream oin = new ObjectInputStream(new BufferedInputStream(in));

            BigInteger mod = (BigInteger) oin.readObject();
            BigInteger enc = (BigInteger) oin.readObject();

            return new RSAPublicKeySpec(mod, enc);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}

