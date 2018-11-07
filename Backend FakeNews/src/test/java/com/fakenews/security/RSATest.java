package com.fakenews.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;

import static org.assertj.core.api.Java6Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class RSATest {

    private RSA rsa;

    private String plainText;
    private String cryptoText;

    private BigDecimal modulus;
    private BigDecimal encExp;
    private BigDecimal decExp;

    @BeforeEach
    void setUp() {
        plainText = "plainText";
        cryptoText = "67453265272947814885893218436721676497901200027823161869578662490735819590687840901064782477740542397249296879293779166301347207053382115118269658234789206056497577720097454045048376913641426581042462090069535166540446679166205598360016891528485932265184622934282225243325641489363842934229661919242538133815";

        modulus = new BigDecimal("114148583453528745752158573299299462974849455803660656482072552191881163973185343560593952047234554690302302264823153536655488960387116585505622616432041543078622970397250697451778064891543319594178153797228157167683013081933196126650429380296544727748466019924362570851520110617956047194419951742367399790853");
        encExp = new BigDecimal("65537");
        decExp = new BigDecimal("33298634640959497097666472440545144470347618842095067376051132227663519734491316328050340650915211507533140282571509976254324090890350410938805456169000256520741194286946780602454621083327057643032729590642843725780625377381384749342388096938198107146927109759526548861172195154004923472826081212789832947265");

        rsa = new RSA(modulus, encExp, decExp);
    }

    @Test
    void shouldGetEncryptedTextWhenGivenPlainText() {
        String result = rsa.encryptString(plainText);

        assertThat(result).isEqualToIgnoringCase(cryptoText);
    }

    @Test
    void shouldGetDecryptedTextWhenGivenCryptoText() {
        String result = rsa.decryptString(cryptoText);

        assertThat(result).isEqualToIgnoringCase(plainText);
    }
}
