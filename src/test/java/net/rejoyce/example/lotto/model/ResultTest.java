package net.rejoyce.example.lotto.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Questionable how useful these are but getting coverage up
 */
class ResultTest {

    @Test
    void getDateNull() {
        Result result = new Result();
        assertNull(result.getDate());
    }

    @Test
    void getDateVal() {
        LocalDate date = LocalDate.now();
        Result result = new Result(date, new int[6]);
        assertEquals(date, result.getDate());
    }

    @Test
    void getNumbersNull() {
        Result result = new Result();
        assertNull(result.getNumbers());
    }

    @Test
    void getNumbersVal() {
        Result result = new Result(null, new int[]{1,2,3,4,5,6});
        assertEquals(1, result.getNumbers()[0]);
        assertEquals(2, result.getNumbers()[1]);
        assertEquals(3, result.getNumbers()[2]);
        assertEquals(4, result.getNumbers()[3]);
        assertEquals(5, result.getNumbers()[4]);
        assertEquals(6, result.getNumbers()[5]);
    }
}