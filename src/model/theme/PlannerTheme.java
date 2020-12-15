package model.theme;

import java.awt.Color;
/**
 * An ITheme implementation to be used a the theme for an IPlanner. Allows a user
 * to set and get the characteristics of this theme.
 */
public class PlannerTheme implements ITheme {
  private String font;
  private Color textColor;
  private int textSize;
  private Color backgroundColor;
  private String themeName;

  /**
   * Constructs a theme with default values.
   */
  public PlannerTheme() {
    this.font = "Times New Roman";
    this.textColor = new Color(0,0,0);
    this.textSize = 12;
    this.backgroundColor = new Color(255, 255, 255);
    this.themeName = "Default theme";
  }

  @Override
  public void setFont(String font) throws IllegalArgumentException {
    if (font == null) {
      throw new IllegalArgumentException("null font given");
    }
    this.font = font;
  }

  @Override
  public void setTextColor(Color color) throws IllegalArgumentException {
    if (color == null) {
      throw new IllegalArgumentException("null color given");
    }
    this.textColor = color;
  }

  @Override
  public void setTextSize(int size) throws IllegalArgumentException {
    if (size <= 0) {
      throw new IllegalArgumentException("text size must be positive");
    }
    this.textSize = size;
  }

  @Override
  public void setBackgroundColor(Color color) throws IllegalArgumentException {
    if (color == null) {
      throw new IllegalArgumentException("null color given");
    }
    this.backgroundColor = color;
  }

  @Override
  public void setThemeName(String name) throws IllegalArgumentException {
    if (name == null) {
      throw new IllegalArgumentException("null color given");
    }
    this.themeName = name;
  }
}
