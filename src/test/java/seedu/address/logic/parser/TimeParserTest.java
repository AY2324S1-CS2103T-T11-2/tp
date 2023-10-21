package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

class TimeParserTest {
    private static final LocalDateTime TEST_DATE =
            LocalDateTime.of(2099, 1, 1, 23, 59);
    private static final LocalDateTime DEFAULT_DATE =
            LocalDateTime.of(1970, 1, 1, 0, 0);

    @Test
    void testDefaultDate() {
        assertEquals(DEFAULT_DATE, TimeParser.DEFAULT_DATE);
    }

    @Test
    void testDateFormatsArrayNotNull() {
        assertNotNull(TimeParser.dateFormats);
    }

    @Test
    void testInvalidDate() {
        boolean hasError = false;
        try {
            TimeParser.parseDate("whenever I say so");
        } catch (ParseException parseException) {
            hasError = true;
        }
        assertTrue(hasError);
    }

    @Test
    void testParseDateWithDdMmYyyy() throws ParseException {
        assertEquals(TEST_DATE, TimeParser.parseDate("01/01/2099"));
    }

    @Test
    void testParseDateWithPastDate() {
        boolean hasError = false;
        try {
            TimeParser.parseDate("01/01/1970");
        } catch (ParseException parseException) {
            hasError = true;
        }
        assertTrue(hasError);
    }

    @Test
    void testZeroId() throws ParseException {
        assertNotNull(TimeParser.parseDate("mon 2359"));
    }

    @Test
    void testOneId() throws ParseException {
        assertNotNull(TimeParser.parseDate("mon 2359"));
    }

    @Test
    void testTwoId() throws ParseException {
        assertNotNull(TimeParser.parseDate("23 jan 15"));
    }

    @Test
    void testThreeId() throws ParseException {
        assertNotNull(TimeParser.parseDate("dec 4 8.53pm"));
    }

    @Test
    void testFourId() throws ParseException {
        assertNotNull(TimeParser.parseDate("dec 4"));
    }
}
