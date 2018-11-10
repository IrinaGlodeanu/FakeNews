package com.fakenews.security;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;

public class RSA {
    private BigDecimal modulus;
    private BigDecimal encExp;
    private BigDecimal decExp;
    private BigDecimal p, q;

    public RSA() {
        this.generateKeys();
    }

    public RSA(BigDecimal modulus, BigDecimal encExp, BigDecimal decExp) {
        this.modulus = modulus;
        this.encExp = encExp;
        this.decExp = decExp;
    }

    private void generateKeys() {
        SecureRandom rand = new SecureRandom();

        int bitLength = 1024;
        BigInteger ppp = new BigInteger(bitLength / 2, 100, rand);
        BigInteger qqq = new BigInteger(bitLength / 2, 100, rand);

        BigDecimal pp = new BigDecimal(ppp);
        BigDecimal qq = new BigDecimal(qqq);

        /// Generarea numerelor mari 'p' si 'q' pana indeplinesc conditiile
        while (qq.compareTo(pp) >= 0 || pp.compareTo(qq.multiply(BigDecimal.ONE.add(BigDecimal.ONE))) >= 0) {
            ppp = new BigInteger(bitLength / 2, 100, rand);
            qqq = new BigInteger(bitLength / 2, 100, rand);
            pp = new BigDecimal(ppp);
            qq = new BigDecimal(qqq);

            if (qq.compareTo(pp) >= 0) {
                BigDecimal temp = pp;
                pp = qq;
                qq = temp;
            }
        }

        /// Aflarea lui 'n' si a lui 'phi'
        this.modulus = pp.multiply(qq);
        BigDecimal phi = (pp.subtract(BigDecimal.ONE)).multiply(qq.subtract(BigDecimal.ONE));

        /// Generarea exponentului de criptare 'e' pentru a indeplini conditiile
        do {
            BigInteger enc = new BigInteger(phi.toBigInteger().bitLength(), rand);
            this.encExp = new BigDecimal(enc);
        }
        while (phi.toBigInteger().gcd(this.encExp.toBigInteger()).intValue() > 1);

        this.p = pp;
        this.q = qq;
        /// Aflarea exponentului de decriptare 'd'
        this.decExp = new BigDecimal(this.encExp.toBigInteger().modInverse(phi.toBigInteger()));
    }

    /// Criptarea stringului cu exponentul 'e' (parte din cheia publica)
    public String encryptString(String plainText) {
        BigInteger temp = new BigInteger(plainText.getBytes());
        BigDecimal temporary = new BigDecimal(temp);
        temporary = new BigDecimal(temporary.toBigInteger().modPow(this.encExp.toBigInteger(), this.modulus.toBigInteger()));
        return temporary.toString();
    }

    /// Decriptarea stringului cu exponentul de decriptare 'd' (cheia privata)
    public String decryptString(String crypText) {
        BigDecimal temporary = new BigDecimal(crypText);
        temporary = new BigDecimal(temporary.toBigInteger().modPow(this.decExp.toBigInteger(), this.modulus.toBigInteger()));
        return new String(temporary.toBigInteger().toByteArray());
    }
}
