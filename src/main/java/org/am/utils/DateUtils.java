/**
 * Date         Comments
 * ---------------------------------------------
 * 2005-09-30 correctTimeZone is added.
 */
package org.am.utils;

import java.text.*;
import java.util.*;

/**
 * The DateUtils.
 *
 * @author Alexander Maximenya
 * @version 1.0
 */
public class DateUtils {
    public static final String PATTERN_DATE = "yyyy-MM-dd";
    public static final String PATTERN_TIME = "HH:mm:ss";
    public static final String PATTERN_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String[] NAMES_OF_DAYS = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    public static final String[] NAMES_OF_DAYS_SHORT = new String[]{"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

    /**
     * Gets a start date of week.
     *
     * @return a start date of week
     */
    public static Date getCurrentWeekStartDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return calendar.getTime();
    }

    /**
     * Gets a start date of week.
     *
     * @return a start date of week
     */
    public static String getCurrentWeekStartDate(String pattern) {
        return format(getCurrentWeekStartDate(), pattern);
    }

    /**
     * Gets an end date of week.
     *
     * @return an end date of week
     */
    public static Date getCurrentWeekEndDate() {
        Date startDate = getCurrentWeekStartDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.roll(Calendar.DAY_OF_WEEK, 6);

//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

        return calendar.getTime();
    }

    /**
     * Gets an end date of week.
     *
     * @return an end date of week
     */
    public static String getCurrentWeekEndDate(String pattern) {
        return format(getCurrentWeekEndDate(), pattern);
    }

    /**
     * Gets start date of curent month.
     *
     * @return start date of curent month
     */
    public static Date getCurrentMonthStartDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    /**
     * Gets start date of curent month.
     *
     * @param pattern a string pattern
     * @return        start date of curent month.
     */
    public static String getCurrentMonthStartDate(String pattern) {
        return format(getCurrentMonthStartDate(), pattern);
    }
    /**
     * Gets end date of curent month.
     *
     * @return end date of curent month
     */
    public static Date getCurrentMonthEndDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    /**
     * Gets end date of curent month.
     *
     * @param pattern a string pattern
     * @return        end date of curent month.
     */
    public static String getCurrentMonthEndDate(String pattern) {
        return format(getCurrentMonthEndDate(), pattern);
    }

    /**
     * Formats a Date into a date/time string.
     *
     * @param date    some date
     * @param pattern the pattern describing the date and time format
     * @return        the formatted time string
     * @see           org.am.utils.DateUtils#PATTERN_DATE
     * @see           org.am.utils.DateUtils#PATTERN_TIME
     * @see           org.am.utils.DateUtils#PATTERN_DATE_TIME
     */
    public static String format(Date date , String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    /**
     * Corrects the default time zone.
     */
    public static void correctTimeZone() {
        TimeZone defaultTimeZone = TimeZone.getDefault();
        int rawOffset = defaultTimeZone.getRawOffset() * -1;
        SimpleTimeZone simpleTimeZone = new SimpleTimeZone(rawOffset, defaultTimeZone.getID(), 0, 0, 0, 0, 0, 0, 0, 0);
        TimeZone.setDefault(simpleTimeZone);
    }

    /**
     * Creates and gets a date from a given string.
     *
     * @param stringDate a string
     * @param pattern    a pattern
     * @return           a date
     * @throws ParseException an exception
     */
    public static Date getDate(String stringDate, String pattern) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.parse(stringDate);
    }

    /**
     * Gets string date
     *
     * @param pattern a pattern
     * @return        string date
     * @see DateUtils#PATTERN_DATE
     * @see DateUtils#PATTERN_TIME
     * @see DateUtils#PATTERN_DATE_TIME
     */
    public static String getDate(String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(new Date());
    }

    /**
     * Gets a day of week from a given date.
     *
     * @param stringDate a string date
     * @param pattern    a pattern
     * @return  a day of week from a given date
     * @throws java.text.ParseException an exception
     */
    public static String getDayOfWeek(String stringDate, String pattern) throws ParseException {
        return getDayOfWeek(getDate(stringDate, pattern));
    }

    /**
     * Gets a day of week from a given date.
     *
     * @param date a date
     * @return     a day of week from a given date
     */
    public static String getDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        String strDay = NAMES_OF_DAYS[day - 1];
        return strDay;
    }

    /**
     * Gets a difference between two dates.
     *
     * @param startDate           a start date
     * @param endDate             an end date
     * @return                    a difference between two dates
     */
    public static String getDifference(Date startDate, Date endDate) {
        final String COLON = ":";
        final String POINT = ".";

        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);

        int hours = endCal.get(Calendar.HOUR) - startCal.get(Calendar.HOUR);
        int minutes = endCal.get(Calendar.MINUTE) - startCal.get(Calendar.MINUTE);
        int seconds = endCal.get(Calendar.SECOND) - startCal.get(Calendar.SECOND);
        int milliseconds = endCal.get(Calendar.MILLISECOND) - startCal.get(Calendar.MILLISECOND);

        return addLeadingZero(hours) + COLON + addLeadingZero(minutes) + COLON + addLeadingZero(seconds) + POINT + addLeadingZero(milliseconds);
    }

    private static String addLeadingZero(int i) {
      if (i < 10) {
        return "0" + i;
      } else {
        return "" + i;
      }
    }

  /**
   * Gets an array of days in given interval.
   *
   * @param start a first day of interval
   * @param end   a last day of interval
   * @return      the array of days
   */
  public static Date[] getDaysInterval(Date start, Date end) {
    Calendar startCal = Calendar.getInstance();
    startCal.setTime(start);
    Calendar endCal = Calendar.getInstance();
    endCal.setTime(end);
    Calendar resultCal = Calendar.getInstance();

    int years = endCal.get(Calendar.YEAR) - startCal.get(Calendar.YEAR);
    int days = endCal.get(Calendar.DAY_OF_YEAR) - startCal.get(Calendar.DAY_OF_YEAR) + 1;//because first day is 0.
    if (years > 0) {
//      days = 366 * years - Math.abs(days) + 1;
      days = 365 * years - Math.abs(days);
    }

    Date[] interval = new Date[days];
    for (int i = 0; i < interval.length; i++) {
      resultCal.set(Calendar.YEAR, startCal.get(Calendar.YEAR));
      resultCal.set(Calendar.DAY_OF_YEAR, startCal.get(Calendar.DAY_OF_YEAR) + i);
      interval[i] = resultCal.getTime();
    }

    return interval;
  }
}
