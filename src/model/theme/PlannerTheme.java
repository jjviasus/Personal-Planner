package model.theme;

import java.awt.Color;
import model.date.PlannerDate;

/**
 * An ITheme implementation to be used as the theme for an IPlanner. Allows a user
 * to get the characteristics of this theme.
 */
public class PlannerTheme implements ITheme, Comparable<PlannerTheme> {
  private final String themeName;
  private final String textFont;
  private final Color textColor;
  private final Color firstColor;
  private final Color secondaryColor;
  private final Color thirdColor;
  private final int cost;

  public enum Theme {
    LIGHT,
    DARK
  }

  /**
   * Constructs a theme with the given values.
   */
  public PlannerTheme(String themeName, String textFont, Color textColor, int textSize,
      Color firstColor, Color secondaryColor, Color thirdColor, int cost) {
    this.themeName = themeName;
    this.textFont = textFont;
    this.textColor = textColor;
    this.firstColor = firstColor;
    this.secondaryColor = secondaryColor;
    this.thirdColor = thirdColor;
    this.cost = cost;
  }

  @Override
  public String getTextFont() {
    return this.textFont;
  }

  @Override
  public Color getTextColor() {
    return this.textColor;
  }

  @Override
  public Color getFirstColor() {
    return this.firstColor;
  }

  @Override
  public Color getSecondColor() {
    return this.secondaryColor;
  }

  @Override
  public Color getThirdColor() {
    return this.thirdColor;
  }

  @Override
  public String getThemeName() {
    return this.themeName;
  }

  @Override
  public int getCost() { return this.cost;}

  @Override
  public int compareTo(PlannerTheme o) {
    return this.cost - o.cost;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    PlannerTheme theme = (PlannerTheme) obj;
    return themeName == theme.themeName && textFont == theme.textFont && textColor == theme.textColor && firstColor == theme.firstColor && secondaryColor == theme.secondaryColor && thirdColor == theme.thirdColor;
  }

  @Override
  public int hashCode() {
    return themeName.length() * 100000 + textFont.length() * 10;
  }

  @Override
  public String toString() {
    return themeName;
  }
}
