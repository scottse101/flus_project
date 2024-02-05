package edu.ntnu.idatt1002.group12.flus.view.controllers;

/**
 * This AssignableController interface is implemented by controllers
 * that can be assigned data when they are initialized.
 *
 * @author Markus Evald Dalbakk
 * @version 1.0
 * @since April 24, 2023.
 */
public interface AssignableController {

    /**
     * The method assigns the given object to this controller.
     *
     * @param controllerData the given ControllerData containing the required data.
     */
    public void assign(ControllerData controllerData);
}
