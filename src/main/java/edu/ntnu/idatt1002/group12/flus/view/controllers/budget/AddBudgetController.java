package edu.ntnu.idatt1002.group12.flus.view.controllers.budget;

import edu.ntnu.idatt1002.group12.flus.view.controllers.BaseController;
import java.io.IOException;
import edu.ntnu.idatt1002.group12.flus.controller.BudgetManager;
import java.util.logging.Level;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

/**
 * The AddBudgetController class is responsible for
 * the creation of new budgets for the user.
 *
 * @author Henrik Nilsen
 * @version 1.0
 * @since April 24, 2023.
 */
public class AddBudgetController extends BaseController {

  private final BudgetManager budgetManager = new BudgetManager();

  @FXML
  private TextField budgetIdField;

  @FXML
  private Button addBudgetButton;

  /**
   * The method initializes the controller class.
   */
  @FXML
  public void initialize() {

    addBudgetButton.setDisable(true);

    budgetIdField.textProperty().addListener((observable, oldValue, newValue) -> {
      addBudgetButton.setDisable(budgetIdField.getText().isEmpty());
    });
  }

  /**
   * The method handles the button press for creating a new budget.
   *
   * @param event The ActionEvent object generated when the button is pressed.
   */
  @FXML
  private void addButtonPressed(ActionEvent event) {
    try {
      budgetManager.createBudget(account.getUsername(), budgetIdField.getText());
      accountRegister.saveAccounts(PATH_OF_FILE_ACCOUNTS);
      switchScene("budget/listBudgets");
    } catch (NullPointerException | IllegalArgumentException e) {
      alertException(Level.WARNING, AlertType.WARNING, e);
    } catch (IOException e) {
      alertException(Level.SEVERE, AlertType.ERROR, e);
    }
  }
}