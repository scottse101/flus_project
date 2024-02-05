package edu.ntnu.idatt1002.group12.flus.model;

import java.io.Serializable;
import java.util.Objects;
import org.mindrot.jbcrypt.BCrypt;

/**
 * The class represents a user of the application.
 * This is an abstract class that provides common
 * properties and methods for all types of users.
 *
 * @author Ramtin Samavat and Stian Lyng
 * @version 1.0
 * @since April 21, 2023.
 */
public abstract class User implements Serializable {
  private final String username;
  private String password;
  private String email;
  private String phoneNumber; 

  /**
   * Constructor to create an object of the type user.
   *
   * @param username the name of the user.
   * @param password the password for the user.
   * @param email the email of the user.
   * @param phoneNumber the phone number of the user.
   * @throws IllegalArgumentException if the variables do not meet the requirements.
   * @throws NullPointerException if the variables are null.
   */
  public User(String username, String password, String email, String phoneNumber)
          throws IllegalArgumentException, NullPointerException {
    if (username.isBlank()) {
      throw new IllegalArgumentException("Username cannot be blank.");
    }
    if (password.isBlank() || password.contains(" ") || !(Character.isUpperCase(password.charAt(0)))) {
      throw new IllegalArgumentException("Password cannot be blank, cannot contain spaces, "
              + "and first letter must be in uppercase.");
    }
    if (email.isBlank() || email.contains(" ") || !(email.contains("@"))) {
      throw new IllegalArgumentException(("Email cannot be blank, cannot contain spaces, "
              + "and must contain @."));
    }
    if (phoneNumber.isBlank() || phoneNumber.contains(" ") || !(phoneNumber.matches("\\d+"))) {
      throw new IllegalArgumentException("Phone number cannot be blank, cannot contain spaces, "
              + "and must contain only numeric values.");
    }
    this.username = Objects.requireNonNull(username.trim(), "Username cannot be null");
    this.password = hashPassword(Objects.requireNonNull(password.trim(), "Password cannot be null"));
    this.email = Objects.requireNonNull(email.trim(), "Email cannot be null");
    this.phoneNumber = Objects.requireNonNull(phoneNumber.trim(), "Phone number cannot be  null");
  }

  /**
   * The method retrieves the name of the user.
   *
   * @return username.
   */
  public String getUsername() {
    return username;
  }

  /**
   * The method retrieves the password of the user.
   *
   * @return password.
   */
  public String getPassword() {
    return password;
  }

  /**
   * The method retrieves the email of the user.
   *
   * @return email.
   */
  public String getEmail() {
    return email;
  }

  /**
   * The method retrieves the phone number of the user.
   *
   * @return phone number.
   */
  public String getPhoneNumber() {
    return phoneNumber;
  }
 
  /**
   * The method sets a new hashed password.
   *
   * @param password new hashed password.
   * @throws IllegalArgumentException if the password does not meet the requirements.
   * @throws NullPointerException if the password is null;
   */
  public void setPassword(String password) throws IllegalArgumentException, NullPointerException {
    if (password.isBlank() || password.contains(" ") || !(Character.isUpperCase(password.charAt(0)))) {
      throw new IllegalArgumentException("Password cannot be blank, cannot contain spaces, "
              + "and first letter must be in uppercase.");
    }
    this.password = hashPassword(Objects.requireNonNull(password.trim(), "Password cannot be null"));
  }

  /**
   * The method sets a new email.
   *
   * @param email new email.
   * @throws IllegalArgumentException if the email does not meet the if the requirements.
   * @throws NullPointerException if the email is null ;
   */
  public void setEmail(String email) throws IllegalArgumentException, NullPointerException {
    if (email.isBlank() || email.contains(" ") || !(email.contains("@"))) {
      throw new IllegalArgumentException(("Email cannot be blank, cannot contain spaces, "
              + "and must contain @."));
    }
    this.email = Objects.requireNonNull(email.trim(), "Email cannot be null");
  }

  /**
   * The method sets a new phone number.
   *
   * @param phoneNumber new phone number.
   * @throws IllegalArgumentException if the phone number is blank of contains spaces.
   * @throws NullPointerException if the phone number is null;
   */
  public void setPhoneNumber(String phoneNumber)
          throws IllegalArgumentException, NullPointerException {
    if (phoneNumber.isBlank() || phoneNumber.contains(" ") || !(phoneNumber.matches("\\d+"))) {
      throw new IllegalArgumentException("Phone number cannot be blank, cannot contain spaces, "
              + "and must contain only numeric values.");
    }
    this.phoneNumber = Objects.requireNonNull(phoneNumber.trim(), "Phone number cannot be  null");
  }

  /**
   * The method checks if a given password matches the stored hashed password.
   *
   * @param passwordToCheck the password to be checked.
   * @return a boolean value indicating whether the password is correct.
   */
  public boolean checkPassword(String passwordToCheck) {
    return BCrypt.checkpw(passwordToCheck, getPassword());
  }

  /**
   * The method hashes the password.
   *
   * @param password the password to be hashed.
   * @return the hashed password.
   */
  public String hashPassword(String password) {
    String salt = BCrypt.gensalt();
    return BCrypt.hashpw(password, salt);
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
    User user = (User) o;
    return getUsername().equals(user.getUsername())
            && getPassword().equals(user.getPassword())
            && getEmail().equals(user.getEmail())
            && getPhoneNumber().equals(user.getPhoneNumber());
  }

  /**
   * The method generates a hash value for the object.
   *
   * @return hash value for the object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(getUsername(), getPassword(), getEmail(), getPhoneNumber());
  }
}
