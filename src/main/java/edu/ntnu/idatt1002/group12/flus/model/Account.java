package edu.ntnu.idatt1002.group12.flus.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The class represents an account for the user. An Account object
 * contains the basic information about a user such as their username, password,
 * email, and phone number. It also contains a list of budgets.
 *
 * @author Ramtin Samavat.
 * @version 2.0
 * @since April 17, 2023.
 */
public class Account extends User {
  private final List<Budget> budgets;

  /**
   * Constructor to create an object of the type Account.
   *
   * @param username the name of the user.
   * @param password the password for the user.
   * @param email the email of the user.
   * @param phoneNumber the phone number of the user.
   * @throws IllegalArgumentException if the variables do not meet the content requirements.
   * @throws NullPointerException     if the variables are null.
   */
  public Account(String username, String password, String email, String phoneNumber)
          throws IllegalArgumentException, NullPointerException {
    super(username, password, email, phoneNumber);
    budgets = new ArrayList<>();
  }

  /**
   * The method adds budgets to the list of budgets.
   *
   * @param budget th budget to be added to the list.
   * @throws NullPointerException if the budget is null.
   */
  public void addBudget(Budget budget) throws NullPointerException {
    if (budget == null) {
      throw new NullPointerException("The budget cannot be null.");
    }
    budgets.add(budget);
  }

  /**
   * The method removes a budget from the list of budgets with budget ID.
   *
   * @param budgetId the ID of the budget to be removed.
   * @return true or false depending on if the budget was removed.
   * @throws NullPointerException id the budget ID is null.
   */
  public boolean removeBudget(String budgetId) throws NullPointerException {
    if (budgetId == null) {
      throw new NullPointerException("Budget ID cannot be null.");
    }
    return budgets.removeIf(budget -> budget.getBudgetId().equals(budgetId.trim()));
  }

  /**
   * The method retrieves the list of budgets.
   *
   * @return list of budgets.
   */
  public List<Budget> getBudgets() {
    return budgets;
  }

  /**
   * The method checks for equality between objects.
   *
   * @param o the object to which it is being compared.
   * @return a boolean value which indicate whether they are equal or not.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    Account account = (Account) o;
    return Objects.equals(getBudgets(), account.getBudgets());
  }

  /**
   * The method generates a hash value for the object.
   *
   * @return hash value for the object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), getBudgets());
  }
}