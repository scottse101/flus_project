package edu.ntnu.idatt1002.group12.flus.model.goals;

import edu.ntnu.idatt1002.group12.flus.model.Budget;

import java.io.Serializable;
import java.util.Objects;

/**
 * The class represents a goal for financial balance.
 *
 * @author Henrik Nilsen and Ramtin Samavat.
 * @version 1.0
 * @since April 10, 2023
 */
public class FinancialGoal implements Goal, Serializable{

  private final double minimumMoneyValue;

  /**
   * Constructor to create an object of the type Financial goal.
   *
   * @param minimumMoneyValue the minimum money balance.
   * @throws IllegalArgumentException if the value is less than zero.
   */
  public FinancialGoal(double minimumMoneyValue) throws IllegalArgumentException {
    if (minimumMoneyValue < 0) {
      throw new IllegalArgumentException("The minimum value of the goal cannot be less than zero.");
    }

    this.minimumMoneyValue = minimumMoneyValue;
  }

  /**
   * The method retrieves the minimum money value.
   *
   * @return the minimum value.
   */
  public double getMinimumMoneyValue() {
    return minimumMoneyValue;
  }

  /**
   * The method checks if the goal has been achieved.
   *
   * @param budget the budget that has the goal.
   * @return true if the goal is achieved, false otherwise.
   */
  @Override
  public boolean completed(Budget budget) throws NullPointerException {
    if (budget == null) {
      throw new NullPointerException("The budget cannot be null.");
    }
    return budget.getBalance() >= getMinimumMoneyValue();
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
    FinancialGoal that = (FinancialGoal) o;
    return Double.compare(that.getMinimumMoneyValue(), getMinimumMoneyValue()) == 0;
  }

  /**
   * Creates a hashcode for the object.
   *
   * @return Hashcode for the object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(getMinimumMoneyValue());
  }
}
