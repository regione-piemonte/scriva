/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * The type Calendar utils.
 *
 * @author CSI PIEMONTE
 */
public class CalendarUtils {
    private static final Logger LOGGER = Logger.getLogger(Constants.LOGGER_NAME + ".util");

    /**
     * The constant DATE_FORMAT_DDMMYYYYHHMMSS.
     */
    public static final String DATE_FORMAT_DDMMYYYYHHMMSS = "dd/MM/yyyy HH:mm:ss";
    /**
     * The constant DATE_FORMAT_DDMMYYYY.
     */
    public static final String DATE_FORMAT_DDMMYYYY = "dd/MM/yyyy";
    /**
     * The constant DATE_FORMAT_DDMMYYYY_NO_SLASH.
     */
    public static final String DATE_FORMAT_DDMMYYYY_NO_SLASH = "ddMMyyyy";
    /**
     * The constant DATE_FORMAT_DDMMYY.
     */
    public static final String DATE_FORMAT_DDMMYY = "dd/MM/yy";
    /**
     * The constant DATE_FORMAT_YYYMMDD.
     */
    public static final String DATE_FORMAT_YYYMMDD = "yyyyMMdd";
    /**
     * The constant DATE_FORMAT_YYYMMDD_HHMM.
     */
    public static final String DATE_FORMAT_YYYMMDD_HHMM = "yyyyMMdd HH.mm";
    /**
     * The constant DATE_FORMAT_YYYMMDD_HHMMSS.
     */
    public static final String DATE_FORMAT_YYYMMDD_HHMMSS = "yyyyMMdd HH:mm:ss";
    /**
     * The constant DATE_FORMAT_YYYMMDD__HHMMSS.
     */
    public static final String DATE_FORMAT_YYYMMDD__HHMMSS = "yyyyMMdd_HHmmss";
    /**
     * The constant DATE_FORMAT_YYYYMMDDHHMMSS.
     */
    public static final String DATE_FORMAT_YYYYMMDDHHMMSS = "yyyy/MM/dd HH:mm:ss";
    /**
     * The constant DATE_FORMAT_YYYY_MM_DDHHMMSS.
     */
    public static final String DATE_FORMAT_YYYY_MM_DDHHMM = "yyyy-MM-dd HH:mm";
    /**
     * The constant DATE_FORMAT_YYYYMMDDTHHMMSS.
     */
    public static final String DATE_FORMAT_YYYYMMDDTHHMMSS = "yyyy/MM/ddTHH:mm:ss";
    /**
     * The constant DATE_FORMAT_YYYYMMDD.
     */
    public static final String DATE_FORMAT_YYYYMMDD = "yyyy/MM/dd";
    /**
     * The constant DATE_FORMAT_YYMMDD.
     */
    public static final String DATE_FORMAT_YYMMDD = "yy/MM/dd";

    /**
     * The constant LABEL_FORMAT_MINUTE.
     */
    public static final String LABEL_FORMAT_MINUTE = "dd/MM/yyyy HH:mm";
    /**
     * The constant LABEL_FORMAT_HOUR.
     */
    public static final String LABEL_FORMAT_HOUR = "dd/MM/yyyy HH:00";
    /**
     * The constant LABEL_FORMAT_DAY.
     */
    public static final String LABEL_FORMAT_DAY = "dd/MM/yyyy";
    /**
     * The constant LABEL_FORMAT_MONTH.
     */
    public static final String LABEL_FORMAT_MONTH = "MM/yyyy";
    /**
     * The constant LABEL_FORMAT_YEAR.
     */
    public static final String LABEL_FORMAT_YEAR = "yyyy";

    /**
     * The constant REGEX_LABEL_FORMAT_HOUR.
     */
    public static final String REGEX_LABEL_FORMAT_HOUR = "^(\\d{2})\\/(\\d{2})\\/(\\d{4}) (\\d{2}):00$";
    /**
     * The constant REGEX_LABEL_FORMAT_DAY.
     */
    public static final String REGEX_LABEL_FORMAT_DAY = "^(\\d{2})\\/(\\d{2})\\/(\\d{4})$";
    /**
     * The constant REGEX_LABEL_FORMAT_MONTH.
     */
    public static final String REGEX_LABEL_FORMAT_MONTH = "^(\\d{2})\\/(\\d{4})$";
    /**
     * The constant REGEX_LABEL_FORMAT_YEAR.
     */
    public static final String REGEX_LABEL_FORMAT_YEAR = "^(\\d{4})$";

    /**
     * The constant DEFAULT_TIME_ZONE.
     */
    public static final String DEFAULT_TIME_ZONE = "GMT+1";


