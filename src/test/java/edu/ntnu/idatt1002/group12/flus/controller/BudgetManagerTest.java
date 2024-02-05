package edu.ntnu.idatt1002.group12.flus.controller;

import static org.junit.jupiter.api.Assertions.*;
import edu.ntnu.idatt1002.group12.flus.model.Account;
import edu.ntnu.idatt1002.group12.flus.model.Budget;
import edu.ntnu.idatt1002.group12.flus.model.goals.FinancialGoal;
import edu.ntnu.idatt1002.group12.flus.model.transactions.Expense;
import edu.ntnu.idatt1002.group12.flus.model.transactions.Income;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * The class tests the BudgetManager class.
 *
 * @author Ramitn Samavat
 * @version 1.0
 * @since April 12, 2023.
 */
class BudgetManagerTest {

  private final AccountRegister accountRegister = AccountRegister.getInstance();
  private BudgetManager budgetManager;
  private Account account;
  private Budget budget;
  private Income income;
  private Expense expense;
  private FinancialGoal financialGoal;

  @BeforeEach
  void setUp() {
    budgetManager = new BudgetManager();
    account = new Account("Username", "Password123", "kl@test.no", "8123456789");
    budget = new Budget("BudgetId");
    income = new Income("Work", 4000);
    expense = new Expense("Grocery", 2500);
    financialGoal = new FinancialGoal(250);

    accountRegister.registerAccount(account.getUsername(), "Password123", account.getEmail(), account.getPhoneNumber());
    budgetManager.createBudget(account.getUsername(), budget.getBudgetId());
    budgetManager.addIncomeToBudget(account.getUsername(), budget.getBudgetId(), income.getDescription(), income.getAmount());
    budgetManager.addExpenseToBudget(account.getUsername(), budget.getBudgetId(), expense.getDescription(), expense.getAmount());
    budgetManager.addGoalToBudget(account.getUsername(), budget.getBudgetId(), financialGoal.getMinimumMoneyValue());
  }

  @AfterEach
  void tearDown() {
    accountRegister.deleteAccount(account.getUsername(), account.getUsername());
  }

  @Nested
  @DisplayName("Create and delete budget tests")
  class BudgetManagementTests {
    @Test
    @DisplayName("Should create budget")
    void shouldCreateBudget(){
      Budget testBudget = new Budget("Test budget ID");
      budgetManager.createBudget(account.getUsername(), testBudget.getBudgetId());
      assertTrue(accountRegister.findAccount(account.getUsername()).getBudgets().contains(testBudget));
    }

    @Test
    @DisplayName("Should delete budget")
    void shouldDeleteBudget() {
      assertTrue(budgetManager.deleteBudget(account.getUsername(), budget.getBudgetId()));
      assertFalse(accountRegister.findAccount(account.getUsername()).getBudgets().contains(budget));
    }

    @Test
    @DisplayName("Should not create budget throws NullPointerException")
    void shouldNotCreateBudgetThrowsNullPointerException() {
      assertThrows(NullPointerException.class,
              () -> budgetManager.createBudget(null, budget.getBudgetId()));

      assertThrows(NullPointerException.class,
              () -> budgetManager.createBudget(account.getUsername(), null));
    }

    @Test
    @DisplayName("Should not create budget throws IllegalArgumentException")
    void shouldNotCreateBudgetThrowsIllegalArgumentException() {
      assertThrows(IllegalArgumentException.class,
              () -> budgetManager.createBudget(account.getUsername(), budget.getBudgetId()));
    }

    @Test
    @DisplayName("Should not delete budget throws NullPointerException")
    void shouldNotDeleteBudgetThrowsNullPointerException() {
      assertThrows(NullPointerException.class,
              () -> budgetManager.deleteBudget(account.getUsername(), null));

      assertThrows(NullPointerException.class,
              () -> budgetManager.deleteBudget(null, budget.getBudgetId()));
    }
  }

  @Nested
  @DisplayName("Budget income tests")
  class BudgetIncomeTests {
    @Test
    @DisplayName("Should add income to budget")
    void shouldAddIncomeToBudget() {
      Income testIncome = new Income("Test", 200);

      budgetManager.addIncomeToBudget(account.getUsername(), budget.getBudgetId(),
              testIncome.getDescription(), testIncome.getAmount());

      assertTrue(budgetManager.getBudget(account.getUsername(), budget.getBudgetId()).getIncomes().contains(testIncome));
    }

