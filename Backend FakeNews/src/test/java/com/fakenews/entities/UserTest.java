package com.fakenews.entities;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserTest {

    @Test
    void shouldGetTrueWithLowerThanPoint5WhenCallFunctionIsFake() {
        // Arrange
        User t = new User();
        t.setTrustDegree(0.1);

        // Act
        boolean result = t.isFake();

        // Assert
        assertThat(true).isEqualTo(result);
    }

    @Test
    void shouldGetTrueWithHigherThanPoint5WhenCallFunctionIsTrue() {
        // Arrange
        User t = new User();
        t.setTrustDegree(0.8);

        // Act
        boolean result = t.isTrue();

        // Assert
        assertThat(true).isEqualTo(result);
    }

}
