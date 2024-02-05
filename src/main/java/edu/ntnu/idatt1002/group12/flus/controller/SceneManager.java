package edu.ntnu.idatt1002.group12.flus.controller;

import edu.ntnu.idatt1002.group12.flus.view.controllers.AssignableController;
import edu.ntnu.idatt1002.group12.flus.view.controllers.ControllerData;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The SceneManager class manages the loading and changing of
 * scenes in the application.
 *
 * @author Markus Evald Dalbakk
 * @version 1.0
 * @since April 12, 2023.
 */
public class SceneManager {
  private final Scene scene;
  private final List<String> history;
  private int currentIndex;

  /**
   * Constructs a new SceneManager instance with the given stage.
   *
   * @param stage the stage to set as the root for loaded FXML files.
   */
  public SceneManager(Stage stage) {
    scene = new Scene(new Parent(){});
    scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
    stage.setScene(scene);

    history = new ArrayList<>();
    currentIndex = -1;
  }

  /**
   * The method navigates back in the scene history.
   *
   * @param controllerData the data for the controller of the scene being navigated back to.
   * @throws IOException if an error occurs while loading the FXML file.
   */
  public void goBack(ControllerData controllerData) throws IOException {
    if (canGoBack()) {
      currentIndex--;
      setRootAsFxml(history.get(currentIndex), controllerData);
    }
  }

  /**
   * The method navigates forward in the scene history.
   *
   * @param controllerData the data for the controller of the scene being navigated forward to.
   * @throws IOException if an error occurs while loading the FXML file.
   */
  public void goForward(ControllerData controllerData) throws IOException {
    if (canGoForward()) {
      currentIndex++;
      setRootAsFxml(history.get(currentIndex), controllerData);
    }
  }

  /**
   * The method checks if it is possible to navigate back in the scene history.
   *
   * @return true if navigation is possible, false otherwise.
   */
  public boolean canGoBack() {
    return currentIndex > 0;
  }

  /**
   * The method checks if it is possible to navigate forward in the scene history.
   *
   * @return true if navigation is possible, false otherwise.
   */
  public boolean canGoForward() {
    return currentIndex < history.size() - 1;
  }

  /**
   * The method adds a new FXML file to the scene history
   * and sets the root of the stage to the loaded FXML file.
   *
   * @param fxml the name of the FXML file to load.
   */
  public void addToHistory(String fxml) {
    history.add(fxml);
    currentIndex = history.size() - 1;
  }

  /**
   * The method sets the root of the stage to the FXML file with the given name.
   *
   * @param fxml the name of the FXML file to load.
   * @param controllerData the controller data to be passed to the controller.
   * @throws IOException if an error occurs while loading the FXML file.
   */
  public void setRootAsFxml(String fxml, ControllerData controllerData) throws IOException {
    FXMLLoader loader = createFxmlLoader(fxml);
    loader.setControllerFactory(controllerClass -> {
      try {
        Constructor<?> constructor = controllerClass.getDeclaredConstructor();
        AssignableController controller = (AssignableController) constructor.newInstance();
        controller.assign(controllerData);
        return controller;
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });
    scene.setRoot(loader.load());
  }

  /**
   * The method creates an instance of the FXMLLoader class.
   *
   * @param fxml the name of the FXML file to load.
   * @return the created FXMLLoader instance.
   */
  private FXMLLoader createFxmlLoader(String fxml) {
    return new FXMLLoader(getClass().getResource("/fxml/" + fxml + ".fxml"));
  }
}