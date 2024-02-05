package edu.ntnu.idatt1002.group12.flus.view.controllers;

import edu.ntnu.idatt1002.group12.flus.model.Account;
import edu.ntnu.idatt1002.group12.flus.view.BlurTransition;
import edu.ntnu.idatt1002.group12.flus.controller.AccountRegister;
import edu.ntnu.idatt1002.group12.flus.controller.BudgetManager;
import edu.ntnu.idatt1002.group12.flus.controller.SceneManager;
import javafx.scene.control.Alert.AlertType;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * The BaseController class is an abstract class which provides
 * the basic functionality that all controllers in the application
 * will have.
 *
 * @author Markus Evald Dalbakk
 * @version 1.0
 * @since April 24, 2023.
 */
public abstract class BaseController implements AssignableController {

  private static final Logger logger = Logger.getLogger(BaseController.class.getName());
  protected static final String PATH_OF_FILE_ACCOUNTS = "src/main/resources/storage/store.ser";
  protected AccountRegister accountRegister;
  protected SceneManager sceneManager;
  protected BudgetManager budgetManager;
  protected Account account;
  protected String budgetId;
  protected boolean sidebarActive;

  /**
   * The method assigns values to the instance variables
   * based on the provided ControllerData object.
   *
   * @param controllerData The provided ControllerData containing data.
   */
  @Override
  public void assign(ControllerData controllerData) {
    this.accountRegister = AccountRegister.getInstance();
    this.sceneManager = controllerData.getSceneManager();
    this.budgetManager = controllerData.getBudgetManager();
    this.account = controllerData.getAccount();
    this.budgetId = controllerData.getBudgetId();
    this.sidebarActive = controllerData.getSidebarActive();
  }

  //#region fxml

  @FXML
  private Pane underlay, overlay, sidebar, filter;

  @FXML
  private Button back, forward, menuOpen, menuClose;

  /**
   * The method initializes the controller class.
   */
  @FXML
  public void initialize() {
    back.setDisable(!sceneManager.canGoBack());
    forward.setDisable(!sceneManager.canGoForward());
    showSidebar(sidebarActive);
  }

  /**
   * The method handles the action for when the back button is pressed.
   *
   * @param event The ActionEvent object generated when the button is pressed.
   */
  @FXML
  private void backButtonPressed(ActionEvent event) {
    try {
      sceneManager.goBack(createControllerData());
    } catch (IOException e) {
      alertException(Level.WARNING, AlertType.ERROR, e);
    }
  }

  /**
   * The method handles the action for when the forward button is pressed.
   *
   * @param event The ActionEvent object generated when the button is pressed.
   */
  @FXML
  private void forwardButtonPressed(ActionEvent event) {
    try {
      sceneManager.goForward(createControllerData());
    } catch (IOException e) {
      alertException(Level.WARNING, AlertType.ERROR, e);
    }
  }

  /**
   * The method handles the action for when the home button is pressed.
   *
   * @param event The ActionEvent object generated when the button is pressed.
   */
  @FXML
  private void homeButtonPressed(ActionEvent event) {
    switchScene("home");
  }

  /**
   * The method handles the action for when the menu button is opened.
   *
   * @param event The ActionEvent object generated when the button is pressed.
   */
  @FXML
  private void menuOpenButtonPressed(ActionEvent event) {
    openSidebar();
  }

  /**
   * The method handles the action for when the menu button is closed.
   *
   * @param event The ActionEvent object generated when the button is pressed.
   */
  @FXML
  private void menuCloseButtonPressed(ActionEvent event) {
    closeSidebar();
  }

  /**
   * The method handles the action for when the check-in option is pressed.
   *
   * @param event The ActionEvent object generated when the button is pressed.
   */
  @FXML
  private void checkInOptionPressed(ActionEvent event) {
    if (sidebarActive) {
      switchScene("checkin/checkinExpense");
    }
  }

  /**
   * The method handles the action for when the budgets option is pressed.
   *
   * @param event The ActionEvent object generated when the button is pressed.
   */
  @FXML
  private void budgetsOptionPressed(ActionEvent event) {
    if (sidebarActive) {
      switchScene("budget/listBudgets");
    }
  }

  /**
   * The method handles the action for when the profile option is pressed.
   *
   * @param event The ActionEvent object generated when the button is pressed.
   */
  @FXML
  private void profileOptionPressed(ActionEvent event) {
    if (sidebarActive) {
      switchScene("profile/showProfile");
    }
  }

  /**
   * The method handles the action for when the log-out option is pressed.
   *
   * @param event The ActionEvent object generated when the button is pressed.
   */
  @FXML
  private void logOutOptionPressed(ActionEvent event) {
    if (sidebarActive) {
      Alert alert = new Alert(AlertType.CONFIRMATION);
      alert.setTitle("Log out confirmation");
      alert.setHeaderText("Are you sure you want to log out of this account?");
      alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

      Optional<ButtonType> result = alert.showAndWait();
      if (result.isPresent() && result.get() == ButtonType.YES) {
        switchScene("login/showLoginPage");
      } else {
        closeSidebar();
      }
    }
  }

