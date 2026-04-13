package com.raven8472.stardate;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StardateCalculatorTest {

    private final StardateCalculator calculator = new StardateCalculator();

    @Test
    void januaryFirstStartsAtWholeThousandBoundary() {
        assertEquals("0.00", calculator.format(LocalDate.of(2323, 1, 1)));
    }

    @Test
    void leapYearUsesThreeHundredSixtySixDays() {
        assertEquals("2160.94", calculator.format(LocalDate.of(2325, 3, 20)));
    }

    @Test
    void endOfYearApproachesNextBoundaryWithoutCrossing() {
        assertEquals("997.26", calculator.format(LocalDate.of(2323, 12, 31)));
    }

    @Test
    void modernDateProducesExpectedNegativeOffset() {
        assertEquals("-296728.77", calculator.format(LocalDate.of(2026, 4, 13)));
    }

    @Test
    void reverseConversionRecoversTheOriginalDateFromRoundedStardate() {
        assertEquals(LocalDate.of(2323, 12, 31), calculator.reverse(997.26));
    }

    @Test
    void reverseConversionWorksForNegativeModernEraValues() {
        assertEquals(LocalDate.of(2026, 4, 13), calculator.reverse(-296728.77));
    }
}
