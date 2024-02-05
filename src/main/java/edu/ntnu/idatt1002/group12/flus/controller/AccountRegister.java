package edu.ntnu.idatt1002.group12.flus.controller;

import edu.ntnu.idatt1002.group12.flus.model.Account;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The class represents a registry of user accounts.
 * It provides methods to register, delete, and modify accounts,
 * as well as to retrieve account information.
 *
 * @author Ramtin Samavat and Stian Lyng
 * @version 1.0
 * @since April 17, 2023.
 */
public class AccountRegister {

  private static AccountRegister instance = null;
  private static final Logger logger = Logger.getLogger(AccountRegister.class.getName());
  private static final String FILE_EXTENSION = ".ser";
  private List<Account> accounts;

  /**
   * Constructs an instance of AccountRegister with
   * an empty list of accounts.
   */
  private AccountRegister() {
    accounts = new ArrayList<>();
  }

  /**
   * Returns an instance of the AccountRegister class.
   * If an instance does not exist, a new instance is created.
   *
   * @return an instance of the AccountRegister class.
   */
  public static AccountRegister getInstance() {
    if (instance == null) {
      instance = new AccountRegister();
    }
    return instance;
  }

  /**
   * The method is a helper method for the other methods in the class.
   * It retrieves an account from the list of accounts with username.
   *
   * @param username the username of the account.
   * @return the account to be retrieved.
   * @throws NullPointerException   if the username is null.
   * @throws NoSuchElementException if the account does not exist.
   */
  protected Account findAccount(String username)
          throws NullPointerException, NoSuchElementException {

    validateUsername(username);

    return this.accounts
            .stream()
            .filter(account -> account.getUsername().equals(username.trim()))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException("Account with username "
                    + username + " does not exist."));
  }

  /**
   * The method registers a new account with the provided information.
   *
   * @param username The desired username for the new account. Must be unique.
   * @param password The password for the account.
   * @param email The email address for the new account. Must be unique.
   * @param phoneNumber The phone number for the new account. Must be unique.
   * @throws IllegalArgumentException If an account with the same information already exists.
   */
  public void registerAccount(String username, String password, String email, String phoneNumber)
          throws IllegalArgumentException, NullPointerException {
    validateUsername(username);
    validatePassword(password);
    if (email == null) {
      throw new NullPointerException("Email cannot be null.");
    }
    if (phoneNumber == null) {
      throw new NullPointerException("Phone number cannot be null.");
    }
    if (accounts.stream().anyMatch(account -> account.getUsername().equals(username.trim()))) {
      throw new IllegalArgumentException("An account with the same username already exists.");
    }
    if (accounts.stream().anyMatch(account -> account.getEmail().equals(email.trim()))) {
      throw new IllegalArgumentException("An account with the same email already exists.");
    }
    if (accounts.stream()
            .anyMatch(account -> account.getPhoneNumber().equals(phoneNumber.trim()))) {
      throw new IllegalArgumentException("An account with the same phone number already exists.");
    }
    Account account = new Account(username, password, email, phoneNumber);
    accounts.add(account);
  }

  /**
   * The method removes an account from the list of accounts based on the provided information.
   *
   * @param username the username of the account to be deleted.
   * @param inputValidation input validation for deletion i form of username.
   * @return true if the account was successfully deleted, false otherwise.
   * @throws NullPointerException if either the username or inputValidation is null.
   */
  public boolean deleteAccount(String username, String inputValidation)
          throws NullPointerException {
    validateUsername(username);
    if (inputValidation == null) {
      throw new NullPointerException("The input validation cannot be null.");
    }
    if (username.trim().equals(inputValidation.trim())) {
      return accounts.removeIf(account -> account.getUsername().equals(username.trim()));
    }
    return false;
  }

  /**
   * The method changes the password of an existing account.
   *
   * @param username the username of the account.
   * @param oldPassword the old password of the account.
   * @param newPassword the new password of the account.
   * @throws IllegalArgumentException if the old or new password is invalid.
   * @throws NullPointerException if the old or new password is null.
   * @throws NoSuchElementException if the account does not exist.
   */
  public void changePassword(String username, String oldPassword, String newPassword)
          throws IllegalArgumentException, NullPointerException, NoSuchElementException {
    if (oldPassword == null) {
      throw new NullPointerException("The old password cannot be null.");
    }
    if (newPassword == null) {
      throw new NullPointerException("The new password cannot be null.");
    }
    if (!findAccount(username).checkPassword(oldPassword)) {
      throw new IllegalArgumentException("The old password is wrong.");
    }
    findAccount(username).setPassword(newPassword);
  }

  /**
   * The method changes the email of an existing account.
   *
   * @param username the username of the account.
   * @param newEmail the new email for the account.
   * @throws IllegalArgumentException if the new email is not valid.
   * @throws NullPointerException if either parameter is null.
   * @throws NoSuchElementException if the account does not exist.
   */
  public void changeEmail(String username, String newEmail)
          throws IllegalArgumentException, NullPointerException, NoSuchElementException {
    if (newEmail == null) {
      throw new NullPointerException("The new email cannot be null.");
    }
    if (accounts.stream().anyMatch(account -> account.getEmail().equals(newEmail.trim()))) {
      throw new IllegalArgumentException("An account with the same email already exists.");
    }
    findAccount(username).setEmail(newEmail);
  }

  /**
   * The method changes the phone number of an existing account.
   *
   * @param username the username of the account.
   * @param newPhoneNumber the new phone number for the account.
   * @throws IllegalArgumentException if the new phone number is not valid.
   * @throws NullPointerException if either parameter is null.
   * @throws NoSuchElementException if the account does not exist.
   */
  public void changePhoneNumber(String username, String newPhoneNumber)
          throws IllegalArgumentException, NullPointerException, NoSuchElementException {
    if (newPhoneNumber == null) {
      throw new NullPointerException("The new phone number cannot be null.");
    }
    if (accounts.stream()
            .anyMatch(account -> account.getPhoneNumber().equals(newPhoneNumber.trim()))) {
      throw new IllegalArgumentException("An account with the same phone number already exists.");
    }
    findAccount(username).setPhoneNumber(newPhoneNumber);
  }

  /**
   * The method retrieves a deep copy of the Account object corresponding
   * to the specified username and password.
   *
   * @param username the username of the account.
   * @param password the password of the account.
   * @return a deep copy of the Account object corresponding to the specified username and password.
   * @throws NullPointerException if either the username or password is null
   * @throws NoSuchElementException if the account does not exist.
   * @throws UnsupportedOperationException if cloning is not supported.
   */
  public Account getAccount(String username, String password)
          throws NullPointerException, IllegalArgumentException {
    validateUsername(username);
    validatePassword(password);
    if (!findAccount(username).checkPassword(password)) {
      throw new IllegalArgumentException("Invalid password for account.");
    }
    return findAccount(username);
  }

  /**
   * The method retrieves the list of accounts.
   *
   * @return the list of accounts.
   */
  protected List<Account> getAccounts() {
    return accounts;
  }

  /**
   * Saves the list of accounts to the ACCOUNTS_STORE file.
   * Creates a new file if the file does not exist. If the file exists, it will be overwritten.
   *
   * @param pathOfFile the path of the file to save the accounts to.
   * @throws IOException IOException if an I/O error occurs.
   * @throws NullPointerException if pathOfFile is null.
   * @throws IllegalArgumentException if pathOfFile is blank or does not end with FILE_EXTENSION.
   */
  public void saveAccounts(String pathOfFile) throws IOException,
          NullPointerException, IllegalArgumentException {
    validatePathOfFile(pathOfFile);
    try (FileOutputStream fileOutput = new FileOutputStream(pathOfFile);
         ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput)) {
      objectOutput.writeObject(accounts);
    } catch (IOException e) {
      logger.log(Level.SEVERE, "Error saving accounts to file.", e);
      throw new IOException("Error saving accounts to file: " + e.getMessage());
    }
  }

  /**
   * Loads the list of accounts from the ACCOUNTS_STORE file.
   * Annotated with @SuppressWarnings("unchecked") because we know
   * that the file contains a list of accounts.
   *
   * @param pathOfFile the path of the file to load the accounts from.
   * @throws IOException if an I/O error occurs.
   * @throws ClassNotFoundException if the class of a serialized object cannot be found.
   * @throws EOFException if the file is either empty or corrupted.
   * @throws NullPointerException if pathOfFile is null.
   * @throws IllegalArgumentException if pathOfFile is blank or does not end with FILE_EXTENSION.
   */
  @SuppressWarnings("unchecked") // Reason: We know that the file contains a list of accounts.
  public void loadAccounts(String pathOfFile) throws IOException, ClassNotFoundException,
          EOFException, NullPointerException, IllegalArgumentException {
    validatePathOfFile(pathOfFile);
    File file = new File(pathOfFile);
    if (file.exists() && file.length() != 0) {
      try (FileInputStream fileInput = new FileInputStream(pathOfFile);
           ObjectInputStream objectInput = new ObjectInputStream(fileInput)) {
        accounts = (List<Account>) objectInput.readObject();
      } catch (EOFException e) {
        logger.log(Level.SEVERE, "File is empty or corrupted,"
                + " initializing an empty accounts list.", e);
        throw new EOFException("File is empty or corrupted: " + e.getMessage());
      } catch (IOException e) {
        logger.log(Level.SEVERE, "An error occurred while loading"
                + " accounts from the file: " + pathOfFile, e);
        throw new IOException("Error loading accounts from file: " + e.getMessage());
      } catch (ClassNotFoundException e) {
        logger.log(Level.SEVERE, "Cannot find the class of a serialized object"
                + " while loading accounts from the file: " + pathOfFile, e);
        throw new ClassNotFoundException("Error loading accounts from file: " + e.getMessage());
      }
    }
  }

  /**
   * The method validates the given username is not null.
   *
   * @param username the username to validate.
   * @throws NullPointerException if the username is null.
   */
  private void validateUsername(String username) throws NullPointerException {
    if (username == null) {
      throw new NullPointerException("Username cannot be null.");
    }
  }

  /**
   * The method validates the given password is not null.
   *
   * @param password the password to validate.
   * @throws NullPointerException if the password is null.
   */
  private void validatePassword(String password) throws NullPointerException {
    if (password == null) {
      throw new NullPointerException("Password cannot be null.");
    }
  }

  /**
   * Helper method to validate the pathOfFile parameter.
   *
   * @param pathOfFile the path of the file.
   * @throws NullPointerException if pathOfFile is null.
   * @throws IllegalArgumentException if pathOfFile is blank or does not end with FILE_EXTENSION.
   */
  private static void validatePathOfFile(String pathOfFile)
          throws NullPointerException, IllegalArgumentException {
    if (pathOfFile == null) {
      throw new NullPointerException("The path of the file cannot be null.");
    }
    if (pathOfFile.isBlank()) {
      throw new IllegalArgumentException("The path to the file cannot be blank.");
    }
    if (!pathOfFile.toLowerCase().trim().endsWith(FILE_EXTENSION)) {
      throw new IllegalArgumentException("The path to the file must end with " + FILE_EXTENSION);
    }
  }
}
