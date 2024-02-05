package edu.ntnu.idatt1002.group12.flus.view.controllers.profile;

import edu.ntnu.idatt1002.group12.flus.view.controllers.BaseController;
import java.io.IOException;
import java.util.logging.Level;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

/**
 * The ChangePhoneNumberController class is responsible for changing
 * the phone number of the user.
 *
 * @author Markus Evald Dalbakk
 * @version 1.0
 * @since April 24, 2023.
 */
public class ChangePhoneNumberController extends BaseController {

  @FXML
  private TextField phoneNumberField;

  @FXML
  private Button changeNumberButton;

  /**
   * The method initializes the controller class.
   */
  @FXML
  public void initialize() {

    changeNumberButton.setDisable(true);

    phoneNumberField.textProperty().addListener((observable, oldValue, newValue) -> {
      changeNumberButton.setDisable(phoneNumberField.getText().isEmpty());
    });
  }

  /**
   * The method handles the button press for changing the user's phone number.
   *
   * @param event The ActionEvent object generated when the button is pressed.
   */
  @FXML
  private void changeButtonPressed(ActionEvent event) {
    String newPhoneNumber = phoneNumberField.getText();
    try {
      accountRegister.changePhoneNumber(account.getUsername(), newPhoneNumber);
      accountRegister.saveAccounts(PATH_OF_FILE_ACCOUNTS);
    } catch (IOException e) {
      alertException(Level.SEVERE, AlertType.ERROR, e);
    } catch (IllegalArgumentException | NullPointerException e) {
      alertException(Level.WARNING, AlertType.WARNING, e);
    }
    switchScene("profile/showProfile");
  }
}