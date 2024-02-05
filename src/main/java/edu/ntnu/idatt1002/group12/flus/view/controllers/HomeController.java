package edu.ntnu.idatt1002.group12.flus.view.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * The HomeController class is responsible for controlling
 * the home screen of the application.
 *
 * @author Markus Evald Dalbakk
 * @version 1.0
 * @since April 25, 2023.
 */
public class HomeController extends BaseController {

  @FXML
  private Button homeButton;

  @FXML
  private Label welcomeLabel;

  /**
   * The method initializes the controller class.
   */
  @FXML
  @Override
  public void initialize() {
    super.initialize();
    homeButton.setDisable(true);
    welcomeLabel.setText("Welcome " + account.getUsername() + "!");
  }
}