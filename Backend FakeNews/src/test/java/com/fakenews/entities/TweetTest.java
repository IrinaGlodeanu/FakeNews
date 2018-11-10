package com.fakenews.entities;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TweetTest {

    @Test
    void shouldGetTrueWithLowerThanPoint5WhenCallFunctionIsFake() {
        // Arrange
        Tweet t = new Tweet();
        t.setTrustDegree(0.1);

        // Act
        boolean result = t.isFake();

        // Assert
        assertThat(true).isEqualTo(result);
    }

    @Test
    void shouldGetTrueWithHigherThanPoint5WhenCallFunctionIsTrue() {
        // Arrange
        Tweet t = new Tweet();
        t.setTrustDegree(0.8);

        // Act
        boolean result = t.isTrue();

        // Assert
        assertThat(true).isEqualTo(result);
    }

}