  /**
   * The method handles the action for when the exit option is pressed.
   *
   * @param event The ActionEvent object generated when the button is pressed.
   */
  @FXML
  private void exitOptionPressed(ActionEvent event) {
    if (sidebarActive) {
      Alert alert = new Alert(AlertType.CONFIRMATION);
      alert.setTitle("Exit confirmation");
      alert.setHeaderText("Are you sure you want to exit this application?");
      alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

      Optional<ButtonType> result = alert.showAndWait();
      if (result.isPresent() && result.get() == ButtonType.YES) {
        System.exit(0);
      } else {
        closeSidebar();
      }
    }
  }

  //#endregion

  //#region Utility

  /**
   * The method switches the current scene to the one
   * defined by the given FXML file path.
   *
   * @param fxml the file path to the FXML file of the new scene.
   */
  protected void switchScene(String fxml) {
    try {
      sceneManager.addToHistory(fxml);
      sceneManager.setRootAsFxml(fxml, createControllerData());
    } catch (IOException | NullPointerException e) {
      alertException(Level.SEVERE, AlertType.ERROR, e);
    }
  }

  /**
   * The method logs an exception with the given level
   * and shows an alert message for the exception.
   *
   * @param levelType The logging level to use for the exception.
   * @param type The type of alert to show.
   * @param exception The exception to log and show in the alert message.
   */
  protected void alertException(Level levelType, AlertType type, Exception exception) {
    logger.log(levelType, exception.getMessage(), exception);
    alertMessage(type, exception.getMessage());
  }

  /**
   * The method shows an alert message of the given type and message.
   *
   * @param type The message to display in the alert.
   * @param message The message to display in the alert.
   */
  protected void alertMessage(AlertType type, String message) {
    Alert alert = new Alert(type, message);
    alert.showAndWait();
  }

  /**
   * The method creates a new instance of ControllerData.
   *
   * @return A new instance of ControllerData.
   */
  private ControllerData createControllerData() {
    return new ControllerData.ControllerDataBuilder()
            .withSceneManager(sceneManager)
            .withBudgetManager(budgetManager)
            .withAccount(account)
            .withBudgetId(budgetId)
            .withSidebarActive(sidebarActive)
            .build();
  }

  //#endregion

  //#region Sidebar

  private final Duration slideDuration = new Duration(250);
  private final double blurRadius = 10;
  private final double filterOpacity = 0.2;
  private final double layoutX = 300;

  /**
   * The method shows or hides the sidebar depending
   * on the value of the boolean parameter.
   *
   * @param value the boolean parameter.
   */
  protected void showSidebar(boolean value) {
    if (value) {
      overlay.setVisible(true);
      underlay.setEffect(new GaussianBlur(blurRadius));
      filter.setOpacity(filterOpacity);
      sidebar.setLayoutX(0);
      closeSidebar();
    } else {
      overlay.setVisible(false);
      sidebar.setLayoutX(layoutX);
    }
  }

  /**
   * The method opens the sidebar.
   */
  private void openSidebar() {
    overlay.setVisible(true);
    menuOpen.setDisable(true);

    BlurTransition blurTransition = new BlurTransition(slideDuration, underlay, 0, blurRadius);
    blurTransition.setInterpolator(Interpolator.EASE_OUT);
    blurTransition.play();

    FadeTransition fadeTransition = new FadeTransition(slideDuration, filter);
    fadeTransition.setInterpolator(Interpolator.EASE_OUT);
    fadeTransition.setFromValue(0);
    fadeTransition.setToValue(filterOpacity);
    fadeTransition.play();

    TranslateTransition translateTransition = new TranslateTransition(slideDuration, sidebar);
    translateTransition.setInterpolator(Interpolator.EASE_OUT);
    translateTransition.setFromX(layoutX);
    translateTransition.setToX(0);
    translateTransition.play();

    translateTransition.setOnFinished((ActionEvent e) ->  {
      menuClose.setDisable(false);
      sidebarActive = true;
    });
  }

  /**
   * The method Closes the sidebar.
   */
  private void closeSidebar() {
    menuClose.setDisable(true);
    sidebarActive = false;

    BlurTransition blurTransition = new BlurTransition(slideDuration, underlay, blurRadius, 0);
    blurTransition.setInterpolator(Interpolator.EASE_OUT);
    blurTransition.play();

    FadeTransition fadeTransition = new FadeTransition(slideDuration, filter);
    fadeTransition.setInterpolator(Interpolator.EASE_OUT);
    fadeTransition.setFromValue(filterOpacity);
    fadeTransition.setToValue(0);
    fadeTransition.play();

    TranslateTransition translateTransition = new TranslateTransition(slideDuration, sidebar);
    translateTransition.setInterpolator(Interpolator.EASE_OUT);
    translateTransition.setFromX(0);
    translateTransition.setToX(layoutX);
    translateTransition.play();

    translateTransition.setOnFinished((ActionEvent e) -> {
      overlay.setVisible(false);
      menuOpen.setDisable(false);
    });
  }

  //#endregion
}
