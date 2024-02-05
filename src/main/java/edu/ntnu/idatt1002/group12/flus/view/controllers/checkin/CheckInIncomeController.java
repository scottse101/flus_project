package edu.ntnu.idatt1002.group12.flus.view.controllers.checkin;

import edu.ntnu.idatt1002.group12.flus.model.Budget;
import edu.ntnu.idatt1002.group12.flus.view.controllers.BaseController;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

/**
 * The CheckInIncomeController class is responsible for
 * managing the user's daily income by adding incomes to a budget.
 *
 * @author Henrik Nilsen
 * @version 1.0
 * @since April 24, 2023.
 */
public class CheckInIncomeController extends BaseController {
  @FXML
  private TextField earningsField, earningsThoughtsField;

  private final List<String> listWithBudgets = new ArrayList<>();

  @FXML
  private ComboBox<String> chosenBudget;

  /**
   * The method initializes the controller class.
   */
  @FXML
  public void initialize() {
    super.initialize();

    for (Budget budget : account.getBudgets()) {
      listWithBudgets.add(budget.getBudgetId());
    }
    ObservableList<String> observableBudgetList = FXCollections.observableList(listWithBudgets);
    chosenBudget.setItems(observableBudgetList);
  }

  /**
   * The method handles the event when the user clicks on the button for
   * adding an income and proceed in the application.
   *
   * @param event The ActionEvent object generated when the button is pressed.
   */
  @FXML
  private void incomeNextButtonPressed(ActionEvent event) {
    String incomeThoughts = earningsThoughtsField.getText();
    String chosenBudgetId = chosenBudget.getValue();

    try {
      if (incomeThoughts.isEmpty() && earningsField.getText().isEmpty()) {
        switchScene("home");
      } else {
        float checkInIncome = Float.parseFloat(earningsField.getText());
        budgetManager
            .addIncomeToBudget(account.getUsername(), chosenBudgetId,
                incomeThoughts, checkInIncome);
        switchScene("home");
      }
    } catch (NoSuchElementException | NullPointerException | IllegalArgumentException e) {
      alertException(Level.WARNING, AlertType.WARNING, e);
    }
  }
}