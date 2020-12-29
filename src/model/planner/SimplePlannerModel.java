package model.planner;

import java.awt.Color;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import model.date.IDate;
import model.date.PlannerDate;
import model.task.ITask;
import model.task.PlannerTask;
import model.theme.ITheme;
import model.theme.PlannerTheme;

/**
 * An IPlannerModel implementation that represents a simple planner. It uses
 * the PlannerTheme class as the theme, PlannerTask class as the task, and the PlannerDate class as the
 * date. It keeps track of dates and tasks in a TreeMap. PlannerDate's are used
 * as keys and a list of Strings (as the tasks) are used as values.
 */
public class SimplePlannerModel implements IPlannerModel<ITheme, IDate, ITask> {
  private TreeMap<IDate, List<ITask>> taskMap;
  private int totalPoints;
  private String userName;
  private TreeMap<ITheme, Integer> themeMap; // the value is the cost of the theme
  private ITheme currentTheme;
  private LocalDateTime now = LocalDateTime.now();
  private int pointReward; // the number of points to reward a user for completing all their tasks
  // at a particular date



  /**
   * Initializes a new simple planner.
   */
  public SimplePlannerModel() {
    this.taskMap = new TreeMap<>();
    this.totalPoints = 0;
    this.userName = "Enter your name!";
    this.themeMap = new TreeMap<>();
    this.themeMap.put(new PlannerTheme("Dark", "Courier New",
        new Color(255,255,255), 12, new Color(30,30,30),
        new Color(50,50,50), new Color(75,75,75), 10), 10);
    this.themeMap.put(new PlannerTheme("Light", "Times New Roman",
        new Color(30,30,30), 12, new Color(250,250,250),
        new Color(200,200,200), new Color(150,150,150), 0), 0);
    this.currentTheme = this.themeMap.firstKey();
    this.pointReward = 10;
  }

  @Override
  public void addTask(ITask task, IDate date) throws IllegalArgumentException {
    if (task == null || date == null) {
      throw new IllegalArgumentException("task and date must be non-null");
    }
    // check if the date already exists
    if (this.taskMap.containsKey(date)) {
      // add the task to the list of tasks at the given date
      List<ITask> newValueList = new ArrayList<>(this.taskMap.get(date));
      newValueList.add(task);
      this.taskMap.put(date, newValueList);
    } else {
      // put the date as a new key in the tree map
      this.taskMap.put(date, Arrays.asList(task));
    }
  }

  @Override
  public List<ITask> getAllTasks() {
    // list of values
    List<List<ITask>> copyValues = new ArrayList();
    copyValues.addAll(taskMap.values());

    // flatten the list of list of strings
    List<ITask> newList = new ArrayList();
    for (List<ITask> los : copyValues) {
      newList.addAll(los);
    }

    // returns a copy of the tasks as one list
    // in the order of their date
    return newList;
  }

  @Override
  public void removeTask(ITask task, IDate date) throws IllegalArgumentException {
    if (task == null || date == null) {
      throw new IllegalArgumentException("task and date must be non-null");
    }
    // first check that the date exists, then check if the task exists
    if (!this.taskMap.containsKey(date)) {
      throw new IllegalArgumentException("date does not exist");
    } else {
      // check that the task exists
      if (this.taskMap.get(date).contains(task)) {
        List<ITask> newValueList = new ArrayList<>(this.taskMap.get(date));
        newValueList.remove(task);
        this.taskMap.put(date, newValueList);
      } else {
        throw new IllegalArgumentException("task does not exist");
      }
    }
  }

  @Override
  public void moveTask(ITask task, IDate initialDate, IDate newDate) throws
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
        List<ITask> newValueList = new ArrayList<>(this.taskMap.get(initialDate));
        newValueList.remove(task);
        this.taskMap.put(initialDate, newValueList);

