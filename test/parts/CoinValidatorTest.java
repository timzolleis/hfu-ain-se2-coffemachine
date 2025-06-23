package parts;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoinValidatorTest {

    private CoinValidator coinValidator;

    @BeforeEach
    void setUp() {
        coinValidator = new CoinValidator();
    }

    @Test
    void testValidateAcceptedCoinsFiftyEuroCents() {
        assertTrue(coinValidator.validateCoin(0.50));
    }

    @Test
    void testValidateAcceptedCoinsOneEuro() {
        assertTrue(coinValidator.validateCoin(1.00));
    }

    @Test
    void testValidateAcceptedCoinsTwoEuro() {
        assertTrue(coinValidator.validateCoin(2.00));
    }

    @Test
    void testValidateRejectsInvalidCoins() {
        assertFalse(coinValidator.validateCoin(0.25));
        assertFalse(coinValidator.validateCoin(0.10));
        assertFalse(coinValidator.validateCoin(0.05));
        assertFalse(coinValidator.validateCoin(0.01));
        assertFalse(coinValidator.validateCoin(0.02));
        assertFalse(coinValidator.validateCoin(5.00));
        assertFalse(coinValidator.validateCoin(10.00));
    }

    @Test
    void testValidateRejectsZero() {
        assertFalse(coinValidator.validateCoin(0.0));
    }

    @Test
    void testValidateRejectsNegativeValues() {
        assertFalse(coinValidator.validateCoin(-0.50));
        assertFalse(coinValidator.validateCoin(-1.00));
    }

    @Test
    void testValidateHandlesFloatingPointPrecision() {
        assertTrue(coinValidator.validateCoin(0.499));
        assertTrue(coinValidator.validateCoin(0.501));
        assertTrue(coinValidator.validateCoin(0.999));
        assertTrue(coinValidator.validateCoin(1.001));
        assertTrue(coinValidator.validateCoin(1.999));
        assertTrue(coinValidator.validateCoin(2.001));
    }

    @Test
    void testValidateRejectsCoinsOutsideToleranceRange() {
        assertFalse(coinValidator.validateCoin(0.48));
        assertFalse(coinValidator.validateCoin(0.52));
        assertFalse(coinValidator.validateCoin(0.98));
        assertFalse(coinValidator.validateCoin(1.02));
        assertFalse(coinValidator.validateCoin(1.98));
        assertFalse(coinValidator.validateCoin(2.02));
    }

    @Test
    void testCalculateTotalWithValidCoins() {
        double[] validCoins = {0.50, 1.00, 2.00, 1.00, 0.50};
        double total = coinValidator.calculateTotal(validCoins);
        assertEquals(5.00, total, 0.01);
    }

    @Test
    void testCalculateTotalWithMixedValidAndInvalidCoins() {
        double[] mixedCoins = {0.50, 0.25, 1.00, 0.10, 2.00};
        double total = coinValidator.calculateTotal(mixedCoins);
        assertEquals(3.50, total, 0.01);
    }

    @Test
    void testCalculateTotalWithOnlyInvalidCoins() {
        double[] invalidCoins = {0.25, 0.10, 0.05, 0.01};
        double total = coinValidator.calculateTotal(invalidCoins);
        assertEquals(0.0, total, 0.01);
    }

    @Test
    void testCalculateTotalWithEmptyArray() {
        double[] emptyCoins = {};
        double total = coinValidator.calculateTotal(emptyCoins);
        assertEquals(0.0, total, 0.01);
    }

    @Test
    void testCalculateTotalWithSingleValidCoin() {
        double[] singleCoin = {2.00};
        double total = coinValidator.calculateTotal(singleCoin);
        assertEquals(2.00, total, 0.01);
    }

    @Test
    void testCalculateTotalWithSingleInvalidCoin() {
        double[] singleInvalidCoin = {0.20};
        double total = coinValidator.calculateTotal(singleInvalidCoin);
        assertEquals(0.0, total, 0.01);
    }

    @Test
    void testCalculateTotalWithDuplicateValidCoins() {
        double[] duplicateCoins = {1.00, 1.00, 1.00, 1.00};
        double total = coinValidator.calculateTotal(duplicateCoins);
        assertEquals(4.00, total, 0.01);
    }

    @Test
    void testCalculateTotalWithNegativeValues() {
        double[] negativeCoins = {-0.50, 1.00, -2.00};
        double total = coinValidator.calculateTotal(negativeCoins);
        assertEquals(1.00, total, 0.01);
    }
}