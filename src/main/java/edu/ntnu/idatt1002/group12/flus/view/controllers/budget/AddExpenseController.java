package edu.ntnu.idatt1002.group12.flus.view.controllers.budget;

import edu.ntnu.idatt1002.group12.flus.view.controllers.BaseController;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.logging.Level;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

/**
 * The AddExpenseController class is responsible for
 * adding an expense to a budget.
 *
 * @author Henrik Nilsen
 * @version 1.0
 * @since April 24, 2023.
 */
public class AddExpenseController extends BaseController {

  @FXML
  private TextField descriptionField, expenseField;

  /**
   * The method handles the button press for adding an expense.
   *
   * @param event The ActionEvent object generated when the button is pressed.
   */
  @FXML
  private void addButtonPressed(ActionEvent event) {
    try {
      String description = descriptionField.getText();
      float expense = Float.parseFloat(expenseField.getText());
      budgetManager.addExpenseToBudget(account.getUsername(), budgetId, description, expense);
      accountRegister.saveAccounts(PATH_OF_FILE_ACCOUNTS);
      switchScene("budget/showBudget");
    } catch (NoSuchElementException | NullPointerException | IllegalArgumentException e) {
      alertException(Level.WARNING, AlertType.WARNING, e);
    } catch (IOException e) {
      alertException(Level.SEVERE, AlertType.ERROR, e);
    }
  }
}