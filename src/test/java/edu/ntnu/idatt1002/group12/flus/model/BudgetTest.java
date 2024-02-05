package edu.ntnu.idatt1002.group12.flus.model;

import edu.ntnu.idatt1002.group12.flus.model.goals.FinancialGoal;
import edu.ntnu.idatt1002.group12.flus.model.goals.Goal;
import edu.ntnu.idatt1002.group12.flus.model.Budget;
import edu.ntnu.idatt1002.group12.flus.model.transactions.Expense;
import edu.ntnu.idatt1002.group12.flus.model.transactions.Income;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The class tests the Budget class.
 *
 * @author R.Samavat
 * @version 1.0
 * @since April 12, 2023
 */
class BudgetTest {

  private Budget budget;
  private Income income;
  private Expense expense;

  @BeforeEach
  void setUp() {
    budget = new Budget("BudgetID");
    income = new Income("Work", 3500);
    expense = new Expense("Grocery", 500);
    budget.addIncome(income);
    budget.addExpense(expense);
  }

  @Nested
  @DisplayName("Constructor tests")
  class constructorTests {
    @Test
    @DisplayName("Test constructor valid input")
    void testConstructorValidInput() {
      String validBudgetId = "Valid ID";
      Budget testConstructor = new Budget(validBudgetId);
      assertEquals(validBudgetId, testConstructor.getBudgetId());
    }

    @Test
    @DisplayName("Test constructor invalid budget ID throws IllegalArgumentException")
    void testConstructorInvalidBudgetIdThrowsIllegalArgumentException() {
      String invalidBudgetId = "";
      assertThrows(IllegalArgumentException.class, () -> new Budget(invalidBudgetId));
    }

    @Test
    @DisplayName("Test constructor invalid budget ID throws NullPointerException")
    void testConstructorInvalidBudgetIdThrowsNullPointerException() {
      assertThrows(NullPointerException.class, () -> new Budget(null));
    }
  }

  @Nested
  @DisplayName("Budget information tests")
  class AccountBalanceTests {
    @Test
    @DisplayName("Should get budget ID")
    void getBudgetId() {
      String expectedBudgetId = "BudgetID";
      String actualBudgetId = budget.getBudgetId();
      assertEquals(expectedBudgetId, actualBudgetId);
    }

    @Test
    @DisplayName("Should get balance")
    void shouldGetBalance() {
      double expectedBalance = 3000.0;
      double actualBalance = budget.getBalance();
      assertEquals(expectedBalance, actualBalance);
    }

    @Test
    @DisplayName("Check if the balance is positive true")
    void checkIfBalancePositiveTrue() {
      assertTrue(budget.checkIfBalancePositive());
    }

    @Test
    @DisplayName("Check if the balance is positive false")
    void checkIfBalancePositiveFalse() {
      budget.removeIncome(income);
      assertFalse(budget.checkIfBalancePositive());
    }
  }

  @Nested
  @DisplayName("Income tests")
  class IncomeTests {
    @Test
    @DisplayName("Should add income")
    void shouldAddIncome() {
      Income testIncome = new Income("Test", 4000.0);
      budget.addIncome(testIncome);
      assertTrue(budget.getIncomes().contains(testIncome));
    }

    @Test
    @DisplayName("Should not add income throws NullPointerException")
    void shouldNotAddIncomeThrowsNullPointerException() {
      assertThrows(NullPointerException.class, () -> budget.addIncome(null));
    }

    @Test
    @DisplayName("Should remove income")
    void shouldRemoveIncome() {
      budget.removeIncome(income);
      assertFalse(budget.getIncomes().contains(income));
    }

    @Test
    @DisplayName("Should not remove income throws NullPointerException")
    void shouldNotRemoveIncomeThrowsNullPointerException() {
      assertThrows(NullPointerException.class, () -> budget.removeIncome(null));
    }

    @Test
    @DisplayName("Should get list incomes")
    void shouldGetListIncomes() {
      List<Income> expectedList = new ArrayList<>();
      expectedList.add(income);
      List<Income> actualList = budget.getIncomes();
      assertEquals(expectedList, actualList);
    }
  }

  @Nested
  @DisplayName("Expense tests")
  class ExpenseTests {
    @Test
    @DisplayName("Should add expense")
    void shouldAddExpense() {
      Expense testExpense = new Expense("Buss", 350);
      budget.addExpense(testExpense);
      assertTrue(budget.getExpenses().contains(testExpense));
    }

    @Test
    @DisplayName("Should not add expense throws NullPointerException")
    void shouldNotAddExpenseThrowsNullPointerException() {
      assertThrows(NullPointerException.class, () -> budget.addExpense(null));
    }

    @Test
    @DisplayName("Should remove expense")
    void shouldRemoveExpense() {
        budget.removeExpense(expense);
        assertFalse(budget.getExpenses().contains(expense));
    }

    @Test
    @DisplayName("Should not remove expense throws NullPointerException")
    void shouldNotRemoveExpenseThrowsNullPointerException() {
      assertThrows(NullPointerException.class, () -> budget.removeExpense(null));
    }

    @Test
    @DisplayName("Should get list expenses")
    void shouldGetListExpenses() {
      List<Expense> expectedList = new ArrayList<>();
      expectedList.add(expense);
      List<Expense> actualList = budget.getExpenses();
      assertEquals(expectedList, actualList);
    }
  }

  @Nested
  @DisplayName("Goal tests")
  class GoalTests {
    FinancialGoal goal = new FinancialGoal(500);
    FinancialGoal invalidGoal = null;
    @Test
    @DisplayName("Should add goal")
    void shouldAddGoal() {
      budget.addGoal(goal);
      assertTrue(budget.getFinancialGoals().contains(goal));
    }

    @Test
    @DisplayName("Should not add goal throws NullPointerException")
    void shouldNotAddGoalThrowsNullPointerException() {
      assertThrows(NullPointerException.class, () -> budget.addGoal(invalidGoal));
    }

    @Test
    @DisplayName("Should remove goal")
    void shouldRemoveGoal() {
      budget.addGoal(goal);
      budget.removeGoal(goal.getMinimumMoneyValue());
      assertFalse(budget.getFinancialGoals().contains(goal));
    }

    @Test
    @DisplayName("Should not remove goal throws NullPointerException")
    void shouldNotRemoveGoalThrowsNullPointerException() {
      assertThrows(NullPointerException.class, () -> budget.removeGoal(invalidGoal.getMinimumMoneyValue()));
    }

    @Test
    @DisplayName("Should get list goals")
    void shouldGetListGoals() {
      List<Goal> expectedList = new ArrayList<>();
      expectedList.add(goal);
      budget.addGoal(goal);
      List<FinancialGoal> actualList = budget.getFinancialGoals();
      assertEquals(expectedList, actualList);
    }
  }
}

