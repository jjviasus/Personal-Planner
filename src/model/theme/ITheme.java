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
   * @return font
   */
  public String getTextFont();

  /**
   * Gets this theme's text color.
   * @return color
   */
  public Color getTextColor();

  /**
   * Gets this theme's text size.
   * @return text size
   */
  public int getTextSize();

  /**
   * Gets this theme's first color.
   * @return color
   */
  public Color getFirstColor();

  /**
   * Gets this theme's second color.
   * @return color
   */
  public Color getSecondColor();

  /**
   * Gets this theme's third color.
   * @return color
   */
  public Color getThirdColor();

  /**
   * Gets this theme's name.
   * @return name
   */
  public String getThemeName();

  /**
   * Gets the cost of this theme
   * @return the cost as an int
   */
  public int getCost();

  /**
   * Gets the id of this theme
   * @return the id as an int
   */
  public int getID();
}
