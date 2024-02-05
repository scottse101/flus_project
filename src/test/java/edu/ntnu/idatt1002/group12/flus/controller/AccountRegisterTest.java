package edu.ntnu.idatt1002.group12.flus.controller;

import edu.ntnu.idatt1002.group12.flus.model.Account;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The class tests the AccountRegister class.
 *
 * @author Ramitn Samavat
 * @version 1.0
 * @since April 24, 2023.
 */
class AccountRegisterTest {
  private static final Logger logger = Logger.getLogger(AccountRegisterTest.class.getName());
  private final AccountRegister register = AccountRegister.getInstance();
  private Account account;
  private String pathToTemporaryFile;
  private File testFile;

  @BeforeEach
  void setUp() {
    account = new Account("Username", "Password", "email@test.no", "123456789");
    register.registerAccount(account.getUsername(), "Password", account.getEmail(), account.getPhoneNumber());
    pathToTemporaryFile = "src/test/resources/teststorage/account_objects.ser";
    testFile = new File(pathToTemporaryFile);
  }

  @AfterEach
  void tearDown() {
    Path path = Paths.get(testFile.getPath());
    try {
      Files.deleteIfExists(path);
    } catch (IOException e) {
      logger.log(Level.WARNING, "Error deleting file.", e);
    }
    register.deleteAccount(account.getUsername(), account.getUsername());
  }

  @Nested
  @DisplayName("Register and delete account tests")
  class RegisterAndDeleteAccountTests {

    @Test
    @DisplayName("Should register an account")
    void shouldRegisterAnAccount() {
      register.registerAccount("Test username",
              "TestPassword", "test@test.no", "13456789");

      assertDoesNotThrow(() -> register.findAccount("Test username"));
    }

    @Test
    @DisplayName("Should not register account throws NullPointerException")
    void shouldRegisterAccountThrowsNullPointerException() {
      assertThrows(NullPointerException.class, () -> register.registerAccount
              (null, account.getPassword(), account.getEmail(), account.getPhoneNumber()));

      assertThrows(NullPointerException.class, () -> register.registerAccount
              (account.getUsername(), null, account.getEmail(), account.getPhoneNumber()));

      assertThrows(NullPointerException.class, () -> register.registerAccount
              (account.getUsername(), account.getPassword(), null, account.getPhoneNumber()));

      assertThrows(NullPointerException.class, () -> register.registerAccount
              (account.getUsername(), account.getPassword(), account.getEmail(), null));
    }

    @Test
    @DisplayName("Should not register account throws IllegalArgumentException")
    void shouldRegisterAccountThrowsIllegalArgumentException() {
      String invalidUsername = account.getUsername();
      String invalidEmail = account.getEmail();
      String invalidPhoneNumber = account.getPhoneNumber();
      String validUsername = "Test";
      String validPassword = account.getPassword();
      String validEmail = "test@email.no";
      String validPhoneNumber = "987654321";

      assertThrows(IllegalArgumentException.class, () -> register.registerAccount(invalidUsername,
                      validPassword, validEmail, validPhoneNumber));

      assertThrows(IllegalArgumentException.class, () -> register.registerAccount(validUsername,
                      validPassword, invalidEmail, validPhoneNumber));

      assertThrows(IllegalArgumentException.class, () -> register.registerAccount(validUsername,
                      validPassword, validEmail, invalidPhoneNumber));
    }

    @Test
    @DisplayName("Should delete account")
    void shouldDeleteAccountTrue() {
      Account accountDelete = new Account("Delete", "Delete123",
              "delete@ntnu.no", "34566");
      register.registerAccount(accountDelete.getUsername(), "Delete123",
              accountDelete.getEmail(), accountDelete.getPhoneNumber());

      assertTrue(register.deleteAccount(accountDelete.getUsername(), accountDelete.getUsername()));
      assertThrows(NoSuchElementException.class, () -> register.findAccount(accountDelete.getUsername()));
    }

