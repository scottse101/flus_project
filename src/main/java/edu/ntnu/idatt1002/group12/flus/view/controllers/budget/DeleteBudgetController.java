package edu.ntnu.idatt1002.group12.flus.view.controllers.budget;

import edu.ntnu.idatt1002.group12.flus.model.Budget;
import edu.ntnu.idatt1002.group12.flus.view.controllers.BaseController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.logging.Level;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;

/**
 * The DeleteBudgetController class is responsible
 * for deleting a budget.
 *
 * @author Henrik Nilsen
 * @version 1.0
 * @since April 24, 2023.
 */
public class DeleteBudgetController extends BaseController {

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
   * The method handles the button press for deleting a budget.
   *
   * @param event The ActionEvent object generated when the button is pressed.
   */
  @FXML
  private void deleteButtonPressed(ActionEvent event) {

    String chosenBudgetId = chosenBudget.getValue();

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Delete Budget");
    alert.setHeaderText("Are you sure you want to delete this Budget?");
    alert.setContentText(chosenBudgetId);

    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {
      try {
        budgetManager.deleteBudget(account.getUsername(), chosenBudgetId);
        accountRegister.saveAccounts(PATH_OF_FILE_ACCOUNTS);
        switchScene("budget/listBudgets");
      } catch (NoSuchElementException | NullPointerException e) {
        alertException(Level.WARNING, Alert.AlertType.WARNING, e);
      } catch (IOException e) {
        alertException(Level.SEVERE, Alert.AlertType.ERROR, e);
      }
    }
  }
}
