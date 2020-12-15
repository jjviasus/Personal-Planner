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
   * @throws IllegalArgumentException thrown if the given font is null
   */
  public void setFont(String font) throws IllegalArgumentException;

  /**
   * Sets this theme's text color to the provided color.
   * @param color the desired color the text should be set to
   * @throws IllegalArgumentException thrown if the given color is null
   */
  public void setTextColor(Color color) throws IllegalArgumentException;

  /**
   * Sets this theme's text size to the provided size.
   * @param size the desired size the text should be set to as an int
   * @throws IllegalArgumentException thrown if the given size is invalid
   */
  public void setTextSize(int size) throws IllegalArgumentException;

  /**
   * Sets this theme's background color to the provided color.
   * @param color the desired color the background should be set to
   * @throws IllegalArgumentException thrown if the given color is null
   */
  public void setBackgroundColor(Color color) throws IllegalArgumentException;

  /**
   * Sets this theme's name to the provided name.
   * @param name the desired name the theme will have as a String
   * @throws IllegalArgumentException thrown if the given name is null
   */
  public void setThemeName(String name) throws IllegalArgumentException;

  // set panel color (we could have secondary and third colors, basically levels
  // of different colors for the theme)
}
