package model.planner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import model.date.PlannerDate;
import model.theme.PlannerTheme;
//TODO check for valid arguments
/**
 * An IPlannerModel implementation that represents a simple planner. It uses
 * the PlannerTheme class as the theme and the PlannerDate class as the
 * date.
 */
public class SimplePlannerModel implements IPlannerModel<PlannerTheme, PlannerDate> {
  // for every date there is a list of tasks.
  private TreeMap<PlannerDate, List<String>> taskMap;
  private int totalPoints;
  private String userName;
  private List<PlannerTheme> themeList;
  private PlannerTheme currentTheme;

  /**
   * Initializes a new simple planner.
   */
  public SimplePlannerModel() {
    this.taskMap = new TreeMap<>();
    this.totalPoints = 0;
    this.userName = "Enter your name!";
    this.themeList = new ArrayList<>(); // set a default theme?
  }

  @Override
  public void addTask(String task, PlannerDate date) {
    // check if the date already exists
    if (this.taskMap.containsKey(date)) {
      // add the task to the list of tasks at the given date
      this.taskMap.get(date).add("task as a string");
    } else {
      // put the date as a new key in the tree map
      this.taskMap.put(date, Arrays.asList(new String[]{task}));
    }
  }

  @Override
  public List<String> getTasks() {
    // returns a copy of the tasks
    List copy = new ArrayList();
    copy.addAll(taskMap.values());
    return copy;
  }

  @Override
  public void removeTask(String task, PlannerDate date) {
    this.taskMap.get(date).remove(task);
  }

  @Override
  public void moveTask(String task, PlannerDate initialDate, PlannerDate newDate) {
    // remove from initial date
    this.taskMap.get(initialDate).remove(task);

    // first check if the newDate already exists or not
    if (this.taskMap.containsKey(newDate)) {
      // add the task to the date key that already exists
      this.taskMap.get(newDate).add(task);
    } else {
      // create the new date key and add the task
      this.taskMap.put(newDate, Arrays.asList(new String[]{task}));
    }
  }

  @Override
  public void addPoints(int points) {
    this.totalPoints+=points;
  }

  @Override
  public int getTotalPoints() {
    return this.totalPoints;
  }

  @Override
  public void removePoints(int points) {
    this.totalPoints-=points;
  }

  @Override
  public String getUserName() {
    return this.userName;
  }

  @Override
  public void setUserName(String name) {
    this.userName = name;
  }

  @Override
  public List<PlannerTheme> getThemes() {
    // return a copy of the list of unlocked themes
    List copy = new ArrayList();
    copy.addAll(this.themeList);
    return copy;
  }

  @Override
  public void addTheme(PlannerTheme theme) {
    this.themeList.add(theme);
  }

  @Override
  public void setTheme(PlannerTheme theme) {
    this.currentTheme=theme;
  }
}
