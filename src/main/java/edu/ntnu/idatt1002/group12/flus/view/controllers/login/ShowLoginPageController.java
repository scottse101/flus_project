package edu.ntnu.idatt1002.group12.flus.view.controllers.login;

import edu.ntnu.idatt1002.group12.flus.model.Account;
import edu.ntnu.idatt1002.group12.flus.view.controllers.AssignableController;
import edu.ntnu.idatt1002.group12.flus.view.controllers.ControllerData;
import edu.ntnu.idatt1002.group12.flus.controller.AccountRegister;
import edu.ntnu.idatt1002.group12.flus.controller.BudgetManager;
import edu.ntnu.idatt1002.group12.flus.controller.SceneManager;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * The ShowLoginPageController class handles the actions
 * related to the login page.
 *
 * @author Markus Evald Dalbakk and Ramtin Samavat
 * @version 1.0
 * @since April 23, 2023.
 */
public class ShowLoginPageController implements AssignableController {
  private static final Logger logger = Logger.getLogger(ShowLoginPageController.class.getName());
  private AccountRegister accountRegister;
  private SceneManager sceneManager;
  private BudgetManager budgetManager;
    
  /**
   * The method assigns the SceneManager instance variable and
   * initializes the AccountRegister and BudgetManager.
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
  private TextField usernameField, passwordField;

  @FXML
  private Button loginButton;

  /**
   * The method initializes the controller by loading the
   * stored accounts using the AccountRegister.
   */
  @FXML
  public void initialize() {

    loginButton.setDisable(true);

    usernameField.textProperty().addListener((observable, oldValue, newValue) -> {
      loginButton.setDisable(usernameField.getText().isEmpty() || passwordField.getText().isEmpty());
    });

    passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
      loginButton.setDisable(usernameField.getText().isEmpty() || passwordField.getText().isEmpty());
    });

    try {
      accountRegister.loadAccounts("src/main/resources/storage/store.ser");
    } catch (IOException | ClassNotFoundException e) {
      logger.log(Level.SEVERE, e.getMessage(), e);
      Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
      alert.showAndWait();
    }
  }

  /**
   * The method handles the create account button.
   *
   * @param event The ActionEvent object generated when the button is pressed.
   */
  @FXML
  private void createAccountPressed(ActionEvent event) {
    try {
      ControllerData controllerData = new ControllerData.ControllerDataBuilder()
              .withSceneManager(sceneManager)
              .withBudgetManager(budgetManager)
              .build();
      sceneManager.setRootAsFxml("login/createAccount", controllerData);
    } catch (IOException e) {
      logger.log(Level.SEVERE, e.getMessage(), e);
      Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
      alert.showAndWait();
    }
  }

  /**
   * The method handles the login button by verifying the username and password.
   *
   * @param event The ActionEvent object generated when the button is pressed.
   */
  @FXML
  private void loginButtonPressed(ActionEvent event) {
    try {
      String username = usernameField.getText();
      String password = passwordField.getText();
      Account account = accountRegister.getAccount(username, password);

      ControllerData controllerData = new ControllerData.ControllerDataBuilder()
              .withSceneManager(sceneManager)
              .withBudgetManager(budgetManager)
              .withAccount(account)
              .build();

      sceneManager.addToHistory("home");
      sceneManager.setRootAsFxml("home", controllerData);
    } catch (Exception e) {
      logger.log(Level.SEVERE, e.getMessage(), e);
      Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
      alert.showAndWait();
    }    
  }
}