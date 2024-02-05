package edu.ntnu.idatt1002.group12.flus.view.controllers.profile;

import edu.ntnu.idatt1002.group12.flus.view.controllers.BaseController;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

/**
 * The ShowProfileController class is responsible for displaying
 * and managing the user's profile page.
 *
 * @author Markus Evald Dalbakk
 * @version 1.0
 * @since April 24, 2023.
 */
public class ShowProfileController extends BaseController {

  @FXML
  private Label username, email, phoneNumber;

  /**
   * The method initializes the user's profile page with their
   * username, email, and phone number.
   */
  @FXML
  @Override
  public void initialize() {
    super.initialize();

    username.setText("Username: " + account.getUsername());
    email.setText("Email: " + account.getEmail());
    phoneNumber.setText("Phone number: " + account.getPhoneNumber());
  }

  /**
   * The method handles the button press for changing the user's password.
   *
   * @param event The ActionEvent object generated when the button is pressed.
   */
  @FXML
  private void passwordButtonPressed(ActionEvent event) {
    switchScene("profile/changePassword");
  }

  /**
   * The method handles the button press for changing the user's email.
   *
   * @param event The ActionEvent object generated when the button is pressed.
   */
  @FXML
  private void emailButtonPressed(ActionEvent event) {
    switchScene("profile/changeEmail");
  }

  /**
   * The method handles the button press for changing the user's phone number.
   *
   * @param event The ActionEvent object generated when the button is pressed.
   */
  @FXML
  private void phoneNumberButtonPressed(ActionEvent event) {
    switchScene("profile/changePhoneNumber");
  }

  /**
   * The method handles the button press for deleting the user's account.
   *
   * @param event The ActionEvent object generated when the button is pressed.
   */
  @FXML
  private void deleteUserPressed(ActionEvent event) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Delete user");
    alert.setHeaderText("Are you sure you want to delete this user?");
    alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.YES) {
      switchScene("login/showLoginPage");
      try {
        accountRegister.deleteAccount(account.getUsername(), account.getUsername());
        accountRegister.saveAccounts(PATH_OF_FILE_ACCOUNTS);
      } catch (IOException | NullPointerException e) {
        alertException(Level.SEVERE, AlertType.ERROR, e);
      }
    }
  }
}