    @Test
    @DisplayName("Should not delete account")
    void deleteNonexistentAccountFalse() {
      String nonexistentUsername = "Test";
      String nonexistentPassword = "123";

      assertFalse(register.deleteAccount(nonexistentUsername, account.getPassword()));
      assertFalse(register.deleteAccount(account.getUsername(), nonexistentPassword));
    }

    @Test
    @DisplayName("Should not delete account throws NullPointerException")
    void shouldNotDeleteAccountThrowsNullPointerException() {
      assertThrows(NullPointerException.class, () -> register.deleteAccount(null, account.getPassword()));
      assertThrows(NullPointerException.class, () -> register.deleteAccount(account.getUsername(), null));
    }
  }

  @Nested
  @DisplayName("Retrieve account tests")
  class RetrieveAccountTests {

    @Test
    @DisplayName("Should find account")
    void shouldGetAccount() {
      assertEquals(account.getUsername(), register.findAccount(account.getUsername()).getUsername());
      assertEquals(account.getEmail(), register.findAccount(account.getUsername()).getEmail());
      assertEquals(account.getPhoneNumber(), register.findAccount(account.getUsername()).getPhoneNumber());
    }

    @Test
    @DisplayName("Should not find account throws NullPointerException")
    void shouldNotGetAccountThrowsNullPointerException() {
      assertThrows(NullPointerException.class, () -> register.findAccount(null));
    }

    @Test
    @DisplayName("Should not find account throws NoSuchElementException")
    void shouldNotGetAccountThrowsNoSuchElementException() {
      assertThrows(NoSuchElementException.class, () -> register.findAccount("Test1"));
    }

    @Test
    @DisplayName("Should get account copy")
    void shouldGetAccountCopy() {
      assertEquals(account.getUsername(), register.getAccount(account.getUsername(), "Password").getUsername());
      assertEquals(account.getEmail(), register.getAccount(account.getUsername(), "Password").getEmail());
      assertEquals(account.getPhoneNumber(), register.getAccount(account.getUsername(), "Password").getPhoneNumber());
    }

    @Test
    @DisplayName("Should not get account copy throws NullPointerException")
    void ShouldNotGetAccountThrowsNullPointerException() {
      assertThrows(NullPointerException.class,
              () -> register.getAccount(null, account.getPassword()));
      assertThrows(NullPointerException.class,
              () -> register.getAccount(account.getUsername(), null));
    }

    @Test
    @DisplayName("Should not get account copy throws IllegalArgumentException")
    void ShouldNotGetAccountThrowsIllegalArgumentException() {
      assertThrows(IllegalArgumentException.class,
              () -> register.getAccount(account.getUsername(), "InvalidPassword"));
    }

    @Test
    @DisplayName("Should get accounts")
    void shouldGetAccounts() {
      List<Account> expected = new ArrayList<>();
      expected.add(account);
      List<Account> actual = register.getAccounts();
      assertEquals(expected.get(0).getUsername(), actual.get(0).getUsername());
      assertEquals(expected.get(0).getEmail(), actual.get(0).getEmail());
      assertEquals(expected.get(0).getPhoneNumber(), actual.get(0).getPhoneNumber());
    }
  }

  @Nested
  @DisplayName("Change account information tests")
  class ChangeAccountInformationTests {
    @Test
    @DisplayName("Should change password")
    void shouldChangePassword() {
      register.changePassword(account.getUsername(), "Password", "NewPassword");
      assertTrue(register.findAccount(account.getUsername()).checkPassword("NewPassword"));
    }

    @Test
    @DisplayName("Should not change password throws NullPointerException")
    void shouldNotChangePasswordThrowsNullPointerException() {
      assertThrows(NullPointerException.class,
              () -> register.changePassword(account.getUsername(), null, "NewPassword"));
      assertThrows(NullPointerException.class,
              () -> register.changePassword(account.getUsername(), account.getPassword(), null));
    }

    @Test
    @DisplayName("Should not change password throws IllegalArgumentException")
    void shouldNotChangePasswordThrowsIllegalArgumentException() {
      assertThrows(IllegalArgumentException.class,
              () -> register.changePassword(account.getUsername(), account.getEmail(), account.getPassword()));
    }

