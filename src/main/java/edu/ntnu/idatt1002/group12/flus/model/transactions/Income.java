package edu.ntnu.idatt1002.group12.flus.model.transactions;

/**
 * The class represents an income.
 *
 * @author R.Samavat
 * @version 1.0
 */
public class Income extends Transaction {

  /**
   * Constructor to create an object of the type income.
   *
   * @param description the description of the transaction.
   * @param amount the amount of the transaction.
   * @throws IllegalArgumentException if the description is blank or if the amount is less than or equal to zero.
   * @throws NullPointerException if the description is null.
   */
  public Income(String description, double amount)
          throws IllegalArgumentException, NullPointerException {
    super(description, amount);
  }

  /**
   * The toString collects all the information about the income,
   * and return a textual representation.
   *
   * @return information about the income.
   */
  @Override
  public String toString() {
    return "Income: Description = " + getDescription() + ", Amount = " + getAmount();
  }
}
