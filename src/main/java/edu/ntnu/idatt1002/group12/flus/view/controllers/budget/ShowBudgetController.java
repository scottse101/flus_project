package edu.ntnu.idatt1002.group12.flus.view.controllers.budget;

import edu.ntnu.idatt1002.group12.flus.model.Budget;
import edu.ntnu.idatt1002.group12.flus.view.controllers.BaseController;
import edu.ntnu.idatt1002.group12.flus.model.goals.FinancialGoal;
import edu.ntnu.idatt1002.group12.flus.model.transactions.Expense;
import edu.ntnu.idatt1002.group12.flus.model.transactions.Income;
import java.io.IOException;
import java.util.Optional;
import java.util.function.Function;
import java.util.logging.Level;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

/**
 * The ShowBudgetController class provides a way
 * for users to view and manage their budgets.
 *
 * @author Henrik Nilsen
 * @version 1.0
 * @since April 24, 2023.
 */
public class ShowBudgetController extends BaseController {

  private Budget chosenBudget;

  @FXML
  private Label title, totalBalance;

  @FXML
  private ListView<Income> incomeListView;

  @FXML
  private ListView<Expense> expenseListView;

  @FXML
  private ListView<FinancialGoal> goalListView;

  /**
   * The method converts the completion status
   * of a financial goal from a boolean into a string.
   *
   * @param isGoalCompleted the status of the goal in from of a boolean.
   * @return the status of the goal in form of a string.
   */
  private String goalStatus(boolean isGoalCompleted) {
    String goalStatus = "Not Completed";
    if (isGoalCompleted) {
      goalStatus = "Completed";
    }
    return goalStatus;
  }

  /**
   * The method is used to create a custom list cell for ListView
   * components in the class.
   *
   * @param textExtractor A function that extracts the text for the item.
   * @return A ListCell with a container HBox and a nameLabel Label for the specified item type.
   * @param <T> The type of item for the ListCell.
   */
  private <T> ListCell<T> createListCell(Function<T, String> textExtractor) {
    return new ListCell<T>() {
      private final HBox container = new HBox();
      private final Label nameLabel = new Label();

      {
        container.getChildren().add(nameLabel);
      }

      @Override
      protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
          setGraphic(null);
        } else {
          nameLabel.setText(textExtractor.apply(item));
          setGraphic(container);
        }
      }
    };
  }

  /**
   * The method initializes the controller class.
   */
  @FXML
  public void initialize() {
    super.initialize();

    chosenBudget = budgetManager.getBudget(account.getUsername(), budgetId);

    title.setText(budgetId);
    totalBalance.setText("Total balance: " + String.valueOf(chosenBudget.getBalance()));

    ObservableList<Income> incomeList = FXCollections.observableArrayList();
    incomeList.addAll(chosenBudget.getIncomes());

    ObservableList<Expense> expenseList = FXCollections.observableArrayList();
    expenseList.addAll(chosenBudget.getExpenses());

    ObservableList<FinancialGoal> goalList = FXCollections.observableArrayList();
    goalList.addAll(chosenBudget.getFinancialGoals());

    incomeListView.setItems(incomeList);
    expenseListView.setItems(expenseList);
    goalListView.setItems(goalList);

    incomeListView.setCellFactory(
        param -> createListCell(income -> income.getAmount() + "  -  " + income.getDescription()));
    expenseListView.setCellFactory(param -> createListCell(
        expense -> expense.getAmount() + "  -  " + expense.getDescription()));
    goalListView.setCellFactory(param -> createListCell(
        goal -> goal.getMinimumMoneyValue() + "  -  " + goalStatus(goal.completed(chosenBudget))));

    incomeListView.setOnMouseClicked(event -> {
      if (event.getClickCount() == 2) {
        Income selectedIncome = incomeListView.getSelectionModel().getSelectedItem();
        if (selectedIncome != null) {
          Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
          alert.setTitle("Delete Income");
          alert.setHeaderText("Are you sure you want to delete this income?");
          alert.setContentText(
              selectedIncome.getAmount() + " - " + selectedIncome.getDescription());

          Optional<ButtonType> result = alert.showAndWait();
          if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
              budgetManager.getBudget(account.getUsername(), budgetId).removeIncome(selectedIncome);
              accountRegister.saveAccounts(PATH_OF_FILE_ACCOUNTS);
              switchScene("budget/showBudget");
            } catch (IOException e) {
              Alert alertException = new Alert(Alert.AlertType.WARNING, e.getMessage());
              alertException.showAndWait();
            }
          }
        }
      }
    });

    expenseListView.setOnMouseClicked(event -> {
      if (event.getClickCount() == 2) {
        Expense selectedExpense = expenseListView.getSelectionModel().getSelectedItem();
        if (selectedExpense != null) {
          Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
          alert.setTitle("Delete Expense");
          alert.setHeaderText("Are you sure you want to delete this expense?");
          alert.setContentText(
              selectedExpense.getAmount() + " - " + selectedExpense.getDescription());

          Optional<ButtonType> result = alert.showAndWait();
          if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
              budgetManager.getBudget(account.getUsername(), budgetId)
                  .removeExpense(selectedExpense);
              accountRegister.saveAccounts(PATH_OF_FILE_ACCOUNTS);
              switchScene("budget/showBudget");
            } catch (IOException e) {
              alertException(Level.SEVERE, AlertType.ERROR, e);
            }
          }
        }
      }
    });

    goalListView.setOnMouseClicked(event -> {
      if (event.getClickCount() == 2) {
        FinancialGoal selectedGoal = goalListView.getSelectionModel().getSelectedItem();
        if (selectedGoal != null) {
          Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
          alert.setTitle("Delete Goal");
          alert.setHeaderText("Are you sure you want to delete this goal?");
          alert.setContentText(
              selectedGoal.getMinimumMoneyValue() + " - "
                      + goalStatus(selectedGoal.completed(chosenBudget)));

          Optional<ButtonType> result = alert.showAndWait();
          if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
              budgetManager.getBudget(account.getUsername(), budgetId)
                  .removeGoal(selectedGoal.getMinimumMoneyValue());
              accountRegister.saveAccounts(PATH_OF_FILE_ACCOUNTS);
              switchScene("budget/showBudget");
            } catch (IOException e) {
              Alert alertException = new Alert(AlertType.ERROR, e.getMessage());
              alertException.showAndWait();
            }
          }
        }
      }
    });
  }

  /**
   * The method handles the action for when the user press the
   * button for adding an income to a budget.
   *
   * @param event The ActionEvent object generated when the button is pressed.
   */
  @FXML
  private void addIncomeButtonPressed(ActionEvent event) {
    switchScene("budget/addIncome");
  }

  /**
   * The method handles the action for when the user press the
   * button for adding an expense to a budget.
   *
   * @param event The ActionEvent object generated when the button is pressed.
   */
  @FXML
  private void addExpenseButtonPressed(ActionEvent event) {
    switchScene("budget/addExpense");
  }

  /**
   * The method handles the action for when the user press the
   * button for adding a goal to a budget.
   *
   * @param event The ActionEvent object generated when the button is pressed.
   */
  @FXML
  private void addGoalButtonPressed(ActionEvent event) {
    switchScene("budget/addGoal");
  }
}