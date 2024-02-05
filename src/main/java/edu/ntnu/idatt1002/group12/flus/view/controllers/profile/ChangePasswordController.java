package edu.ntnu.idatt1002.group12.flus.view.controllers.profile;

import edu.ntnu.idatt1002.group12.flus.view.controllers.BaseController;
import java.io.IOException;
import java.util.logging.Level;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * The ChangePasswordController class is responsible for changing
 * the password of the user.
 *
 * @author Markus Evald Dalbakk
 * @version 1.0
 * @since April 24, 2023.
 */
public class ChangePasswordController extends BaseController {

  @FXML
  private TextField passwordField, passwordField1;

  @FXML
  private Button changePassword;

  /**
   * The method initializes the controller class.
   */
  @FXML
  public void initialize() {

    changePassword.setDisable(true);

    passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
      changePassword.setDisable(passwordField.getText().isEmpty()
              || passwordField1.getText().isEmpty());
    });

    passwordField1.textProperty().addListener((observable, oldValue, newValue) -> {
      changePassword.setDisable(passwordField.getText().isEmpty()
              || passwordField1.getText().isEmpty());
    });
  }

  /**
   * The method handles the button press for changing the user's password.
   *
   * @param event The ActionEvent object generated when the button is pressed.
   */
  @FXML
  private void changeButtonPressed(ActionEvent event) {
    String oldPassword = passwordField.getText();
    String newPassword = passwordField1.getText();
    try {
      accountRegister.changePassword(account.getUsername(), oldPassword, newPassword);
      accountRegister.saveAccounts(PATH_OF_FILE_ACCOUNTS);
      switchScene("profile/showProfile");
    } catch (IOException e) {
      alertException(Level.SEVERE, AlertType.ERROR, e);
    } catch (IllegalArgumentException | NullPointerException e) {
      alertException(Level.WARNING, AlertType.WARNING, e);
    }
  }
}