    @Test
    @DisplayName("Should change email")
    void shouldChangeEmail() {

      register.changeEmail(account.getUsername(), "test@stud.no");
      assertEquals("test@stud.no", register.findAccount(account.getUsername()).getEmail());
    }

    @Test
    @DisplayName("Should not change email throws NullPointerException")
    void shouldNotChangeEmailThrowsNullPointerException() {
      assertThrows(NullPointerException.class,
              () -> register.changeEmail(account.getUsername(), null));
    }

    @Test
    @DisplayName("Should not change email throws IllegalArgumentException")
    void shouldNotChangeEmailThrowsIllegalArgumentException() {
      assertThrows(IllegalArgumentException.class,
              () -> register.changeEmail(account.getUsername(), account.getEmail()));
    }

    @Test
    @DisplayName("Should change phone number")
    void shouldChangePhoneNumber() {
      register.changePhoneNumber(account.getUsername(), "987654321");
      assertEquals("987654321", register.findAccount(account.getUsername()).getPhoneNumber());
    }

    @Test
    @DisplayName("Should not change phone number throws NullPointerException")
    void shouldNotChangePhoneNumberThrowsNullPointerException() {
      assertThrows(NullPointerException.class,
              () -> register.changePhoneNumber(account.getUsername(), null));
    }

    @Test
    @DisplayName("Should not change phone number throws IllegalArgumentException")
    void shouldNotChangePhoneNumberThrowsIllegalArgumentException() {
      assertThrows(IllegalArgumentException.class,
              () -> register.changePhoneNumber(account.getUsername(), account.getPhoneNumber()));
    }
  }

  @Nested
  @DisplayName("Account file handling")
  class AccountsFileHandling {

    String invalidPathToFileNull = null;
    String invalidPathToFileFormat = "src/test/resources/teststorage/accounts.txt";
    String invalidPathToFileBlank = " ";

    @Test
    @DisplayName("Test saving and loading accounts")
    void testSaveAccounts() {
      assertFalse(register.getAccounts().isEmpty());

      try {
        register.saveAccounts(pathToTemporaryFile);
      } catch (IOException e) {
        logger.log(Level.WARNING, "Error occurred while writing games to file: " + pathToTemporaryFile, e);
      }

      assertTrue(register.deleteAccount(account.getUsername(), account.getUsername()));

      assertTrue(register.getAccounts().isEmpty());

      try {
        register.loadAccounts(pathToTemporaryFile);
      } catch (IOException | ClassNotFoundException e) {
        logger.log(Level.WARNING, "Error occurred while reading games to file: " + pathToTemporaryFile, e);
      }

      assertTrue(register.getAccounts().contains(register.findAccount(account.getUsername())));

      assertFalse(register.getAccounts().isEmpty());
    }

    @Test
    @DisplayName("Should not save accounts to file throws NullPointerException")
    void shouldNotWriteStoryToFileThrowsNullPointerException() {
      assertThrows(NullPointerException.class,
              () -> register.saveAccounts(invalidPathToFileNull));
    }

    @Test
    @DisplayName("Should not save accounts to file throws IllegalArgumentException")
    void shouldNotWriteStoryToFileThrowsIllegalArgumentException() {
      assertThrows(IllegalArgumentException.class,
              () -> register.saveAccounts(invalidPathToFileBlank));
      assertThrows(IllegalArgumentException.class,
              () -> register.saveAccounts(invalidPathToFileFormat));
    }

    @Test
    @DisplayName("Should not load accounts from file throws NullPointerException")
    void shouldNotReadStoryFromFileThrowsNullPointerException() {
      assertThrows(NullPointerException.class, () -> register.loadAccounts(invalidPathToFileNull));
    }

    @Test
    @DisplayName("Should not read story from file throws IllegalArgumentException")
    void shouldNotReadStoryFromFileThrowsIllegalArgumentException() {
      assertThrows(IllegalArgumentException.class, () -> register.loadAccounts(invalidPathToFileFormat));
      assertThrows(IllegalArgumentException.class, () -> register.loadAccounts(invalidPathToFileBlank));
    }
  }
}