    /**
     * Date is in the past boolean.
     *
     * @param dateToCheck dateToCheck
     * @return boolean boolean
     */
    public static boolean dateIsInThePast(Calendar dateToCheck) {
        Calendar now = Calendar.getInstance();
        return dateToCheck.before(now);
    }

    /**
     * Creating a local date using a instant
     *
     * @param dateToConvert The date to convert
     * @return {@link LocalDate}
     */
    public static LocalDate convertToLocalDateUsingInstant(Date dateToConvert) {
        return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * Creating an Instant object – using the ofEpochMilli() method
     *
     * @param dateToConvert The date to convert
     * @return {@link LocalDate}
     */
    public static LocalDate convertToLocalDateUsingMilisecond(Date dateToConvert) {
        if (dateToConvert == null) {

            LOGGER.warn("Warning, the date to convert cannot be empty");
            return null;
        }
        return Instant.ofEpochMilli(dateToConvert.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * Creating a local date time using a instant
     *
     * @param dateToConvert The date to convert
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime convertToLocalDateTimeUsingInstant(Date dateToConvert) {
        return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * Creating an Instant object – using the ofEpochMilli() method
     *
     * @param dateToConvert The date to convert
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime convertToLocalDateTimeUsingMilisecond(Date dateToConvert) {
        if (dateToConvert == null) {

            LOGGER.warn("Warning, the date to convert cannot be empty");
            return null;
        }
        return Instant.ofEpochMilli(dateToConvert.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * Date is in current year boolean.
     *
     * @param calendar calendar
     * @return booelan boolean
     */
    public static boolean dateIsInCurrentYear(Calendar calendar) {
        if (calendar == null) {
            LOGGER.error("Cal cannot be empty...");
            return false;
        }

        int year = LocalDate.now().getYear();
        LocalDate firstDayOfYear = LocalDate.of(year, 1, 1);
        LocalDate lastDayOfYear = LocalDate.of(year, 12, 31);

        LocalDate dateToCheck = LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH));

        if (dateToCheck.equals(firstDayOfYear) || dateToCheck.equals(lastDayOfYear)) {
            LOGGER.info("Current Date : {" + dateToCheck + "} is in current year {" + year + "}");
            return true;
        }

        if (dateToCheck.isAfter(firstDayOfYear) && dateToCheck.isBefore(lastDayOfYear)) {
            LOGGER.info("Current Date : {" + dateToCheck + "} is in current year {" + year + "}");
            return true;
        }
        return false;
    }

    /**
     * Is valid date boolean.
     *
     * @param date   date
     * @param format format
     * @return booelan boolean
     */
    public static boolean isValidDate(String date, String format) {

        if (StringUtils.isBlank(date)) {
            return false;
        }

        Date formattedDate = CalendarUtils.convertStringToDate(date, format);

        return formattedDate != null;
    }

    /**
     * Reset some calendar fields (hour of day, minute, second, millisecond)
     *
     * @param calendar The calendar to reset
     * @return {@link Calendar}
     */
    public static Calendar resetCalendar(Calendar calendar) {
        if (calendar != null) {
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
        }
        return calendar;
    }

    /**
     * Days between int.
     *
     * @param before before
     * @param after  after
     * @return int int
     */
    public static int daysBetween(Date before, Date after) {

        LocalDate beforeLd = LocalDate.parse(CalendarUtils.convertDateToString(CalendarUtils.DATE_FORMAT_DDMMYYYY, before),
                DateTimeFormatter.ofPattern(CalendarUtils.DATE_FORMAT_DDMMYYYY));
        LocalDate afterLd = LocalDate.parse(CalendarUtils.convertDateToString(CalendarUtils.DATE_FORMAT_DDMMYYYY, after),
                DateTimeFormatter.ofPattern(CalendarUtils.DATE_FORMAT_DDMMYYYY));

        long days = java.time.temporal.ChronoUnit.DAYS.between(beforeLd, afterLd);
        return (int) days;
    }

    /**
     * Is adult boolean.
     *
     * @param birthDate birthDate
     * @return boolean boolean
     */
    public static boolean isAdult(Date birthDate) {
        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();
        LocalDate beforeLd = LocalDate.parse(CalendarUtils.convertDateToString(CalendarUtils.DATE_FORMAT_DDMMYYYY, birthDate),
                DateTimeFormatter.ofPattern(CalendarUtils.DATE_FORMAT_DDMMYYYY));
        LocalDate afterLd = LocalDate.parse(CalendarUtils.convertDateToString(CalendarUtils.DATE_FORMAT_DDMMYYYY, today),
                DateTimeFormatter.ofPattern(CalendarUtils.DATE_FORMAT_DDMMYYYY));

        long years = java.time.temporal.ChronoUnit.YEARS.between(beforeLd, afterLd);
        return years >= 18;
    }

    /**
     * Format date for cal string.
     *
     * @param date date
     * @return string string
     */
    public static String formatDateForCal(Date date) {
        String ret = StringUtils.EMPTY;
        Calendar day = new GregorianCalendar();
        day.setTime(date);
        int dayOfMonth = day.get(Calendar.DAY_OF_MONTH);
        int year = day.get(Calendar.YEAR);
        int month = day.get(Calendar.MONTH);
        String monthString = CalendarUtils.getShortMonth(month);
        ret = dayOfMonth + " " + monthString + " " + year;
        return ret;
    }

    /**
     * Convert string to date date.
     *
     * @param dateString dateString
     * @param pattern    pattern
     * @return date date
     */
    public static Date convertStringToDate(String dateString, String pattern) {
        if (dateString == null || dateString.isEmpty())
            return null;

        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        Date dateFormatted = null;
        try {
            dateFormatted = formatter.parse(dateString);
        } catch (Exception e) {
            // e.printStackTrace();
            LOGGER.error(e);
        }
        return dateFormatted;
    }

    /**
     * Convert string gmt plus 1 to date date.
     *
     * @param dateString dateString
     * @param pattern    pattern
     * @return date date
     */
    public static Date convertStringGMTPlus1ToDate(String dateString, String pattern) {
        if (dateString == null || dateString.isEmpty()) {
            return null;
        }
        pattern += " Z";
        dateString += " +0100";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        Date dateFormatted = null;
        try {
            dateFormatted = formatter.parse(dateString);
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return dateFormatted;
    }

    /**
     * Convert date to string string.
     *
     * @param pattern       pattern
     * @param dateToConvert dateToConvert
     * @return string string
     */
    public static String convertDateToString(String pattern, Date dateToConvert) {
        if (dateToConvert == null) {
            return null;
        }
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(dateToConvert);
    }

    /**
     * Convert calendar to string string.
     *
     * @param pattern           pattern
     * @param calendarToConvert calendarToConvert
     * @return string string
     */
    public static String convertCalendarToString(String pattern, Calendar calendarToConvert) {
        if (calendarToConvert == null) {
            LOGGER.warn("Calendar cannot be empty..");
            return null;
        }
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(calendarToConvert.getTime());
    }

    /**
     * Gets day of week.
     *
     * @param dayOfWeek dayOfWeek
     * @return string day of week
     */
    public static String getDayOfWeek(int dayOfWeek) {
        String ret = "";
        switch (dayOfWeek) {
            case Calendar.MONDAY:
                ret = "Lun";
                break;
            case Calendar.TUESDAY:
                ret = "Mar";
                break;
            case Calendar.WEDNESDAY:
                ret = "Mer";
                break;
            case Calendar.THURSDAY:
                ret = "Gio";
                break;
            case Calendar.FRIDAY:
                ret = "Ven";
                break;
            case Calendar.SATURDAY:
                ret = "Sab";
                break;
            default:
                ret = "Dom";
                break;
        }
        return ret;
    }

    /**
     * Gets month.
     *
     * @param month dayOfWeek
     * @return string month
     */
    public static String getMonth(int month) {
        String ret = "";
        switch (month) {
            case Calendar.FEBRUARY:
                ret = "Febbraio";
                break;
            case Calendar.MARCH:
                ret = "Marzo";
                break;
            case Calendar.APRIL:
                ret = "Aprile";
                break;
            case Calendar.MAY:
                ret = "Maggio";
                break;
            case Calendar.JUNE:
                ret = "Giugno";
                break;
            case Calendar.JULY:
                ret = "Luglio";
                break;
            case Calendar.AUGUST:
                ret = "Agosto";
                break;
            case Calendar.SEPTEMBER:
                ret = "Settembre";
                break;
            case Calendar.OCTOBER:
                ret = "Ottobre";
                break;
            case Calendar.NOVEMBER:
                ret = "Novembre";
                break;
            case Calendar.DECEMBER:
                ret = "Dicembre";
                break;
            default:
                ret = "Gennaio";
                break;
        }

        return ret;
    }

    /**
     * Gets short month.
     *
     * @param month month
     * @return string short month
     */
    public static String getShortMonth(int month) {
        String ret = "";
        switch (month) {
            case Calendar.FEBRUARY:
                ret = "Feb";
                break;
            case Calendar.MARCH:
                ret = "Mar";
                break;
            case Calendar.APRIL:
                ret = "Apr";
                break;
            case Calendar.MAY:
                ret = "Mag";
                break;
            case Calendar.JUNE:
                ret = "Giu";
                break;
            case Calendar.JULY:
                ret = "Lug";
                break;
            case Calendar.AUGUST:
                ret = "Ago";
                break;
            case Calendar.SEPTEMBER:
                ret = "Set";
                break;
            case Calendar.OCTOBER:
                ret = "Ott";
                break;
            case Calendar.NOVEMBER:
                ret = "Nov";
                break;
            case Calendar.DECEMBER:
                ret = "Dic";
                break;
            default:
                ret = "Gen";
                break;
        }
        return ret;
    }


    /**
     * Convert date to local date local date.
     *
     * @param date date
     * @return localDate local date
     */
    public static LocalDate convertDateToLocalDate(Date date) {
        return LocalDate.parse(convertDateToString(DATE_FORMAT_DDMMYYYYHHMMSS, date), DateTimeFormatter.ofPattern(DATE_FORMAT_DDMMYYYYHHMMSS));
    }

    /**
     * Convert timestamp to date date.
     *
     * @param millis millis
     * @return date date
     */
    public static Date convertTimestampToDate(Long millis) {
        if (millis == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
        return cal.getTime();
    }

    /**
     * Convert date to calendar calendar.
     *
     * @param date date
     * @return calendar calendar
     */
    public static Calendar convertDateToCalendar(Date date) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    /**
     * Is not today boolean.
     *
     * @param date date
     * @return boolean boolean
     */
    public static boolean isNotToday(Date date) {
        boolean result = false;
        Calendar toDay = Calendar.getInstance();
        Calendar dateToCheck = convertDateToCalendar(date);
        if (toDay != null && dateToCheck != null) {
            resetCalendar(dateToCheck);
            resetCalendar(toDay);
            result = (dateToCheck.get(Calendar.YEAR) != toDay.get(Calendar.YEAR))
                    && (dateToCheck.get(Calendar.MONTH) != toDay.get(Calendar.MONTH))
                    && (dateToCheck.get(Calendar.DAY_OF_MONTH) != toDay.get(Calendar.DAY_OF_MONTH));
        }
        return result;
    }

    /**
     * Is today boolean.
     *
     * @param date date
     * @return booelan boolean
     */
    public static boolean isToday(Date date) {
        boolean result = false;
        Calendar toDay = Calendar.getInstance();
        Calendar dateToCheck = convertDateToCalendar(date);
        if (toDay != null && dateToCheck != null) {
            resetCalendar(dateToCheck);
            resetCalendar(toDay);
            result = (dateToCheck.get(Calendar.YEAR) == toDay.get(Calendar.YEAR))
                    && (dateToCheck.get(Calendar.MONTH) == toDay.get(Calendar.MONTH))
                    && (dateToCheck.get(Calendar.DAY_OF_MONTH) == toDay.get(Calendar.DAY_OF_MONTH));
        }
        return result;
    }

    /**
     * Is greather than boolean.
     *
     * @param anni      anni
     * @param birthDate birthDate
     * @return boolean boolean
     */
    public static boolean isGreatherThan(int anni, Date birthDate) {
        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();
        LocalDate beforeLd = LocalDate.parse(CalendarUtils.convertDateToString(CalendarUtils.DATE_FORMAT_DDMMYYYYHHMMSS, birthDate),
                DateTimeFormatter.ofPattern(CalendarUtils.DATE_FORMAT_DDMMYYYYHHMMSS));
        LocalDate afterLd = LocalDate.parse(CalendarUtils.convertDateToString(CalendarUtils.DATE_FORMAT_DDMMYYYYHHMMSS, today),
                DateTimeFormatter.ofPattern(CalendarUtils.DATE_FORMAT_DDMMYYYYHHMMSS));

        long years = java.time.temporal.ChronoUnit.YEARS.between(beforeLd, afterLd);
        return years > anni;
    }

    /**
     * Now date.
     *
     * @return date date
     */
    public static Date now() {
        Calendar cal = Calendar.getInstance();
        return cal.getTime();
    }


    /**
     * At start of day date.
     *
     * @param date date
     * @return date date
     */
    public static Date atStartOfDay(Date date) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return localDateTimeToDate(startOfDay);
    }

    /**
     * At end of day date.
     *
     * @param date date
     * @return date date
     */
    public static Date atEndOfDay(Date date) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return localDateTimeToDate(endOfDay);
    }

    /**
     * Date to local date time local date time.
     *
     * @param date date
     * @return localDateTime local date time
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * @param localDateTime localDateTime
     * @return date
     */
    private static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Gets time zone calendar intance.
     *
     * @return calendar time zone calendar intance
     */
    public static Calendar getTimeZoneCalendarIntance() {
        return Calendar.getInstance(TimeZone.getTimeZone(DEFAULT_TIME_ZONE));
    }

}