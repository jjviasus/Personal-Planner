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
  private final int textSize;
  private final Color firstColor;
  private final Color secondaryColor;
  private final Color thirdColor;
  private final int cost;
  private final int id;

  /* this.themeMap.put(new PlannerTheme("Dark", "Courier New",
      new Color(255,255,255), 12, new Color(30,30,30),
        new Color(50,50,50), new Color(75,75,75), 10), 10);

        */

/*
  public enum Theme {
    LIGHT("Light", "Times New Roman", new Color(30,30,30), 12, new Color(250,250,250), new Color(200,200,200), new Color(150,150,150), 0),

    private final String themeName;
    private final String textFont;
    private final Color textColor;
    private final Color firstColor;
    private final Color secondaryColor;
    private final Color thirdColor;
    private final int cost;

    private Theme(String themeName, String textFont, Color textColor, int textSize,
        Color firstColor, Color secondaryColor, Color thirdColor, int cost) {
      this.themeName = themeName;
      this.textFont = textFont;
      this.textColor = textColor;
      this.firstColor = firstColor;
      this.secondaryColor = secondaryColor;
      this.thirdColor = thirdColor;
      this.cost = cost;
    }
  }
*/

  /**
   * Constructs a theme with the given values.
   */
  public PlannerTheme(String themeName, String textFont, Color textColor, int textSize,
      Color firstColor, Color secondaryColor, Color thirdColor, int cost, int id) {
    this.themeName = themeName;
    this.textFont = textFont;
    this.textColor = textColor;
    this.textSize = textSize;
    this.firstColor = firstColor;
    this.secondaryColor = secondaryColor;
    this.thirdColor = thirdColor;
    this.cost = cost;
    this.id = id;
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
  public int getTextSize() {
    return this.textSize;
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
  public int getID() {
    return this.id;
  }

  @Override
  public int compareTo(PlannerTheme o) {
    return this.id - o.id;
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
    return themeName == theme.themeName && textFont == theme.textFont && textColor.equals(theme.textColor) && textSize == theme.textSize && firstColor.equals(theme.firstColor) && secondaryColor.equals(theme.secondaryColor) && thirdColor.equals(theme.thirdColor) && cost == theme.cost && id == theme.id;
  }

  @Override
  public int hashCode() {
    return this.id;
  }

  @Override
  public String toString() {
    return themeName;
  }
}
