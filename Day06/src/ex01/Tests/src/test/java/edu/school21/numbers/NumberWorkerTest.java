package edu.school21.numbers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class NumberWorkerTest {

    NumberWorker numberWorker;

    @BeforeEach
    void setUp() {
        numberWorker = new NumberWorker();
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 5, 7, 11})
    void isPrimeForPrimes(int number) {
        assertTrue(numberWorker.isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 6, 8, 9, 10})
    void isPrimeForNotPrimes(int number) {
        assertFalse(numberWorker.isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 1})
    void isPrimeForIncorrectNumbers(int number) {
        assertThrows(IllegalNumberException.class, new Executable() {
            @Override
            public void execute() {
                numberWorker.isPrime(number);
            }
        });
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv")
    void digitsSum(int number, int expectedSum) {
        assertEquals(expectedSum, numberWorker.digitsSum(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {908070, 5937, 33333333})
    void digitsSumWihExpectedNumber(int number) {
        int expected = 24;
        assertEquals(expected, numberWorker.digitsSum(number));
    }
}
