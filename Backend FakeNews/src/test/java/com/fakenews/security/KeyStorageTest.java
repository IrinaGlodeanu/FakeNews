package com.fakenews.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigInteger;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import static org.assertj.core.api.Java6Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class KeyStorageTest {
    private RSA rsa;

    private BigInteger modulus;
    private BigInteger encExp;
    private BigInteger decExp;

    @BeforeEach
    void setUp() {
        rsa = new RSA();

        RSAPublicKeySpec pub = KeyStorage.readPublicKey();
        RSAPrivateKeySpec priv = KeyStorage.readPrivateKey();

        modulus = pub.getModulus();
        encExp = pub.getPublicExponent();
        decExp = priv.getPrivateExponent();
    }

    @Test
    void shouldGetSamePublicKeyWhenHavingNewRSAInstance() {
        rsa = new RSA();
        RSAPublicKeySpec result = KeyStorage.readPublicKey();

        assertThat(result.getModulus()).isEqualTo(modulus);
        assertThat(result.getPublicExponent()).isEqualTo(encExp);
    }

    @Test
    void shouldGetSamePrivateKeyWhenHavingNewRSAInstance() {
        rsa = new RSA();
        RSAPrivateKeySpec result = KeyStorage.readPrivateKey();

        assertThat(result.getModulus()).isEqualTo(modulus);
        assertThat(result.getPrivateExponent()).isEqualTo(decExp);
    }
}
