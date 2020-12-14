package model.planner;

import java.util.List;
import model.date.PlannerDate;
import model.theme.PlannerTheme;

/**
 * An IPlannerModel implementation that represents a simple planner. It uses
 * the PlannerTheme class as the theme and the PlannerDate class as the
 * date. Test
 */
public class SimplePlannerModel implements IPlannerModel<PlannerTheme, PlannerDate> {

  @Override
  public void addTask(PlannerTheme task) {

  }

  @Override
  public List<PlannerTheme> getTasks() {
    return null;
  }

  @Override
  public void removeTask(PlannerTheme task) {

  }

  @Override
  public void moveTask(PlannerTheme task, PlannerDate date) {

  }

  @Override
  public void addPoints(int points) {

  }

  @Override
  public int getTotalPoints() {
    return 0;
  }

  @Override
  public void removePoints(int points) {

  }

  @Override
  public void getUserName() {

  }

  @Override
  public void setUserName(String name) {

  }

  @Override
  public List<PlannerTheme> getThemes() {
    return null;
  }

  @Override
  public void addTheme(PlannerTheme theme) {

  }
}
