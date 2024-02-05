package edu.ntnu.idatt1002.group12.flus.model.transactions;

import edu.ntnu.idatt1002.group12.flus.model.transactions.Expense;
import edu.ntnu.idatt1002.group12.flus.model.transactions.Income;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * The class tests the Expense class.
 *
 * @author R.Samavat
 * @version 1.0
 */
class ExpenseTest {
  private Expense expense;

  @BeforeEach
  void setUp() {
    expense = new Expense("Grocery", 500);
  }

  @Test
  @DisplayName("Test constructor valid input")
  void testConstructorValidInput() {
    String validDescription = "Test description";
    double validAmount = 100;
    Income testConstructor = new Income(validDescription, validAmount);
    assertEquals(validDescription, testConstructor.getDescription());
    assertEquals(validAmount, testConstructor.getAmount());
  }

  @Test
  @DisplayName("Test constructor invalid input throws IllegalArgumentException")
  void testConstructorInvalidInputThrowsIllegalArgumentException() {
    String validDescription = "Test description";
    String invalidDescription = "";
    double validAmount = 100;
    double invalidAmount = -100;
    assertThrows(IllegalArgumentException.class, () -> new Income(invalidDescription, validAmount));
    assertThrows(IllegalArgumentException.class, () -> new Income(validDescription, invalidAmount));
  }

  @Test
  @DisplayName("Test constructor invalid input throws NullPointerException")
  void testConstructorInvalidInputThrowsNullPointerException() {
    String invalidDescription = null;
    double validAmount = 100;
    assertThrows(NullPointerException.class, () -> new Income(invalidDescription, validAmount));
  }

  @Test
  @DisplayName("Should get description")
  void shouldGetDescription() {
    String expectedDescription = "Grocery";
    String actualDescription = expense.getDescription();
    assertEquals(expectedDescription, actualDescription);
  }

  @Test
  @DisplayName("Should get amount")
  void shouldGetAmount() {
    double expectedAmount = 500;
    double actualAmount = expense.getAmount();
    assertEquals(expectedAmount, actualAmount);
  }

  @Test
  void testToString() {
    String expectedString = "Expense: Description = Grocery, Amount = 500.0";
    String actualString = expense.toString();
    assertEquals(expectedString, actualString);
  }
}