package edu.ntnu.idatt1002.group12.flus.view.controllers;

import edu.ntnu.idatt1002.group12.flus.model.Account;
import edu.ntnu.idatt1002.group12.flus.controller.BudgetManager;
import edu.ntnu.idatt1002.group12.flus.controller.SceneManager;

/**
 * The ControllerData class is responsible for storing
 * data which is necessary for the controllers.
 *
 * @author Markus Evald Dalbakk
 * @version 1.0
 * @since April 24, 2023.
 */
public class ControllerData {
  private SceneManager sceneManager;
  private BudgetManager budgetManager;
  private Account account;
  private String budgetId;
  private boolean sidebarActive;


  private ControllerData() {}

  /**
   * The method retrieves the sceneManager.
   *
   * @return the sceneManager.
   */
  public SceneManager getSceneManager() {
    return sceneManager;
  }

  /**
   * The method retrieves the budgetManager.
   *
   * @return the budgetManager.
   */
  public BudgetManager getBudgetManager() {
    return budgetManager;
  }

  /**
   * The method retrieves the account.
   *
   * @return the account.
   */
  public Account getAccount() {
    return account;
  }

  /**
   * The method retrieves the budget ID.
   *
   * @return the budget ID.
   */
  public String getBudgetId() {
    return budgetId;
  }

  /**
   * The method retrieves the status of the sidebar.
   *
   * @return the status of the sidebar
   */
  public boolean getSidebarActive() {
    return sidebarActive;
  }

  /**
   * Builder class for the ControllerData class. The class constructs a
   * ControllerData object with optional parameters.
   */
  public static class ControllerDataBuilder {
    private SceneManager sceneManager;
    private BudgetManager budgetManager;
    private Account account;
    private String budgetId;
    private boolean sidebarActive;

    /**
     * The method sets the sceneManager.
     *
     * @param sceneManager the SceneManager object to be stored.
     * @return this builder object.
     */
    public ControllerDataBuilder withSceneManager(SceneManager sceneManager) {
      this.sceneManager = sceneManager;
      return this;
    }

    /**
     * The method sets the budgetManager.
     *
     * @param budgetManager the budgetManager object to be stored.
     * @return this builder object.
     */
    public ControllerDataBuilder withBudgetManager(BudgetManager budgetManager) {
      this.budgetManager = budgetManager;
      return this;
    }

    /**
     * The method sets the account.
     *
     * @param account the account object to be stored.
     * @return this builder object.
     */
    public ControllerDataBuilder withAccount(Account account) {
      this.account = account;
      return this;
    }

    /**
     * The method sets the budget ID.
     *
     * @param budgetId the budget ID to be stored.
     * @return this builder object.
     */
    public ControllerDataBuilder withBudgetId(String budgetId) {
      this.budgetId = budgetId;
      return this;
    }

    /**
     * The method sets the status of the sidebar.
     *
     * @param sidebarActive the status of the sidebar to be stored.
     * @return this builder object.
     */
    public ControllerDataBuilder withSidebarActive(boolean sidebarActive) {
      this.sidebarActive = sidebarActive;
      return this;
    }

    /**
     * Constructs a ControllerData object with the set values.
     *
     * @return a ControllerData object.
     */
    public ControllerData build() {
      ControllerData controllerData = new ControllerData();
      controllerData.sceneManager = this.sceneManager;
      controllerData.budgetManager = this.budgetManager;
      controllerData.account = this.account;
      controllerData.budgetId = this.budgetId;
      controllerData.sidebarActive = this.sidebarActive;
      return controllerData;
    }
  }
}
