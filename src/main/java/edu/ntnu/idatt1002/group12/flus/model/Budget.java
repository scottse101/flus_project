package edu.ntnu.idatt1002.group12.flus.model;

import edu.ntnu.idatt1002.group12.flus.model.goals.FinancialGoal;
import edu.ntnu.idatt1002.group12.flus.model.transactions.Expense;
import edu.ntnu.idatt1002.group12.flus.model.transactions.Income;
import edu.ntnu.idatt1002.group12.flus.utils.DateTimeUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The class represents a budget for the account,
 * and contains income, expenses and goals.
 *
 * @author Ramtin Samavat, Stian Lyng
 * @version 1.0
 * @since April 07, 2023
 */
public class Budget implements Serializable {

  private final String budgetId;
  private final DateTimeUtil date;
  private double balance;
  private final List<Income> incomes;
  private final List<Expense> expenses;
  private final List<FinancialGoal> financialGoals;

  /**
   * The constructor creates a budget object with the given ID, initializing its balance to zero
   * and creating empty lists of incomes, expenses, and goals.
   *
   * @param budgetId the ID of the budget.
   * @throws IllegalArgumentException if the budgetId is blank.
   * @throws NullPointerException     if the budgetId is null.
   */
  public Budget(String budgetId) throws IllegalArgumentException, NullPointerException {
    if (budgetId.isBlank()) {
      throw new IllegalArgumentException("Budget ID cannot be blank.");
    }
    this.budgetId = Objects.requireNonNull(budgetId.trim(), "Budget ID cannot be null.");
    this.balance = 0;
    this.date = new DateTimeUtil();
    this.incomes = new ArrayList<>();
    this.expenses = new ArrayList<>();
    this.financialGoals = new ArrayList<>();
  }

  /**
   * The method retrieves the budget ID.
   *
   * @return budget ID.
   */
  public String getBudgetId() {
    return budgetId;
  }

  /**
   * The method retrieves the timestamp of the budget.
   *
   * @return timestamp.
   */
  public String getDate() {
    return date.getFormattedDateTime();
  }

  /**
   * The method retrieves the current balance of the budget.
   *
   * @return the balance.
   */
  public double getBalance() {
    calculateBalance();
    return this.balance;
  }

  /**
   * The method retrieves the list of incomes.
   *
   * @return list of incomes.
   */
  public List<Income> getIncomes() {
    return incomes;
  }

  /**
   * The method retrieves the list of expenses.
   *
   * @return list of expenses.
   */
  public List<Expense> getExpenses() {
    return expenses;
  }

  /**
   * The method retrieves the list of financial goals.
   *
   * @return list of financial goals.
   */
  public List<FinancialGoal> getFinancialGoals() {
    return financialGoals;
  }

  /**
   * The method checks if the balance is positive.
   *
   * @return boolean depending on if the balance is positive.
   */
  public boolean checkIfBalancePositive() {
    return getBalance() >= 0;
  }

  /**
   * The method add incomes to the list of incomes.
   *
   * @param income income to be added.
   * @throws NullPointerException if the income is null;
   */
  public void addIncome(Income income) throws NullPointerException {
    validateIncome(income);
    incomes.add(income);
  }

  /**
   * The method removes the given income from this list of expenses.
   *
   * @param income the income to be removed.
   * @return true if an income was found and removed, false otherwise.
   * @throws NullPointerException if the income is null.
   */
  public boolean removeIncome(Income income) throws NullPointerException {
    validateIncome(income);
    return incomes.remove(income);
  }

  /**
   * The method adds expense to the list of expenses.
   *
   * @param expense expense to be added.
   * @throws NullPointerException if the expense is null.
   */
  public void addExpense(Expense expense) throws NullPointerException {
    validateExpense(expense);
    expenses.add(expense);
  }

  /**
   * The method removes the given expense from this list of expenses.
   *
   * @param expense the expense to be removed.
   * @return true if an expense found and removed, false otherwise.
   * @throws NullPointerException if the description or date is null.
   */
  public boolean removeExpense(Expense expense) throws NullPointerException {
    validateExpense(expense);
    return expenses.remove(expense);
  }

  /**
   * The method adds a financial goal to the list of financial goals.
   *
   * @param  financialGoal the financial goal to be added.
   * @throws NullPointerException if the financial goal is null.
   */
  public void addGoal(FinancialGoal financialGoal) throws NullPointerException {
    if (financialGoal == null) {
      throw new NullPointerException("Goal cannot be null.");
    }
    financialGoals.add(financialGoal);
  }

  /**
   * The method removes a financial goal from the list of financial goals.
   *
   * @param goalValue the value of the financial goal.
   * @return true if the goal was removed, and false otherwise.
   */
  public boolean removeGoal(double goalValue) throws NullPointerException {
    return financialGoals.removeIf(financialGoal -> financialGoal
            .getMinimumMoneyValue() == goalValue);
  }

  /**
   * The method calculates the balance of the budget.
   */
  private void calculateBalance() {
    double totalIncome = 0;
    double totalExpenses = 0;
    for (Income income : incomes) {
      totalIncome += income.getAmount();
    }
    for (Expense expense : expenses) {
      totalExpenses += expense.getAmount();
    }
    this.balance = totalIncome - totalExpenses;
  }

  /**
   * The method validates the given income.
   *
   * @param income the income to be validated.
   * @throws NullPointerException if the income is null.
   */
  private void validateIncome(Income income) throws NullPointerException {
    if (income == null) {
      throw new NullPointerException("Income cannot be null.");
    }
  }

  /**
   * The method validates the given expense.
   *
   * @param expense the expense to be validated.
   * @throws NullPointerException if the expense is null.
   */
  private void validateExpense(Expense expense) throws NullPointerException {
    if (expense == null) {
      throw new NullPointerException("Expense cannot be null.");
    }
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
    Budget budget = (Budget) o;
    return Double.compare(budget.getBalance(), getBalance()) == 0
            && Objects.equals(getBudgetId(), budget.getBudgetId())
            && Objects.equals(getDate(), budget.getDate())
            && Objects.equals(getIncomes(), budget.getIncomes())
            && Objects.equals(getExpenses(), budget.getExpenses())
            && Objects.equals(getFinancialGoals(), budget.getFinancialGoals());
  }

  /**
   * Creates a hashcode for the object.
   *
   * @return Hashcode for the object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(getBudgetId(), getDate(), getBalance(),
            getIncomes(), getExpenses(), getFinancialGoals());
  }
}
