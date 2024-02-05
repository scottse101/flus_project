package edu.ntnu.idatt1002.group12.flus.view.controllers.profile;

import java.io.IOException;
import java.util.logging.Level;
import edu.ntnu.idatt1002.group12.flus.view.controllers.BaseController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

/**
 * The ChangeEmailController class is responsible for changing
 * the email of the user.
 *
 * @author Markus Evald Dalbakk
 * @version 1.0
 * @since April 24, 2023.
 */
public class ChangeEmailController extends BaseController {

  @FXML
  private TextField emailField;

  @FXML
  private Button changeMailButton;

  /**
   * The method initializes the controller class.
   */
  @FXML
  public void initialize() {

    changeMailButton.setDisable(true);

    emailField.textProperty().addListener((observable, oldValue, newValue) -> {
      changeMailButton.setDisable(emailField.getText().isEmpty());
    });
  }

  /**
   * The method handles the button press for changing the user's email.
   *
   * @param event The ActionEvent object generated when the button is pressed.
   */
  @FXML
  private void changeButtonPressed(ActionEvent event) {
    String newEmail = emailField.getText();
    try {
      accountRegister.changeEmail(account.getUsername(), newEmail);
      accountRegister.saveAccounts(PATH_OF_FILE_ACCOUNTS);
    } catch (IOException e) {
      alertException(Level.SEVERE, AlertType.ERROR, e);
    } catch (IllegalArgumentException | NullPointerException e) {
      alertException(Level.WARNING, AlertType.WARNING, e);
    }
    switchScene("profile/showProfile");
  }
}
