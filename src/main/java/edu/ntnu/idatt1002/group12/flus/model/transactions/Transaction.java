package edu.ntnu.idatt1002.group12.flus.model.transactions;

import edu.ntnu.idatt1002.group12.flus.utils.DateTimeUtil;

import java.io.Serializable;
import java.util.Objects;

/**
 * The abstract class represents a financial transaction.
 * It contains information about the transaction, such as its
 * description, amount, and date.
 *
 * @author R.Samavat
 * @version 1.0
 * @since April 12, 2023
 */
public abstract class Transaction implements Serializable{
  private final String description;
  private final double amount;
  private final DateTimeUtil date;

  /**
   * Constructor to create an object of the type transaction.
   *
   * @param description the description of the transaction.
   * @param amount the amount of the transaction.
   * @throws IllegalArgumentException if description is blank or amount is less than or equal to zero.
   * @throws NullPointerException if the description is null.
   */
  public Transaction(String description, double amount)
          throws IllegalArgumentException, NullPointerException {
    if (description.isBlank()) {
      throw new IllegalArgumentException("Description cannot be blank.");
    }
    if (amount <= 0) {
      throw new IllegalArgumentException("Amount cannot be less than or equal to zero.");
    }
    this.description = Objects.requireNonNull(description.trim(), "Description cannot be null");
    this.amount = amount;
    this.date = new DateTimeUtil();
  }

  /**
   * The method retrieves the date of the transaction.
   *
   * @return the date.
   */
  public DateTimeUtil getDate() {
    return date;
  }


  /**
   * The method retrieves the description of the transaction.
   *
   * @return the description.
   */
  public String getDescription() {
    return description;
  }

  /**
   * The method retrieves the amount of the transaction.
   *
   * @return the amount.
   */
  public double getAmount() {
    return amount;
  }

  /**
   * The method checks for equality between objects.
   *
   * @param o The object that this object will be compared to.
   * @return true if the objects are equal, false if they are not.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Transaction that = (Transaction) o;
    return Double.compare(that.getAmount(), getAmount()) == 0
            && Objects.equals(getDescription(), that.getDescription());
  }

  /**
   * Creates a hashcode for the object.
   *
   * @return Hashcode for the object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(getDescription(), getAmount(), getDate());
  }
}