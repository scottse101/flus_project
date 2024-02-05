package edu.ntnu.idatt1002.group12.flus.model.goals;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idatt1002.group12.flus.model.Budget;
import edu.ntnu.idatt1002.group12.flus.model.goals.FinancialGoal;
import edu.ntnu.idatt1002.group12.flus.model.transactions.Income;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class FinancialGoalTest {

  private Budget budget;

  @BeforeEach
  void setUp(){
    budget = new Budget("BudgetID");
    budget.addIncome(new Income("test income", 15));
  }

  @Nested
  @DisplayName("Create Financial goal")
  class createFinancialGoalTests {

    @Test
    @DisplayName("Should create goal")
    void createGoalPositive() {
      FinancialGoal goalTest = new FinancialGoal(250);
      assertEquals(250, goalTest.getMinimumMoneyValue());
    }

    @Test
    @DisplayName("Should throw error while creating goal")
    void createGoalNegative() {
      assertThrows(IllegalArgumentException.class, () ->  new FinancialGoal(-10));
    }
  }

  @Nested
  @DisplayName("Test Completed method for financial goal")
  class completedFinancialGoalTests {
    FinancialGoal completed = new FinancialGoal(10);
    FinancialGoal notCompleted = new FinancialGoal(20);
    Budget nullBudget = null;

    @Test
    @DisplayName("Should complete goal")
    void completeGoalPositive() {
      assertTrue(completed.completed(budget));
    }

    @Test
    @DisplayName("Should not complete goal")
    void notCompleteGoalPositive() {
      assertFalse(notCompleted.completed(budget));
    }

    @Test
    @DisplayName("Should throw exception")
    void completeGoalNegative() {
      assertThrows(NullPointerException.class, () -> completed.completed(nullBudget));
    }
  }
}