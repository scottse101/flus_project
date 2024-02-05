package edu.ntnu.idatt1002.group12.flus.model;

import edu.ntnu.idatt1002.group12.flus.model.Account;
import edu.ntnu.idatt1002.group12.flus.model.Budget;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

/**
 * The class tests the Account class.
 *
 * @author R.Samavat
 * @version 1.0
 */
class AccountTest {
  private Account account;

  @BeforeEach
  void setUp() {
    account = new Account("Name", "Password123", "email@ntnu.no", "41789236");
  }

  @Nested
  @DisplayName("Constructor tests")
  class ConstructorTests {
    private final String validUsername = "Test name";
    private final String validPassword = "TestPassword";
    private final String validEmail = "Email@Test";
    private final String validPhoneNumber = "42769796";


    @Test
    @DisplayName("Test constructor valid input")
    void testConstructorValidInput() {
      Account testConstructor = new Account(validUsername, validPassword, validEmail, validPhoneNumber);
      assertEquals(validUsername, testConstructor.getUsername());
      assertTrue(testConstructor.checkPassword(validPassword));
      assertEquals(validEmail, testConstructor.getEmail());
      assertEquals(validPhoneNumber, testConstructor.getPhoneNumber());
    }
    @Test
    @DisplayName("Test constructor invalid username throws IllegalArgumentException")
    void testConstructorInvalidUsernameThrowsIllegalArgumentException() {
      String invalidUserName = "";
      assertThrows(IllegalArgumentException.class, () -> new Account(
              invalidUserName, validPassword, validEmail, validPhoneNumber));
    }

    @Test
    @DisplayName("Test constructor invalid password throws IllegalArgumentException")
    void testConstructorInvalidPasswordThrowsIllegalArgumentException() {
      String invalidPasswordBlank = "";
      String invalidPasswordSpaces = "Test Password";
      String invalidPasswordNoUpperCase = "testPassword";
      assertThrows(IllegalArgumentException.class, () -> new Account(
              validUsername, invalidPasswordBlank, validEmail, validPhoneNumber));
      assertThrows(IllegalArgumentException.class, () -> new Account(
              validUsername, invalidPasswordSpaces, validEmail, validPhoneNumber));
      assertThrows(IllegalArgumentException.class, () -> new Account(
              validUsername, invalidPasswordNoUpperCase, validEmail, validPhoneNumber));
    }

    @Test
    @DisplayName("Test constructor invalid email throws IllegalArgumentException")
    void testConstructorInvalidEmailThrowsIllegalArgumentException() {
      String invalidEmailBlank = "";
      String invalidEmailSpaces = "Email @ Test";
      String invalidEmailNoSymbol = "EmailTest";
      assertThrows(IllegalArgumentException.class, () -> new Account(
              validUsername, validPassword, invalidEmailBlank, validPhoneNumber));
      assertThrows(IllegalArgumentException.class, () -> new Account(
              validUsername, validPassword, invalidEmailSpaces, validPhoneNumber));
      assertThrows(IllegalArgumentException.class, () -> new Account(
              validUsername, validPassword, invalidEmailNoSymbol, validPhoneNumber));
    }

    @Test
    @DisplayName("Test constructor invalid phone number throws IllegalArgumentException")
    void testConstructorInvalidPhoneNumberThrowsIllegalArgumentException() {
      String invalidPhoneNumberBlank = "";
      String invalidPhoneNumberSpaces = "4 27 6 97 96";
      String invalidPhoneNumberLetters = "4K3i3Ol";
      assertThrows(IllegalArgumentException.class, () -> new Account(
              validUsername, validPassword, validEmail, invalidPhoneNumberBlank));
      assertThrows(IllegalArgumentException.class, () -> new Account(
              validUsername, validPassword, validEmail, invalidPhoneNumberSpaces));
      assertThrows(IllegalArgumentException.class, () -> new Account(
              validUsername, validPassword, validEmail, invalidPhoneNumberLetters));
    }

    @Test
    @DisplayName("Test constructor invalid input throws NullPointerException")
    void testConstructorInvalidInputThrowsNullPointerException() {
      String invalidUserName = null;
      String invalidPassword = null;
      String invalidEmail = null;
      String invalidPhoneNumber = null;
      assertThrows(NullPointerException.class, () -> new Account(
              invalidUserName, validPassword, validEmail, validPhoneNumber));
      assertThrows(NullPointerException.class, () -> new Account(
              validUsername, invalidPassword, validEmail, validPhoneNumber));
      assertThrows(NullPointerException.class, () -> new Account(
              validUsername, validPassword, invalidEmail, validPhoneNumber));
      assertThrows(NullPointerException.class, () -> new Account(
              validUsername, validPassword, validEmail, invalidPhoneNumber));
    }
  }

  @Nested
  @DisplayName("Retrieve user information tests")
  class RetrieveUserInformationTests {
    @Test
    @DisplayName("Should get username")
    void shouldGetUsername() {
      String expectedUsername = "Name";
      String actualUsername = account.getUsername();
      assertEquals(expectedUsername, actualUsername);
    }

