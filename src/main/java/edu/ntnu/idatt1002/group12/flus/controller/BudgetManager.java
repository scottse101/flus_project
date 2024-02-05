package edu.ntnu.idatt1002.group12.flus.controller;

import edu.ntnu.idatt1002.group12.flus.model.Account;
import edu.ntnu.idatt1002.group12.flus.model.Budget;
import edu.ntnu.idatt1002.group12.flus.model.goals.FinancialGoal;
import edu.ntnu.idatt1002.group12.flus.model.transactions.Expense;
import edu.ntnu.idatt1002.group12.flus.model.transactions.Income;
import java.util.NoSuchElementException;

/**
 * The class represents a budget manager, and is responsible for
 * everything related to the account's budgets.
 *
 * @author Ramtin Samavat and Stian Stræte
 * @version 1.0
 * @since April 12, 2023
 */
public class BudgetManager {

  private final AccountRegister register;

  /**
   * Constructor for making an object of BudgetManger.
   * The constructor initializes the list of accounts.
   */
  public BudgetManager() {
    this.register = AccountRegister.getInstance();
  }

  /**
   * The method is a helper method for the other methods in the class.
   * It retrieves a budget from the list of budgets in the account.
   *
   * @param account  the account that holds the budget.
   * @param budgetId the budget id used to retrieve the budget.
   * @return the budget to be retrieved.
   * @throws NullPointerException if the account or the budget id is null.
   */
  private Budget findBudget(Account account, String budgetId) throws NullPointerException {
    if (account == null) {
      throw new NullPointerException("Account cannot be null.");
    }
    validateBudgetId(budgetId);

    return account.getBudgets()
            .stream()
            .filter(budget -> budget.getBudgetId().equals(budgetId.trim()))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException("The budget does not exist."));
  }

  /**
   * The method creates a new budget with the specified ID and adds it to the
   * account with the given username.
   *
   * @param username the username of the account to add the budget to.
   * @param budgetId the ID of the budget to create.
   * @throws NullPointerException if account or budgetId is null.
   * @throws IllegalArgumentException if a budget with the same ID already exists in the account.
   */
  public void createBudget(String username, String budgetId) throws NullPointerException,
          IllegalArgumentException {
    validateBudgetId(budgetId);
    boolean invalidBudgetId = register.findAccount(username).getBudgets()
            .stream()
            .anyMatch(budget -> budget.getBudgetId().equals(budgetId.trim()));
    if (invalidBudgetId) {
      throw new IllegalArgumentException("A budget with the same budget ID already exists.");
    }
    register.findAccount(username).addBudget(new Budget(budgetId.trim()));
  }

  /**
   * The method deletes the budget with the specified ID from the account with the given username.
   *
   * @param username the username of the account to remove the budget from.
   * @param budgetId the ID of the budget to delete.
   * @return true if the budget was successfully removed, and false otherwise.
   * @throws NullPointerException if account of budgetId is null.
   * @throws NoSuchElementException if the account does not exist.
   */
  public boolean deleteBudget(String username, String budgetId)
          throws NullPointerException, NoSuchElementException {
    validateBudgetId(budgetId);
    return register.findAccount(username).removeBudget(budgetId);
  }

  /**
   * The method adds income to the specified budget in the account
   * associated with the given username.
   *
   * @param username the username associated with the account containing the budget.
   * @param budgetId the ID of the budget to which the income is to be added.
   * @param description the description of the income.
   * @param amount the amount of the income.
   * @throws NullPointerException if either the username, budgetId, or description is null.
   * @throws IllegalArgumentException if the description is blank or if the amount is below zero.
   * @throws NoSuchElementException if the account or the budget does not exist.
   */
  public void addIncomeToBudget(String username, String budgetId, String description, double amount)
          throws NullPointerException, IllegalArgumentException, NoSuchElementException {
    validateDescription(description);
    findBudget(register.findAccount(username), budgetId)
            .addIncome(new Income(description.trim(), amount));
  }

  /**
   * The method removes income from the specified budget in the account
   * associated with the given username.
   *
   * @param username the username associated with the account containing the budget.
   * @param budgetId the ID of the budget to which the income is to be removed.
   * @param income the income to be removed.
   * @return true if the income was successfully removed, false otherwise.
   * @throws NullPointerException if either the username, budgetId, or income is null.
   * @throws NoSuchElementException if the account or the budget does not exist.
   */
  public boolean removeIncomeFromBudget(String username, String budgetId, Income income)
          throws NullPointerException, NoSuchElementException {
    if (income == null) {
      throw new NullPointerException("Income cannot be null.");
    }
    return findBudget(register.findAccount(username), budgetId).removeIncome(income);
  }

  /**
   * The method adds expense to the specified budget in the account
   * associated with the given username.
   *
   * @param username the username associated with the account containing the budget.
   * @param budgetId the ID of the budget to which the expense is to be added.
   * @param description the description of the expense.
   * @param amount the amount of the expense.
   * @throws NullPointerException if either the username, budgetId, or description is null.
   * @throws IllegalArgumentException if the description is blank or if the amount is below zero.
   * @throws NoSuchElementException if the account or the budget does not exist.
   */
  public void addExpenseToBudget(String username, String budgetId,
                                 String description, double amount)
          throws NullPointerException, IllegalArgumentException, NoSuchElementException {
    validateDescription(description);
    findBudget(register.findAccount(username), budgetId)
            .addExpense(new Expense(description, amount));
  }

  /**
   * The method removes expense from the specified budget in the account
   * associated with the given username.
   *
   * @param username the username associated with the account containing the budget.
   * @param budgetId the ID of the budget to which the expense is to be removed.
   * @param expense the expense to be removed.
   * @return true if the expense was successfully removed, false otherwise.
   * @throws NullPointerException if either the username, budgetId, or description is null.
   * @throws NoSuchElementException if the account or the budget does not exist.
   */
  public boolean removeExpenseFromBudget(String username, String budgetId, Expense expense)
          throws NullPointerException, NoSuchElementException {
    if (expense == null) {
      throw new NullPointerException("Expense cannot be null.");
    }
    return findBudget(register.findAccount(username), budgetId).removeExpense(expense);
  }

  /**
   * The method adds goal to the specified budget in the account
   * associated with the given username.
   *
   * @param username the username associated with the account containing the budget.
   * @param budgetId the ID of the budget to which the goal is to be added.
   * @param goalValue the value of the goal.
   * @throws NullPointerException if the username or the budgetId is null.
   * @throws IllegalArgumentException if the value is below zero or is already used.
   * @throws NoSuchElementException if the account or the budget does not exist.
   */
  public void addGoalToBudget(String username, String budgetId, double goalValue)
          throws NullPointerException, IllegalArgumentException, NoSuchElementException {
    boolean usedGoalValue = findBudget(register.findAccount(username), budgetId)
            .getFinancialGoals()
            .stream()
            .anyMatch(financialGoal -> financialGoal.getMinimumMoneyValue() == goalValue);
    if (usedGoalValue) {
      throw new IllegalArgumentException("A goal with this value already exists.");
    }
    findBudget(register.findAccount(username), budgetId).addGoal(new FinancialGoal(goalValue));
  }

  /**
   * The method removes goal from the specified budget in the account
   * associated with the given username.
   *
   * @param username the username associated with the account containing the budget.
   * @param budgetId the ID of the budget to which the goal is to be removed.
   * @param goalValue the value of the goal.
   * @return true if the goal was successfully removed, false otherwise.
   * @throws NullPointerException if the username or the budgetId is null.
   * @throws NoSuchElementException if the account or the budget does not exist.
   */
  public boolean removeGoalFromBudget(String username, String budgetId, double goalValue)
          throws NullPointerException, NoSuchElementException {
    return findBudget(register.findAccount(username), budgetId).removeGoal(goalValue);
  }

  /**
   * The method retrieves a deep copy of the Budget object corresponding
   * to the specified username and budget ID.
   *
   * @param username the username of the account with the budget.
   * @param budgetId the budget ID of the budget to be retrieved.
   * @return a deep copy of the Budget object.
   * @throws NullPointerException if either username or budgetID is null.
   * @throws NoSuchElementException if the account does not exist.
   * @throws UnsupportedOperationException if cloning is not supported.
   */
  public Budget getBudget(String username, String budgetId)
          throws NullPointerException, NoSuchElementException, UnsupportedOperationException {
    return findBudget(register.findAccount(username), budgetId);
  }

  /**
   * The method validates that the given budget ID is not null.
   *
   * @param budgetId the budget ID to validate.
   * @throws NullPointerException if the budget ID is null.
   */
  private void validateBudgetId(String budgetId) throws NullPointerException {
    if (budgetId == null) {
      throw new NullPointerException("Budget ID cannot be null.");
    }
  }

  /**
   * The method validates that the given transaction description is not null.
   *
   * @param description the transaction description to validate.
   * @throws NullPointerException if the description is null.
   */
  private void validateDescription(String description) throws NullPointerException {
    if (description == null) {
      throw new NullPointerException("Description cannot be null.");
    }
  }
}