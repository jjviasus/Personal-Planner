package model.theme;

import java.awt.Color;
//TODO add exceptions that should be thrown for invalid arguments
/**
 * An interface that represents a theme and keeps track of its unique characteristics.
 */
public interface ITheme {
  /**
   * Sets this theme's font to the provided font.
   * @param font the name of the font as a String
   */
  public void setFont(String font);

  /**
   * Sets this theme's text color to the provided color.
   * @param color the desired color the text should be set to
   */
  public void setTextColor(Color color);

  /**
   * Sets this theme's text size to the provided size.
   * @param size the desired size the text should be set to as an int
   */
  public void setTextSize(int size);

  /**
   * Sets this theme's background color to the provided color.
   * @param color the desired color the background should be set to
   */
  public void setBackgroundColor(Color color);

  /**
   * Sets this theme's name to the provided name.
   * @param name the desired name the theme will have as a String
   */
  public void setThemeName(String name);

  // set panel color
}