    @Test
    @DisplayName("Should remove income from budget")
    void shouldRemoveIncomeFromBudget() {
      assertTrue(budgetManager.removeIncomeFromBudget(account.getUsername(), budget.getBudgetId(), income));
      assertFalse(budgetManager.getBudget(account.getUsername(), budget.getBudgetId()).getIncomes().contains(income));
    }

    @Test
    @DisplayName("Should not add income to budget throws NullPointerException")
    void shouldNotAddIncomeToBudgetThrowsNullPointerException() {
      assertThrows(NullPointerException.class, () -> budgetManager
              .addIncomeToBudget(null, budget.getBudgetId(), income.getDescription(), income.getAmount()));
      assertThrows(NullPointerException.class, () -> budgetManager
              .addIncomeToBudget(account.getUsername(), null, income.getDescription(), income.getAmount()));
      assertThrows(NullPointerException.class, () -> budgetManager
              .addIncomeToBudget(account.getUsername(), budget.getBudgetId(), null, income.getAmount()));

    }

    @Test
    @DisplayName("Should not add income to budget throws NoSuchElementException")
    void shouldNotAddIncomeToBudgetThrowsNoSuchElementException() {
      assertThrows(NoSuchElementException.class, () -> budgetManager
              .addIncomeToBudget(account.getUsername(), "Non existing budget",
                      income.getDescription(), income.getAmount()));
    }

    @Test
    @DisplayName("Should not remove income form budget throws NullPointerException")
    void shouldNotRemoveIncomeFromBudgetThrowsNullPointerException() {
      assertThrows(NullPointerException.class, () -> budgetManager
              .removeIncomeFromBudget(null, budget.getBudgetId(), income));
      assertThrows(NullPointerException.class, () -> budgetManager
              .removeIncomeFromBudget(account.getUsername(), null, income));
      assertThrows(NullPointerException.class, () -> budgetManager
              .removeIncomeFromBudget(account.getUsername(), budget.getBudgetId(), null));
    }

    @Test
    @DisplayName("Should not remove income from budget throws NoSuchElementException")
    void shouldNotRemoveIncomeFromBudgetThrowsNoSuchElementException() {
      assertThrows(NoSuchElementException.class, () -> budgetManager
              .removeIncomeFromBudget(account.getUsername(), "Non existing budget", income));
    }
  }

  @Nested
  @DisplayName("Budget expense tests")
  class BudgetExpenseTests {
    @Test
    @DisplayName("Should add expense to budget")
    void shouldAddExpenseToBudget() {
      Expense testExpense = new Expense("Test", 500);

      budgetManager.addExpenseToBudget(account.getUsername(), budget.getBudgetId(),
              testExpense.getDescription(), testExpense.getAmount());

      assertTrue(budgetManager.getBudget(account.getUsername(), budget.getBudgetId())
              .getExpenses().contains(testExpense));
    }

    @Test
    @DisplayName("Should remove expense from budget")
    void shouldRemoveIncomeFromBudget() {
      assertTrue(budgetManager.removeExpenseFromBudget(account.getUsername(), budget.getBudgetId(), expense));
      assertFalse(budgetManager.getBudget(account.getUsername(), budget.getBudgetId()).getExpenses().contains(expense));
    }

    @Test
    @DisplayName("Should not add expense to budget throws NullPointerException")
    void shouldNotAddIncomeToBudgetThrowsNullPointerException() {
      assertThrows(NullPointerException.class, () -> budgetManager
              .addExpenseToBudget(null, budget.getBudgetId(), expense.getDescription(), expense.getAmount()));
      assertThrows(NullPointerException.class, () -> budgetManager
              .addExpenseToBudget(account.getUsername(), null, expense.getDescription(), expense.getAmount()));
      assertThrows(NullPointerException.class, () -> budgetManager
              .addExpenseToBudget(account.getUsername(), budget.getBudgetId(), null, expense.getAmount()));
    }

    @Test
    @DisplayName("Should not add expense to budget throws NoSuchElementException")
    void shouldNotAddIncomeToBudgetThrowsNoSuchElementException() {
      assertThrows(NoSuchElementException.class, () -> budgetManager
              .addExpenseToBudget(account.getUsername(), "Non existing budget",
                      expense.getDescription(), expense.getAmount()));
    }

