package com.fakenews.security;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

public class RSA {
    private BigInteger modulus;
    private BigInteger encExp;
    private BigInteger decExp;
    private BigInteger p, q;

    public RSA() {
        RSAPrivateKeySpec privKey = KeyStorage.readPrivateKey();
        RSAPublicKeySpec pubKey = KeyStorage.readPublicKey();

        if(privKey == null || pubKey == null) {
            this.generateKeys();
            KeyStorage.StoreKeys(modulus, encExp, decExp);
        } else {
            this.modulus = pubKey.getModulus();
            this.encExp = pubKey.getPublicExponent();
            this.decExp = privKey.getPrivateExponent();
        }
    }

    public RSA(BigInteger modulus, BigInteger encExp, BigInteger decExp) {
        this.modulus = modulus;
        this.encExp = encExp;
        this.decExp = decExp;
    }

    private void generateKeys() {
        SecureRandom rand = new SecureRandom();

        int bitLength = 1024;
        BigInteger ppp = new BigInteger(bitLength / 2, 100, rand);
        BigInteger qqq = new BigInteger(bitLength / 2, 100, rand);

        BigInteger pp = ppp;
        BigInteger qq = qqq;

        /// Generarea numerelor mari 'p' si 'q' pana indeplinesc conditiile
        while (qq.compareTo(pp) >= 0 || pp.compareTo(qq.multiply(BigInteger.ONE.add(BigInteger.ONE))) >= 0) {
            ppp = new BigInteger(bitLength / 2, 100, rand);
            qqq = new BigInteger(bitLength / 2, 100, rand);
            pp = ppp;
            qq = qqq;

            if (qq.compareTo(pp) >= 0) {
                BigInteger temp = pp;
                pp = qq;
                qq = temp;
            }
        }

        /// Aflarea lui 'n' si a lui 'phi'
        this.modulus = pp.multiply(qq);
        BigInteger phi = (pp.subtract(BigInteger.ONE)).multiply(qq.subtract(BigInteger.ONE));

        /// Generarea exponentului de criptare 'e' pentru a indeplini conditiile
        do {
            this.encExp = new BigInteger(phi.bitLength(), rand);
        }
        while (phi.gcd(this.encExp).intValue() > 1);

        this.p = pp;
        this.q = qq;
        /// Aflarea exponentului de decriptare 'd'
        this.decExp = this.encExp.modInverse(phi);
    }

    /// Criptarea stringului cu exponentul 'e' (parte din cheia publica)
    public String encryptString(String plainText) {
        BigInteger temp = new BigInteger(plainText.getBytes());
        temp = temp.modPow(this.encExp, this.modulus);
        return temp.toString();
    }

    /// Decriptarea stringului cu exponentul de decriptare 'd' (cheia privata)
    public String decryptString(String crypText) {
        BigInteger temp = new BigInteger(crypText);
        temp = temp.modPow(this.decExp, this.modulus);
        return new String(temp.toByteArray());
    }
}
