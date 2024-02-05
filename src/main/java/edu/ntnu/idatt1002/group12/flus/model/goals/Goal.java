package edu.ntnu.idatt1002.group12.flus.model.goals;

import edu.ntnu.idatt1002.group12.flus.model.Budget;

/**
 * The interface represents a goal in a budget.
 *
 * @author Henrik Gulbrandsen Nilsen, Ramtin Samavat.
 * @version 18.03.2023
 */
public interface Goal {

  /**
   * The method checks if the goal has been achieved.
   *
   * @param budget the budget that has the goal.
   * @return true if the goal is achieved, false otherwise.
   */
  boolean completed(Budget budget);
}