package edu.ntnu.idatt1002.group12.flus;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import edu.ntnu.idatt1002.group12.flus.view.controllers.ControllerData;
import edu.ntnu.idatt1002.group12.flus.controller.BudgetManager;
import edu.ntnu.idatt1002.group12.flus.controller.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The App class is the main entry point of the application.
 *
 * @author Ramtin Samavat, Scott Emonanekkul, Markus Dalbakk, Henrik Nilsen and Stian Lyng Stræte.
 * @version 1.0
 * @since April 24, 2023.
 */
public class App extends Application {

  private static final Logger logger = Logger.getLogger(App.class.getName());

  /**
   * The start method of the class. This method is called by the
   * JavaFX framework when the application is launched.
   *
   * @param stage the primary stage for the application.
   */
  @Override
  public void start(Stage stage) {
    try {
      SceneManager sceneManager = new SceneManager(stage);
      BudgetManager budgetManager = new BudgetManager();

      ControllerData controllerData = new ControllerData.ControllerDataBuilder()
              .withSceneManager(sceneManager)
              .withBudgetManager(budgetManager)
              .build();

      sceneManager.setRootAsFxml("login/showLoginPage", controllerData);
      stage.setTitle("Flus");
      stage.setMaximized(true);
      stage.show();
    } catch (IOException e) {
      logger.log(Level.SEVERE, e.getMessage(), e);
    }
  }

  /**
   * The main method of the App class.
   *
   * @param args the command line arguments.
   */
  public static void main(String[] args) {
    launch(args);
  }
}