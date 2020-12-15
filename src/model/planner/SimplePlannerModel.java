package model.planner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import model.date.PlannerDate;
import model.theme.PlannerTheme;
//TODO add exceptions that should be thrown for invalid arguments
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

  // should we have a list of locked themes?

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
  public void addTask(String task, PlannerDate date) throws IllegalArgumentException {
    if (task == null || date == null) {
      throw new IllegalArgumentException("task and date must be non-null");
    }
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
  public void removeTask(String task, PlannerDate date) throws IllegalArgumentException {
    if (task == null || date == null) {
      throw new IllegalArgumentException("task and date must be non-null");
    }
    // first check that the date exists, then check if the task exists
    if (!this.taskMap.containsKey(date)) {
      throw new IllegalArgumentException("date does not exist");
    } else {
      // check that the task exists
      if (this.taskMap.get(date).contains(task)) {
        this.taskMap.get(date).remove(task);
      } else {
        throw new IllegalArgumentException("task does not exist");
      }
    }
  }

  @Override
  public void moveTask(String task, PlannerDate initialDate, PlannerDate newDate) throws
      IllegalArgumentException {
    if (task == null || initialDate == null || newDate == null) {
      throw new IllegalArgumentException("task and dates must be non-null");
    }

    // check that the initialDate exists
    if (!this.taskMap.containsKey(initialDate)) {
      throw new IllegalArgumentException("initialDate does not exist");
    } else {
      // check that the task exists
      if (this.taskMap.get(initialDate).contains(task)) {
        // remove from initial date
        this.taskMap.get(initialDate).remove(task);

        // check if the newDate already exists or not
        if (this.taskMap.containsKey(newDate)) {
          // add the task to the date key that already exists
          this.taskMap.get(newDate).add(task);
        } else {
          // create the new date key and add the task
          this.taskMap.put(newDate, Arrays.asList(new String[]{task}));
        }
      } else {
        throw new IllegalArgumentException("task does not exist");
      }
    }
  }

  @Override
  public void addPoints(int points) {
    if (points <= 0) {
      throw new IllegalArgumentException("points must be positive");
    }
    this.totalPoints+=points;
  }

  @Override
  public int getTotalPoints() {
    return this.totalPoints;
  }

  @Override
  public void removePoints(int points) {
    if (points <= 0 || points > this.totalPoints) {
      throw new IllegalArgumentException("points must be positive and not exceed "
          + "the model's total number of points");
    }
    this.totalPoints-=points;
  }

  @Override
  public String getUserName() {
    return this.userName;
  }

  @Override
  public void setUserName(String name) throws IllegalArgumentException {
    if (name == null) {
      throw new IllegalArgumentException("name must be non-null");
    }
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
    if (theme == null) {
      throw new IllegalArgumentException("theme must be non-null");
    }
    this.themeList.add(theme);
  }

  @Override
  public void setTheme(PlannerTheme theme) {
    if (theme == null) {
      throw new IllegalArgumentException("theme must be non-null");
    }
    this.currentTheme=theme;
  }
}
