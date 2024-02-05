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
 * The AddGoalController class is responsible for
 * adding a financial goal to a budget.
 *
 * @author Markus Evald Dalbakk
 * @version 1.0
 * @since April 24, 2023.
 */
public class AddGoalController extends BaseController {
  @FXML
  private TextField minimumValueField;

  /**
   * The method handles the button press for adding a goal.
   *
   * @param event The ActionEvent object generated when the button is pressed.
   */
  @FXML
  private void addButtonPressed(ActionEvent event) {
    try {
      float minimumValue = Float.parseFloat(minimumValueField.getText());
      budgetManager.addGoalToBudget(account.getUsername(), budgetId, minimumValue);
      accountRegister.saveAccounts(PATH_OF_FILE_ACCOUNTS);
      switchScene("budget/showBudget");
    } catch (NoSuchElementException | NullPointerException | IllegalArgumentException e) {
      alertException(Level.WARNING, AlertType.WARNING, e);
    } catch (IOException e) {
      alertException(Level.SEVERE, AlertType.ERROR, e);
    }
  }
}