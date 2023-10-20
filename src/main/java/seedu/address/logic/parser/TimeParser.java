package seedu.address.logic.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * This class encapsulates the methods that are part of the TimeParser API.
 *
 * @author Tan Kerway
 */
public class TimeParser {
    protected static final LocalDateTime DEFAULT_DATE = LocalDateTime.of(1970, 1, 1, 0, 0);
    protected static String[] dateFormats = new String[] {
        "0",
        // time string with day and time
        "E HHmm",
        "E h'.'mma",
        "E ha",
        // time string with day only(e.g. tue, wed, etc)
        "E",

        "1",
        // time with all required information: year, month, day of month, time
        "MMM d HH:mm yyyy",
        "y-MM-dd HHmm",
        "yyyy-MM-dd HHmm",
        "yyyy-MM-dd HH'.'mma",
        "EEE MMM d HH:mm:ss zzz yyyy",
        "EEE MMM d HH:mm yyyy",
        "d/M/yy HHmm",
        "d/M/yyyy HHmm",
        "d/M/y ha",

        "2",
        // time with these information: year, month, day of month
        "y-MM-dd",
        "d/M/yy",
        "d/M/yyyy",
        "d/M/y",

        "3",
        // time with these information: month, day of month, time
        "MMM dd HH'.'mma",
        "dd MMM HH'.'mma",
        "MMM dd HHmm",
        "dd MMM HHmm",
        "d/M HHmm",
        "MMM dd ha",
        "dd MMM ha",
        "d/M ha",

        "4",
        // time with these information: month and day
        "MMM dd",
        "dd MMM",
        "d/M"
    };

    /**
     * Converts the string date into a Datetime object.
     *
     * @author Tan Kerway
     * @param time the String that contains the data for the date
     * @return datetime object that represents the string
     */
    public static LocalDateTime parseDate(String time)
            throws seedu.address.logic.parser.exceptions.ParseException {
        int currentFormatID = -1;
        for (String formatString : dateFormats) { // find a date format string that matches the user pattern
            try {
                if (isNumeric(formatString)) {
                    currentFormatID = Integer.parseInt(formatString);
                    continue;
                }
                LocalDateTime temp = new SimpleDateFormat(formatString)
                        .parse(time)
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime();
                return addMissingDateFields(temp, currentFormatID);
            } catch (ParseException ignored) {
                String s = "";
            }
        }
        throw new seedu.address.logic.parser.exceptions.ParseException("Please specify a valid date!");
    }

    /**
     * Appends missing date fields depending on what the user typed in.
     * @author Tan Kerway
     * @param temp the LocalDateTime object to modify
     * @param currentFormatID the given type of date object
     * @return a LocalDateTime object that has all the required information
     */
    private static LocalDateTime addMissingDateFields(LocalDateTime temp, int currentFormatID)
            throws seedu.address.logic.parser.exceptions.ParseException {
        switch (currentFormatID) {
        case 0: // case where the current format is a day(index 0)
            // add the year, month, day, and time(default to 2359)
            temp = addDay(temp);
            break;
        case 2: // case where the current format is missing time(index 8 to 11)
            temp = addTime(temp);
            break;
        case 3: // case where the current format is missing year(index 12 to 19)
            // add the year to the final date
            temp = addYear(temp);
            break;
        case 4: // case where the current format is missing year and time(index 20 to 22)
            // add the year and time to the final date
            temp = addYear(temp);
            temp = addTime(temp);
            if (temp.isBefore(LocalDateTime.now())) {
                temp = temp.plusYears(1);
            }
            break;
        default: // case where the current format is not missing anything(index 1 to 7)
            break; // keep the final date as is
        }
        // guard clause: the given date is before today's date even after parsing
        if (temp.isBefore(LocalDateTime.now())) {
            throw new seedu.address.logic.parser.exceptions.ParseException("Please specify a valid date!");
        }
        return temp;
    }

    /**
     * Checks if the given String is numeric or not.
     *
     * @author Tan Kerway
     * @param str the string to check
     * @return true if the string is numeric, false otherwise
     */
    private static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException ignored) {
            String s = "";
        }
        return false;
    }

    /**
     * Adds the year to the date object.
     *
     * @author Tan Kerway
     * @param time the LocalDateTime object that represents start dates, or deadlines
     * @return the modified LocalDateTime object
     */
    private static LocalDateTime addYear(LocalDateTime time) {
        assert time != null : "time should be not null";
        LocalDateTime now = LocalDateTime.now();
        int yearsElapsed = now.getYear() - DEFAULT_DATE.getYear();
        // update the year by adding to the current year
        // why: this method is called when the input date does not have a year
        return time.plusYears(yearsElapsed);
    }

    /**
     * Adds the day to the date object.
     *
     * @author Tan Kerway
     * @param time the LocalDateTime object that represents start dates, or deadlines
     * @return the modified LocalDateTime object
     */
    private static LocalDateTime addDay(LocalDateTime time) {
        assert time != null : "time should be not null";
        LocalDateTime now = LocalDateTime.now();
        int defaultDay = DEFAULT_DATE.getDayOfWeek().getValue();
        int timeDay = time.getDayOfWeek().getValue();
        int daysElapsed = timeDay >= defaultDay ? timeDay - defaultDay : 7 - defaultDay + timeDay;
        // update the current date by the number of days
        LocalDateTime res = now.plusDays(daysElapsed);
        if (time.getHour() == 0 && time.getMinute() == 0) {
            return addTime(res);
        }
        return res.withHour(time.getHour()).withMinute(time.getMinute());
    }

    /**
     * Updates the time fields of the LocalDateTime object.
     *
     * @author Tan Kerway
     * @param time the given LocalDateTime object
     * @return a new LocalDateTime object, with the same parameters as time, but with hour field
     *         set to 23 and minute field set to 59
     */
    private static LocalDateTime addTime(LocalDateTime time) {
        assert time != null : "time should be not null";
        return time.withHour(23).withMinute(59);
    }

    /**
     * Formats the time String.
     *
     * @author Tan Kerway
     * @param time the given LocalDateTime object to format
     * @return the string form of the LocalDateTime object
     */
    public static String formatDate(LocalDateTime time) {
        assert time != null : "time should be not null";
        return time.format(DateTimeFormatter.ofPattern("MMM dd yyyy',' H.mma"));
    }
}
