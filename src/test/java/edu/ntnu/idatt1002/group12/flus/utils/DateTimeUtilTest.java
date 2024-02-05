package edu.ntnu.idatt1002.group12.flus.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class DateTimeUtilTest {

    private DateTimeUtil dateTimeUtil;

    @BeforeEach
    void setUp() {
        dateTimeUtil = new DateTimeUtil(2022, 3, 15, 12, 30, 0);
    }

    @Test
    void testGetDateTime() {
        assertEquals(LocalDateTime.of(2022, 3, 15, 12, 30, 0), dateTimeUtil.getDateTime());
    }

    @Test
    void testGetFormattedDateTime() {
        assertEquals("15/03/2022 12:30:00", dateTimeUtil.getFormattedDateTime());
    }

    @Test
    void testIsAfter() {
        DateTimeUtil dateTimeUtil2 = new DateTimeUtil(2022, 3, 15, 12, 0, 0);
        assertTrue(dateTimeUtil.isAfter(dateTimeUtil2));
    }

    @Test
    void testIsBefore() {
        DateTimeUtil dateTimeUtil2 = new DateTimeUtil(2022, 3, 15, 13, 0, 0);
        assertTrue(dateTimeUtil.isBefore(dateTimeUtil2));
    }

    @Test
    void testIsEqualTo() {
        DateTimeUtil dateTimeUtil2 = new DateTimeUtil(2022, 3, 15, 12, 30, 0);
        assertTrue(dateTimeUtil.isEqualTo(dateTimeUtil2));
    }

    @Test
    void testPlusMinusYears() {
        DateTimeUtil dateTimeUtil2 = dateTimeUtil.plusMinusYears(2);
        assertEquals(LocalDateTime.of(2024, 3, 15, 12, 30, 0), dateTimeUtil2.getDateTime());

        DateTimeUtil dateTimeUtil3 = dateTimeUtil.plusMinusYears(-2);
        assertEquals(LocalDateTime.of(2020, 3, 15, 12, 30, 0), dateTimeUtil3.getDateTime());
    }

    @Test
    void testPlusMinusMonths() {
        DateTimeUtil dateTimeUtil2 = dateTimeUtil.plusMinusMonths(2);
        assertEquals(LocalDateTime.of(2022, 5, 15, 12, 30, 0), dateTimeUtil2.getDateTime());

        DateTimeUtil dateTimeUtil3 = dateTimeUtil.plusMinusMonths(-2);
        assertEquals(LocalDateTime.of(2022, 1, 15, 12, 30, 0), dateTimeUtil3.getDateTime());
    }

    @Test
    void testPlusMinusDays() {
        DateTimeUtil dateTimeUtil2 = dateTimeUtil.plusMinusDays(2);
        assertEquals(LocalDateTime.of(2022, 3, 17, 12, 30, 0), dateTimeUtil2.getDateTime());

        DateTimeUtil dateTimeUtil3 = dateTimeUtil.plusMinusDays(-2);
        assertEquals(LocalDateTime.of(2022, 3, 13, 12, 30, 0), dateTimeUtil3.getDateTime());
    }

    @Test
    void testPlusMinusHours() {
        DateTimeUtil dateTimeUtil2 = dateTimeUtil.plusMinusHours(2);
        assertEquals(LocalDateTime.of(2022, 3, 15, 14, 30, 0), dateTimeUtil2.getDateTime());

        DateTimeUtil dateTimeUtil3 = dateTimeUtil.plusMinusHours(-2);
        assertEquals(LocalDateTime.of(2022, 3, 15, 10, 30, 0), dateTimeUtil3.getDateTime());
    }

    @Test
    void testPlusMinusMinutes() {
        DateTimeUtil dateTimeUtil2 = dateTimeUtil.plusMinusMinutes(2);
        assertEquals(LocalDateTime.of(2022, 3, 15, 12, 32, 0), dateTimeUtil2.getDateTime());

        DateTimeUtil dateTimeUtil3 = dateTimeUtil.plusMinusMinutes(-2);
        assertEquals(LocalDateTime.of(2022, 3, 15, 12, 28, 0), dateTimeUtil3.getDateTime());
    }

    @Test
    void testPlusMinusSeconds() {
        DateTimeUtil dateTimeUtil2 = dateTimeUtil.plusMinusSeconds(2);
        assertEquals(LocalDateTime.of(2022, 3, 15, 12, 30, 2), dateTimeUtil2.getDateTime());

        DateTimeUtil dateTimeUtil3 = dateTimeUtil.plusMinusSeconds(-2);
        assertEquals(LocalDateTime.of(2022, 3, 15, 12, 29, 58), dateTimeUtil3.getDateTime());
    }

}
