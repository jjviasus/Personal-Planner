package model.theme;

import java.awt.Color;

/**
 * An interface that represents a theme. A theme has a text font, text color,
 * themeName, first, second, and third color. The theme has three levels of colors, the first being
 * the closest layer, the second being the intermediate layer, the third being the background layer
 * that is farthest back.
 */
public interface ITheme {
  /**
   * Gets this theme's text font.
   */
  public String getTextFont();

  /**
   * Gets this theme's text color.
   */
  public Color getTextColor();

  /**
   * Gets this theme's first color.
   */
  public Color getFirstColor();

  /**
   * Gets this theme's second color.
   */
  public Color getSecondColor();

  /**
   * Gets this theme's third color.
   */
  public Color getThirdColor();

  /**
   * Gets this theme's name.
   */
  public String getThemeName();

  /**
   * Gets the cost of this theme
   */
  public int getCost();
}
