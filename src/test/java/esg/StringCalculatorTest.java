package esg;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringCalculatorTest {

    public final StringCalculator calculator = new StringCalculator();

    @Test
    void whenEmplyString_shouldReturnZero() throws StringCalculatorException {
        // GIVEN
        var expected = 0;

        // WHEN
        var actual = calculator.add("");

        // THEN
        assertEquals(expected, actual);
    }

    @Test
    void whenStringIsAnInteger_shouldReturnTheInteger() throws StringCalculatorException {
        // GIVEN
        var expected = 1;

        // WHEN
        var actual = calculator.add("1");

        // THEN
        assertEquals(expected, actual);
    }

    @Test
    void whenContainsSeveralIntegers_shouldReturnTheSum() throws StringCalculatorException {
        // GIVEN
        var expected = 795;

        // WHEN
        var actual = calculator.add("3, 5, 99, 256, 111, 321");

        // THEN
        assertEquals(expected, actual);
    }

    @Test
    void add_shouldTrimNewLines() throws StringCalculatorException {
        // GIVEN
        var expected = 6;

        // WHEN
        var actual = calculator.add("1\n2,3");

        // THEN
        assertEquals(expected, actual);
    }

    @Test
    void whenCustomDelimiterIsSpecified_shouldHandleItCorrectly() throws StringCalculatorException {
        // GIVEN
        var expected = 18;

        // WHEN
        var actual = calculator.add("//[*?^]\n 1\n2,3 *?^7*?^  5 *?^");

        // THEN
        assertEquals(expected, actual);
    }

    @Test
    void whenStringContainsNegativeNumbers_shouldThrowException() {
        // GIVEN
        var expected = "Negatives not allowed: -4,-5";

        // WHEN
        StringCalculatorException thrown = Assertions.assertThrows(StringCalculatorException.class, () -> {
            calculator.add("2,-4,3,-5");
        });

        // THEN
        Assertions.assertEquals(expected, thrown.getMessage());
    }

    @Test
    void numbersGreaterThan1000_shouldBeIgnored() throws StringCalculatorException {
        // GIVEN
        var expected = 1008;

        // WHEN
        var actual = calculator.add("3, 5, 1000, 1001");

        // THEN
        assertEquals(expected, actual);
    }

    @Test
    void multipleCustomDelimiters_shouldBeHandledCorrectly() throws StringCalculatorException {
        // GIVEN
        var expected = 18;

        // WHEN
        var actual = calculator.add("//[~|!][$%^]\n 1\n2,3 ~|!7$%^  5 $%^");

        // THEN
        assertEquals(expected, actual);
    }
}