    @Test
    @DisplayName("Should not remove expense form budget throws NullPointerException")
    void shouldNotRemoveIncomeFromBudgetThrowsNullPointerException() {
      assertThrows(NullPointerException.class, () -> budgetManager
              .removeExpenseFromBudget(null, budget.getBudgetId(), expense));
      assertThrows(NullPointerException.class, () -> budgetManager
              .removeExpenseFromBudget(account.getUsername(), null, expense));
      assertThrows(NullPointerException.class, () -> budgetManager
              .removeExpenseFromBudget(account.getUsername(), budget.getBudgetId(), null));
    }

    @Test
    @DisplayName("Should not remove expense from budget throws NoSuchElementException")
    void shouldNotRemoveIncomeFromBudgetThrowsNoSuchElementException() {
      assertThrows(NoSuchElementException.class, () -> budgetManager
              .removeExpenseFromBudget(account.getUsername(), "Non existing budget", expense));
    }
  }

  @Nested
  @DisplayName("Budget goal tests")
  class BudgetGoalTests {
    @Test
    @DisplayName("Should add goal to budget")
    void shouldAddGoalToBudget() {
      FinancialGoal testFinancialGoal = new FinancialGoal(300);

      budgetManager.addGoalToBudget(account.getUsername(), budget.getBudgetId(),
              testFinancialGoal.getMinimumMoneyValue());

      List<FinancialGoal> expectedList = new ArrayList<>();
      expectedList.add(financialGoal);
      expectedList.add(testFinancialGoal);

      List<FinancialGoal> actualList = budgetManager
              .getBudget(account.getUsername(), budget.getBudgetId())
              .getFinancialGoals();

      assertEquals(expectedList, actualList);
    }

    @Test
    @DisplayName("Should remove goal from budget")
    void shouldRemoveGoalFromBudget() {
      assertTrue(budgetManager.removeGoalFromBudget(account.getUsername(),
              budget.getBudgetId(), financialGoal.getMinimumMoneyValue()));
      assertFalse(budgetManager.getBudget(account.getUsername(), budget.getBudgetId())
              .getFinancialGoals().contains(financialGoal));
    }


    @Test
    @DisplayName("Should not add goal to budget throws IllegalArgumentException")
    void shouldNotAddGoalToBudgetThrowsIllegalArgumentException() {
      assertThrows(IllegalArgumentException.class, () -> budgetManager
              .addGoalToBudget(account.getUsername(), budget.getBudgetId(), financialGoal.getMinimumMoneyValue()));
    }

    @Test
    @DisplayName("Should not add goal to budget throws NullPointerException")
    void shouldNotGoalToBudgetThrowsNullPointerException() {
      assertThrows(NullPointerException.class, () -> budgetManager
              .addGoalToBudget(null, budget.getBudgetId(), financialGoal.getMinimumMoneyValue()));
      assertThrows(NullPointerException.class, () -> budgetManager
              .addGoalToBudget(account.getUsername(), null, financialGoal.getMinimumMoneyValue()));
    }

    @Test
    @DisplayName("Should not add goal to budget throws NoSuchElementException")
    void shouldNotGoalToBudgetThrowsNoSuchElementException() {
      assertThrows(NoSuchElementException.class, () -> budgetManager
              .addGoalToBudget(account.getUsername(), "Non existing budget", financialGoal.getMinimumMoneyValue()));
    }

    @Test
    @DisplayName("Should not remove goal from budget throws NullPointerException")
    void shouldNotRemoveGoalFromBudgetThrowsNullPointerException() {
      assertThrows(NullPointerException.class, () -> budgetManager
              .removeGoalFromBudget(null, budget.getBudgetId(), financialGoal.getMinimumMoneyValue()));
      assertThrows(NullPointerException.class, () -> budgetManager
              .removeGoalFromBudget(account.getUsername(), null, financialGoal.getMinimumMoneyValue()));
    }

    @Test
    @DisplayName("Should not remove goal from budget throws NoSuchElementException")
    void shouldNotRemoveGoalFromBudgetThrowsNoSuchElementException() {
      assertThrows(NoSuchElementException.class, () -> budgetManager
              .removeGoalFromBudget(account.getUsername(), "Non existing budget", financialGoal.getMinimumMoneyValue()));
    }
  }
}