        // check if the newDate already exists or not
        if (this.taskMap.containsKey(newDate)) {
          // add the task to the date key that already exists
          List<ITask> newValueList2 = new ArrayList<>(this.taskMap.get(newDate));
          newValueList2.add(task);
          this.taskMap.put(newDate, newValueList2);
        } else {
          // create the new date key and add the task
          this.taskMap.put(newDate, Arrays.asList(task));
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
  public List<ITheme> getAllThemes() {
    // return a copy of the list of all themes
    List copy = new ArrayList();
    copy.addAll(this.themeMap.keySet());
    return copy;
  }

  @Override
  public void setCurrentTheme(ITheme theme) throws IllegalArgumentException, IllegalStateException {
    if (theme == null || !this.themeMap.containsKey(theme)) {
      throw new IllegalArgumentException("theme must be non-null and must be a valid theme");
    }

    if (this.themeMap.get(theme) > this.totalPoints) {
      throw new IllegalStateException("theme has not been bought yet");
    }
    this.currentTheme=theme;
  }

  @Override
  public ITheme getCurrentTheme() {
    return this.currentTheme;
  }

  @Override
  public List<ITask> getTasksAtDate(IDate date) throws IllegalArgumentException {
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
  public void setTaskAsCompleted(ITask task, IDate date) throws IllegalArgumentException {
    if (task == null || date == null) {
      throw new IllegalArgumentException("task and date must be non-null");
    }

    if (!this.taskMap.containsKey(date) || !this.taskMap.get(date).contains(task)) {
      throw new IllegalArgumentException("date does not exist in the task map or task does"
          + "not exist at given date");
    } else {
      // mark task as complete
      int index = this.taskMap.get(date).indexOf(task);
      this.taskMap.get(date).get(index).markComplete();
    }

    // check if all the tasks are complete, if they are then update the points
    if (this.tasksCompleteAtDate(date)) {
      this.updatePoints(date);
    }
  }

  @Override
  public void setTaskAsIncomplete(ITask task, IDate date) throws IllegalArgumentException {
    if (task == null || date == null) {
      throw new IllegalArgumentException("task and date must be non-null");
    }

    if (!this.taskMap.containsKey(date) || !this.taskMap.get(date).contains(task)) {
      throw new IllegalArgumentException("date does not exist in the task map or task does"
          + "not exist at given date");
    } else {
      // mark task as incomplete
      int index = this.taskMap.get(date).indexOf(task);
      this.taskMap.get(date).get(index).markIncomplete();
    }

  }

  private void updatePoints(IDate date) {
    PlannerDate current = (PlannerDate) this.getCurrentDate();

    // since all tasks are complete, check if the given date has passed
    if (current.compareTo((PlannerDate) date) > 0) {
      // update the points
      this.totalPoints+=this.pointReward;
    }
  }

  @Override
  public void buyTheme(ITheme theme)
      throws IllegalArgumentException, IllegalStateException {
    if (theme == null || !this.themeMap.containsKey(theme)) {
      throw new IllegalArgumentException("theme must be non-null and must be a valid theme");
    }

    if (this.themeMap.get(theme) > this.totalPoints) {
      throw new IllegalStateException("insufficient funds to buy theme");
    }

    // set the theme price to zero
    this.themeMap.put(theme, 0);
  }

  @Override
  public boolean tasksCompleteAtDate(IDate date) throws IllegalArgumentException {
    if (date == null || !this.taskMap.containsKey(date)) {
      throw new IllegalArgumentException("date was null or contained no tasks");
    }

    for (ITask task : this.taskMap.get(date)) {
      if (!task.getStatus()) {
        return false;
      }
    }

    return true;
  }

  @Override
  public IDate getCurrentDate() {
    LocalDateTime now = LocalDateTime.now();
    int month = now.getMonthValue();
    int day = now.getDayOfMonth();
    int year = now.getYear();
    return new PlannerDate(month, day, year);
  }
}