    @Test
    @DisplayName("Should get password")
    void shouldGetPassword() {
      String expectedPassword = "Password123";
      String actualPassword = account.getPassword();
      assertNotEquals(expectedPassword, actualPassword);
      assertTrue(account.checkPassword(expectedPassword));
    }
    @Test
    @DisplayName("Should get email")
    void shouldGetEmail() {
      String expectedEmail = "email@ntnu.no";
      String actualEmail = account.getEmail();
      assertEquals(expectedEmail, actualEmail);
    }

    @Test
    @DisplayName("Should get phone number")
    void shouldGetPhoneNumber() {
      String expectedPhoneNumber = "41789236";
      String actualPhoneNumber = account.getPhoneNumber();
      assertEquals(expectedPhoneNumber, actualPhoneNumber);
    }
  }

  @Nested
  @DisplayName("Change user information tests")
  class ChangeUserInformationTests {


    @Test
    @DisplayName("Should change password")
    void shouldSetNewPassword() {
      String newPassword = "Password321";
      account.setPassword(newPassword);
      assertTrue(account.checkPassword(newPassword));
    }

    @Test
    @DisplayName("Should not change password throws IllegalArgumentException")
    void shouldNotSetNewPasswordThrowsIllegalArgumentException() {
      String invalidPasswordBlank = "";
      String invalidPasswordSpaces = "Test Password";
      String invalidPasswordNoUpperCase = "testPassword";
      assertThrows(IllegalArgumentException.class, () -> account.setPassword(invalidPasswordBlank));
      assertThrows(IllegalArgumentException.class, () -> account.setPassword(invalidPasswordSpaces));
      assertThrows(IllegalArgumentException.class, () -> account.setPassword(invalidPasswordNoUpperCase));
    }

    @Test
    @DisplayName("Should not change password throws NullPointerException")
    void shouldNotSetNewPasswordThrowsNullPointerException() {
      String invalidPassword = null;
      assertThrows(NullPointerException.class, () -> account.setPassword(invalidPassword));
    }

    @Test
    @DisplayName("Should change email")
    void shouldSetNewEmail() {
      account.setEmail("idatt2001@ntnu.no");
      String expectedEmail = "idatt2001@ntnu.no";
      String actualEmail = account.getEmail();
      assertEquals(expectedEmail, actualEmail);
    }

    @Test
    @DisplayName("Should not change email throws IllegalArgumentException")
    void shouldNotSetNewEmailThrowsIllegalArgumentException() {
      String invalidEmailBlank = "";
      String invalidEmailSpaces = "Email @ Test";
      String invalidEmailNoSymbol = "EmailTest";
      assertThrows(IllegalArgumentException.class, () -> account.setEmail(invalidEmailBlank));
      assertThrows(IllegalArgumentException.class, () -> account.setEmail(invalidEmailSpaces));
      assertThrows(IllegalArgumentException.class, () -> account.setEmail(invalidEmailNoSymbol));
    }

    @Test
    @DisplayName("Should not change email throws NullPointerException")
    void shouldNotSetNewEmailThrowsNullPointerException() {
      String invalidEmail = null;
      assertThrows(NullPointerException.class, () -> account.setEmail(invalidEmail));
    }

    @Test
    @DisplayName("Should change phone number")
    void shouldSetNewPhoneNumber() {
      account.setPhoneNumber("2222222");
      String expectedPhoneNumber = "2222222";
      String actualPhoneNumber = account.getPhoneNumber();
      assertEquals(expectedPhoneNumber, actualPhoneNumber);
    }

    @Test
    @DisplayName("Should not change phone number throws IllegalArgumentException")
    void shouldNotSetNewPhoneNumberThrowsIllegalArgumentException() {
      String invalidPhoneNumberBlank = "";
      String invalidPhoneNumberSpaces = "4 27 6 97 96";
      String invalidPhoneNumberLetters = "4K3i3Ol";
      assertThrows(IllegalArgumentException.class, () -> account.setPhoneNumber(invalidPhoneNumberBlank));
      assertThrows(IllegalArgumentException.class, () -> account.setPhoneNumber(invalidPhoneNumberSpaces));
      assertThrows(IllegalArgumentException.class, () -> account.setPhoneNumber(invalidPhoneNumberLetters));
    }

    @Test
    @DisplayName("Should not change phone number throws NullPointerException")
    void shouldNotSetNewPhoneNumberThrowsNullPointerException() {
      String invalidPhoneNumber = null;
      assertThrows(NullPointerException.class, () -> account.setPhoneNumber(invalidPhoneNumber));
    }
  }


  @Nested
  @DisplayName("Budget tests")
  class BudgetTests {

    @Test
    @DisplayName("Should add a budget to the account")
    void testAddBudget() {
      Budget budget = new Budget("Test");
      account.addBudget(budget);
      assertEquals(1, account.getBudgets().size());
    }

    @Test
    @DisplayName("Should remove a budget from the account")
    void testRemoveBudget() {
      Budget budget = new Budget("Test");
      account.addBudget(budget);
      account.removeBudget("Test");
      assertEquals(0, account.getBudgets().size());
    }

    @Test
    @DisplayName("Should remove a 'false' budget from the account")
    void testRemoveBudgetFalse() {
      assertFalse(account.removeBudget("nonexistentID"));
    }
  }
}
