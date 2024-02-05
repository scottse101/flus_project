package edu.ntnu.idatt1002.group12.flus.view;

import javafx.animation.Transition;
import javafx.scene.Node;
import javafx.scene.effect.GaussianBlur;
import javafx.util.Duration;

/**
 * The BlurTransition class provides a transition that animates
 * the blurring effect of a given node from a specified start
 * value to an end value.
 *
 * @author Markus Evald Dalbakk
 * @version 1.0
 * @since April 25, 2023.
 */
public class BlurTransition extends Transition {
  private final GaussianBlur blur;
  private final double fromValue;
  private final double toValue;

  /**
   * The method creates a BlurTransition object.
   *
   * @param duration the duration of the transition.
   * @param node the node to apply the blurring effect on.
   * @param fromValue the starting value of the blurring radius.
   * @param toValue the end value of the blurring radius.
   */
  public BlurTransition(Duration duration, Node node, double fromValue, double toValue) {
    this.blur = new GaussianBlur(0);
    this.fromValue = fromValue;
    this.toValue = toValue;

    setCycleDuration(duration);
    node.setEffect(blur);
  }

  /**
   * The method updates the blurring effect radius
   * based on the current progress of the transition.
   *
   * @param frac the current progress of the transition.
   */
  @Override
  protected void interpolate(double frac) {
    double value = fromValue + (toValue - fromValue) * frac;
    blur.setRadius(value);
  }
}
