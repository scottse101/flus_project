package edu.ntnu.idatt1002.group12.flus.model.transactions;

/**
 * The class represents an expense.
 *
 * @author R.Samavat
 * @version 1.0
 */
public class Expense extends Transaction {

  /**
   * Constructor to create an object of the type expense.
   *
   * @param description the description of the transaction.
   * @param amount the amount of the transaction.
   * @throws IllegalArgumentException if the description is blank or if the amount is less than or equal to zero.
   * @throws NullPointerException if the description is null.
   */
  public Expense(String description, double amount)
          throws IllegalArgumentException, NullPointerException {
    super(description, amount);
  }

  /**
   * The toString collects all the information about the expense,
   * and return a textual representation.
   *
   * @return information about the expense.
   */
  @Override
  public String toString() {
    return "Expense: Description = " + getDescription() + ", Amount = " + getAmount();
  }
}
