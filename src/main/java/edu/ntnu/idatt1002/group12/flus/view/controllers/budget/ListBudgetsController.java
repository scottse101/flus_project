package edu.ntnu.idatt1002.group12.flus.view.controllers.budget;

import edu.ntnu.idatt1002.group12.flus.model.Budget;
import edu.ntnu.idatt1002.group12.flus.view.controllers.BaseController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;

/**
 * The ListBudgetsController class is responsible for
 * displaying a list of all budgets for the user to select from.
 *
 * @author Markus Evald Dalbakk
 * @version 1.0
 * @since April 24, 2023.
 */
public class ListBudgetsController extends BaseController {

  @FXML
  private Button addButton;

  @FXML
  private ListView<Budget> budgetList;

  /**
   * The method initializes the controller class.
   */
  @FXML
  @Override
  public void initialize() {
    super.initialize();

    ObservableList<Budget> list = FXCollections.observableArrayList();
    list.addAll(account.getBudgets());

    budgetList.setItems(list);

    budgetList.setOnMouseClicked(event -> {
      if (event.getClickCount() == 2) {
        Budget selectedBudget = budgetList.getSelectionModel().getSelectedItem();
        budgetId = selectedBudget.getBudgetId();
        switchScene("budget/showBudget");
      }
    });

    budgetList.setCellFactory(param -> new ListCell<Budget>() {
      private final HBox container = new HBox();
      private final Label nameLabel = new Label();

      {
        container.getChildren().add(nameLabel);
      }

      @Override
      protected void updateItem(Budget budget, boolean empty) {
        super.updateItem(budget, empty);

        if (empty || budget == null) {
          setGraphic(null);
        } else {
          nameLabel.setText(budget.getBudgetId() + "  -  " + budget.getDate());
          setGraphic(container);
        }
      }
    });
  }

  /**
   * The method handles the action for when the user press the
   * button for creating a new budget.
   *
   * @param event The ActionEvent object generated when the button is pressed.
   */
  @FXML
  private void newBudgetPressed(ActionEvent event) {
    switchScene("budget/addBudget");
  }

  /**
   * The method handles the action for when the user press the
   * button for deleting a budget.
   *
   * @param event The ActionEvent object generated when the button is pressed.
   */
  @FXML
  private void deleteBudgetPressed(ActionEvent event) {
    switchScene("budget/deleteBudget");
  }
}
