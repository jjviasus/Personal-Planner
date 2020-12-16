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

  /**
   * Constructs a theme with the given values.
   */
  public PlannerTheme(String themeName, String textFont, Color textColor, int textSize,
      Color firstColor, Color secondaryColor, Color thirdColor) {
    this.themeName = themeName;
    this.textFont = textFont;
    this.textColor = textColor;
    this.firstColor = firstColor;
    this.secondaryColor = secondaryColor;
    this.thirdColor = thirdColor;
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
  public int compareTo(PlannerTheme o) {
    return (this.firstColor.getBlue() * 100000 + this.secondaryColor.getBlue() * 100 + this.thirdColor.getBlue()) -
        (o.firstColor.getBlue() * 100000 + o.secondaryColor.getBlue() * 100 + o.thirdColor.getBlue());
  }
}
