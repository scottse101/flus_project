package edu.ntnu.idatt1002.group12.flus.utils;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The class represents a utility class for date and time.
 *
 * @author Stian Lyng
 */
public class DateTimeUtil implements Serializable {


  private final LocalDateTime dateTime;

  /**
   * Creates a new instance of DateTimeUtil with the current date and time.
   */
  public DateTimeUtil() {
    this.dateTime = LocalDateTime.now();
  }

  /**
   * Creates a new instance of DateTimeUtil with the specified date and time.
   *
   * @param dateTime the date and time.
   */
  public DateTimeUtil(LocalDateTime dateTime) {
    this.dateTime = dateTime;
  }

  /**
   * Creates a new instance of DateTimeUtil with the specified date and time.
   *
   * @param year the year.
   * @param month the month.
   * @param dayOfMonth the day.
   * @param hour the hour.
   * @param minute the minute.
   * @param second the second.
   * @throws IllegalArgumentException if the year is less than 0 or greater than the current year.
   * @throws IllegalArgumentException if the month is less than 1 or greater than 12.
   * @throws IllegalArgumentException if the day is less than 1 or greater than 31.
   * @throws IllegalArgumentException if the hour is less than 0 or greater than 23.
   * @throws IllegalArgumentException if the minute is less than 0 or greater than 59.
   * @throws IllegalArgumentException if the second is less than 0 or greater than 59.
   */
  public DateTimeUtil(int year, int month, int dayOfMonth, int hour, int minute, int second)
          throws IllegalArgumentException {
    if (year < 0 || year > LocalDate.now().getYear()) {
      throw new IllegalArgumentException("Year must be between 0 and "
              + LocalDate.now().getYear() + ".");
    }
    if (month < 1 || month > 12) {
      throw new IllegalArgumentException("Month must be between 1 and 12.");
    }
    if (dayOfMonth < 1 || dayOfMonth > 31) {
      throw new IllegalArgumentException("Day must be between 1 and 31.");
    }
    if (hour < 0 || hour > 23) {
      throw new IllegalArgumentException("Hour must be between 0 and 23.");
    }
    if (minute < 0 || minute > 59) {
      throw new IllegalArgumentException("Minute must be between 0 and 59.");
    }
    if (second < 0 || second > 59) {
      throw new IllegalArgumentException("Second must be between 0 and 59.");
    }
    this.dateTime = LocalDateTime.of(year, month, dayOfMonth, hour, minute, second);
  }

  /**
   * Throws an UnsupportedOperationException if called. Use the constructor and create a new
   * instance with the updated date and time rather than changing the value of an existing instance.
   *
   * @param dateTime the date and time.
   */
  public void setDateTime(LocalDateTime dateTime) {
    throw new UnsupportedOperationException("Method not supported. Create a new instance instead.");
  }

  /**
   * Returns the date and time, represented by a LocalDateTime object.
   *
   * @return the date and time.
   */
  public LocalDateTime getDateTime() {
    return dateTime;
  }

  /**
   * Gets the year.
   *
   * @return the year.
   */
  public int getYear() {
    return dateTime.getYear();
  }

  /**
   * Gets the month.
   *
   * @return the month.
   */
  public int getMonth() {
    return dateTime.getMonthValue();
  }

  /**
   * Gets the day.
   *
   * @return the day.
   */
  public int getDayOfMonth() {
    return dateTime.getDayOfMonth();
  }

  /**
   * Returns the date and time, represented by a prettier String object.
   *
   * @return the date and time.
   */
  public String getFormattedDateTime() {
    return dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
  }

  /**
   * Check if this date and time is after the specified date and time.
   *
   * @param other the DateTimeUtil object to compare with.
   * @return true if this date and time is after the specified date and time.
   */
  public boolean isAfter(DateTimeUtil other) {
    return dateTime.isAfter(other.getDateTime());
  }

  /**
   * Check if this date and time is before the specified date and time.
   *
   * @param other the DateTimeUtil object to compare with.
   * @return true if this date and time is before the specified date and time.
   */
  public boolean isBefore(DateTimeUtil other) {
    return dateTime.isBefore(other.getDateTime());
  }

  /**
   * Check if this date and time is equal to the specified date and time.
   *
   * @param other the DateTimeUtil object to compare with.
   * @return true if this date and time is equal to the specified date and time.
   */
  public boolean isEqualTo(DateTimeUtil other) {
    return dateTime.isEqual(other.getDateTime());
  }


  /**
   * Adds or subtracts the specified number of years to this date and time.
   *
   * @param years the number of years to add or subtract. If the number is negative, the number of years will be subtracted.
   * @return a new instance of DateTimeUtil with the updated date and time.
   */
  public DateTimeUtil plusMinusYears(int years) {
    if (years < 0) {
      return new DateTimeUtil(dateTime.minusYears(Math.abs(years)));
    } else {
      return new DateTimeUtil(dateTime.plusYears(years));
    }
  }

  /**
   * Adds or subtracts the specified number of months to this date and time.
   *
   * @param months the number of months to add or subtract. If the number is negative, the number of months will be subtracted.
   * @return a new instance of DateTimeUtil with the updated date and time.
   */
  public DateTimeUtil plusMinusMonths(int months) {
    if (months < 0) {
      return new DateTimeUtil(dateTime.minusMonths(Math.abs(months)));
    } else {
      return new DateTimeUtil(dateTime.plusMonths(months));
    }
  }

  /**
   * Adds or subtracts the specified number of days to this date and time.
   *
   * @param days the number of days to add or subtract. If the number is negative, the number of days will be subtracted.
   * @return a new instance of DateTimeUtil with the updated date and time.
   */
  public DateTimeUtil plusMinusDays(int days) {
    if (days < 0) {
      return new DateTimeUtil(dateTime.minusDays(Math.abs(days)));
    } else {
      return new DateTimeUtil(dateTime.plusDays(days));
    }
  }

  /**
   * Adds or subtracts the specified number of hours to this date and time.
   *
   * @param hours the number of hours to add or subtract. If the number is negative, the number of hours will be subtracted.
   * @return a new instance of DateTimeUtil with the updated date and time.
   */
  public DateTimeUtil plusMinusHours(int hours) {
    if (hours < 0) {
      return new DateTimeUtil(dateTime.minusHours(Math.abs(hours)));
    } else {
      return new DateTimeUtil(dateTime.plusHours(hours));
    }
  }

  /**
   * Adds or subtracts the specified number of minutes to this date and time.
   *
   * @param minutes the number of minutes to add or subtract. If the number is negative, the number of minutes will be subtracted.
   * @return a new instance of DateTimeUtil with the updated date and time.
   */
  public DateTimeUtil plusMinusMinutes(int minutes) {
    if (minutes < 0) {
      return new DateTimeUtil(dateTime.minusMinutes(Math.abs(minutes)));
    } else {
      return new DateTimeUtil(dateTime.plusMinutes(minutes));
    }
  }

  /**
   * Adds or subtracts the specified number of seconds to this date and time.
   *
   * @param seconds the number of seconds to add or subtract. If the number is negative, the number of seconds will be subtracted.
   * @return a new instance of DateTimeUtil with the updated date and time.
   */
  public DateTimeUtil plusMinusSeconds(int seconds) {
    if (seconds < 0) {
      return new DateTimeUtil(dateTime.minusSeconds(Math.abs(seconds)));
    } else {
      return new DateTimeUtil(dateTime.plusSeconds(seconds));
    }
  }
}
