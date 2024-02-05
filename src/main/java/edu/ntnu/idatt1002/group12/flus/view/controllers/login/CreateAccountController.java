package edu.ntnu.idatt1002.group12.flus.view.controllers.login;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import edu.ntnu.idatt1002.group12.flus.model.Account;
import edu.ntnu.idatt1002.group12.flus.view.controllers.AssignableController;
import edu.ntnu.idatt1002.group12.flus.view.controllers.BaseController;
import edu.ntnu.idatt1002.group12.flus.view.controllers.ControllerData;
import edu.ntnu.idatt1002.group12.flus.controller.AccountRegister;
import edu.ntnu.idatt1002.group12.flus.controller.BudgetManager;
import edu.ntnu.idatt1002.group12.flus.controller.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * The CreateAccountController class is responsible for
 * the creation of new accounts.
 *
 * @author Henrik Nilsen
 * @version 1.0
 * @since April 24, 2023.
 */
public class CreateAccountController implements AssignableController {

  private static final Logger logger = Logger.getLogger(BaseController.class.getName());
  private AccountRegister accountRegister;
  private SceneManager sceneManager;
  private BudgetManager budgetManager;

  /**
   * The method assigns the necessary objects to the controller.
   *
   * @param controllerData The data needed to assign the objects.
   */
  @Override
  public void assign(ControllerData controllerData) {
    this.accountRegister = AccountRegister.getInstance();
    this.sceneManager = controllerData.getSceneManager();
    this.budgetManager = controllerData.getBudgetManager();
  }
  
  @FXML
  private TextField usernameField, passwordField, emailField, numberField;

  @FXML
  private Button createAccountButton;

  /**
   * The method checks if the  username, password, email or number
   * text fields are filled, and enables/disables the create account
   * button accordingly.
   */
  private void checkTextFields() {
    if (!usernameField.getText().isEmpty()
            && !passwordField.getText().isEmpty()
            && !emailField.getText().isEmpty()
            && !numberField.getText().isEmpty()) {
      createAccountButton.setDisable(false);
    } else {
      createAccountButton.setDisable(true);
    }
  }

  /**
   * The method initializes the controller class.
   */
  @FXML
  public void initialize() {

    createAccountButton.setDisable(true);

    usernameField.textProperty().addListener((observable, oldValue, newValue) -> {
      checkTextFields();
    });

    passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
      checkTextFields();
    });

    emailField.textProperty().addListener((observable, oldValue, newValue) -> {
      checkTextFields();
    });

    numberField.textProperty().addListener((observable, oldValue, newValue) -> {
      checkTextFields();
    });
  }

  /**
   * The method handles the button press for creating a new account.
   *
   * @param event The ActionEvent object generated when the button is pressed.
   */
  @FXML
  private void createAccountPressed(ActionEvent event) {

    String username = usernameField.getText();
    String password = passwordField.getText();
    String email = emailField.getText();
    String number = numberField.getText();

    try {
      AccountRegister.getInstance().registerAccount(username, password, email, number);
      accountRegister.saveAccounts("src/main/resources/storage/store.ser");
      
      Account account = accountRegister.getAccount(username, password);

      ControllerData controllerData = new ControllerData.ControllerDataBuilder()
            .withSceneManager(sceneManager)
            .withBudgetManager(budgetManager)
            .withAccount(account)
            .build();
      
      sceneManager.addToHistory("home");
      sceneManager.setRootAsFxml("home", controllerData);
    } catch (IllegalArgumentException | NullPointerException e) {
      logger.log(Level.WARNING, e.getMessage(), e);
      Alert alert = new Alert(Alert.AlertType.WARNING, e.getMessage());
      alert.showAndWait();
    } catch (IOException e) {
      logger.log(Level.SEVERE, e.getMessage(), e);
      Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
      alert.showAndWait();
    }
  }

  /**
   * The method handles the button press for taking the user back to the login page.
   *
   * @param event The ActionEvent object generated when the button is pressed.
   */
  @FXML
  private void backToLogInPressed(ActionEvent event) {
    try {
      ControllerData controllerData = new ControllerData.ControllerDataBuilder()
              .withSceneManager(sceneManager)
              .withBudgetManager(budgetManager)
              .build();
      sceneManager.setRootAsFxml("login/showLoginPage", controllerData);
    } catch (IOException e) {
      logger.log(Level.SEVERE, e.getMessage(), e);
      Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
      alert.showAndWait();
    }
  }
}