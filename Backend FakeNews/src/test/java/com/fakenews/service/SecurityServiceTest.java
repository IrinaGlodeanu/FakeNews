package com.fakenews.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.assertj.core.api.Java6Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class SecurityServiceTest {

    private SecurityService service;

    private String plainText;
    private String cryptoText;

    @BeforeEach
    void setUp() {
        plainText = "plainText";
        cryptoText = "71757980407228734952628945156264125882517500216769494656704356433490618094932635171370217992009374301230142791433702674057558640731439489954832581046850457842956057213017839950841270000706331235644955148438012332028858385654914417533058335854308312003456747124904865405912064578627507046604412444277808100945";

        service = new SecurityService();
    }

    @Test
    void shouldGetEncryptedTextWhenGivenPlainText() {
        String result = service.encryptString(plainText);

        assertThat(result).isNotEqualToIgnoringCase(plainText);
    }

    @Test
    void shouldGetDecryptedTextWhenGivingCryptoText() {
        String result = service.decryptString(cryptoText);

        assertThat(result).isNotEqualToIgnoringCase(cryptoText);
    }

}
