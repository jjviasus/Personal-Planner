package model.planner;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import model.date.PlannerDate;
import model.task.PlannerTask;
import model.theme.PlannerTheme;

/**
 * An IPlannerModel implementation that represents a simple planner. It uses
 * the PlannerTheme class as the theme and the PlannerDate class as the
 * date. It keeps track of dates and tasks in a TreeMap. PlannerDate's are used
 * as keys and a list of Strings (as the tasks) are used as values.
 */
public class SimplePlannerModel implements IPlannerModel<PlannerTheme, PlannerDate, PlannerTask> {
  private TreeMap<PlannerDate, List<PlannerTask>> taskMap;
  private int totalPoints;
  private String userName;
  private TreeMap<PlannerTheme, Integer> themeMap; // the value is the cost of the theme
  private PlannerTheme currentTheme;

  /**
   * Initializes a new simple planner.
   */
  public SimplePlannerModel() {
    this.taskMap = new TreeMap<>();
    this.totalPoints = 0;
    this.userName = "Enter your name!";
    this.themeMap = new TreeMap<>();
    this.themeMap.put(new PlannerTheme("Dark", "Courier New", new Color(255,255,255), 12, new Color(30,30,30), new Color(50,50,50), new Color(75,75,75)), 10);
    this.themeMap.put(new PlannerTheme("Light", "Times New Roman", new Color(30,30,30), 12, new Color(250,250,250), new Color(200,200,200), new Color(150,150,150)), 0);
    this.currentTheme = this.themeMap.firstKey();
  }

  @Override
  public void addTask(PlannerTask task, PlannerDate date) throws IllegalArgumentException {
    if (task == null || date == null) {
      throw new IllegalArgumentException("task and date must be non-null");
    }
    // check if the date already exists
    if (this.taskMap.containsKey(date)) {
      // add the task to the list of tasks at the given date
      List<PlannerTask> newValueList = new ArrayList<>(this.taskMap.get(date));
      newValueList.add(task);
      this.taskMap.put(date, newValueList);
    } else {
      // put the date as a new key in the tree map
      this.taskMap.put(date, Arrays.asList(new PlannerTask[]{task}));
    }
  }

  @Override
  public List<PlannerTask> getAllTasks() {
    // list of values
    List<List<PlannerTask>> copyValues = new ArrayList();
    copyValues.addAll(taskMap.values());

    // flatten the list of list of strings
    List<PlannerTask> newList = new ArrayList();
    for (List<PlannerTask> los : copyValues) {
      newList.addAll(los);
    }

    // returns a copy of the tasks as one list
    // in the order of their date
    return newList;
  }

  @Override
  public void removeTask(PlannerTask task, PlannerDate date) throws IllegalArgumentException {
    if (task == null || date == null) {
      throw new IllegalArgumentException("task and date must be non-null");
    }
    // first check that the date exists, then check if the task exists
    if (!this.taskMap.containsKey(date)) {
      throw new IllegalArgumentException("date does not exist");
    } else {
      // check that the task exists
      if (this.taskMap.get(date).contains(task)) {
        List<PlannerTask> newValueList = new ArrayList<>(this.taskMap.get(date));
        newValueList.remove(task);
        this.taskMap.put(date, newValueList);
      } else {
        throw new IllegalArgumentException("task does not exist");
      }
    }
  }

  @Override
  public void moveTask(PlannerTask task, PlannerDate initialDate, PlannerDate newDate) throws
      IllegalArgumentException {
    if (task == null || initialDate == null || newDate == null) {
      throw new IllegalArgumentException("task and dates must be non-null");
    }

    // check that initial date does not equal new date
    if (initialDate.equals(newDate)) {
      throw new IllegalArgumentException("dates must be different");
    }

    // check that the initialDate exists
    if (!this.taskMap.containsKey(initialDate)) {
      throw new IllegalArgumentException("initialDate does not exist");
    } else {
      // check that the task exists
      if (this.taskMap.get(initialDate).contains(task)) {
        // remove from initial date
        List<PlannerTask> newValueList = new ArrayList<>(this.taskMap.get(initialDate));
        newValueList.remove(task);
        this.taskMap.put(initialDate, newValueList);

        // check if the newDate already exists or not
        if (this.taskMap.containsKey(newDate)) {
          // add the task to the date key that already exists
          List<PlannerTask> newValueList2 = new ArrayList<>(this.taskMap.get(newDate));
          newValueList2.add(task);
          this.taskMap.put(newDate, newValueList2);
        } else {
          // create the new date key and add the task
          this.taskMap.put(newDate, Arrays.asList(new PlannerTask[]{task}));
        }
      } else {
        throw new IllegalArgumentException("task does not exist");
      }
    }
  }

  @Override
  public int getTotalPoints() {
    return this.totalPoints;
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
    // return a copy of the list of all themes
    List copy = new ArrayList();
    copy.addAll(this.themeMap.keySet());
    return copy;
  }

  @Override
  public void setTheme(PlannerTheme theme) {
    if (theme == null) {
      throw new IllegalArgumentException("theme must be non-null");
    }
    this.currentTheme=theme;
  }

  @Override
  public List<PlannerTask> getTasksAtDate(PlannerDate date) throws IllegalArgumentException {
    if (date == null) {
      throw new IllegalArgumentException("the date must be non-null");
    }

    // check that the date is in the taskMap
    if (!this.taskMap.containsKey(date)) {
      throw new IllegalArgumentException("date has no tasks");
    }

    // return a copy of the tasks in a given day
    List copy = new ArrayList();
    copy.addAll(this.taskMap.get(date));
    return copy;
  }

  @Override
  public void setTaskAsCompleted(PlannerTask task, PlannerDate date) throws IllegalArgumentException {
    if (task == null || date == null) {
      throw new IllegalArgumentException("task and date must be non-null");
    }

    if (!this.taskMap.containsKey(date) || !this.taskMap.get(date).contains(task)) {
      throw new IllegalArgumentException("date does not exist in the task map or task does"
          + "not exist at given date");
    } else {
      // mark task as complete

      // a task can have a description (String), status (Boolean, true if complete, false otherwise)
    }
  }

  @Override
  public void setTaskAsIncomplete(PlannerTask task, PlannerDate date) throws IllegalArgumentException {

  }
}
