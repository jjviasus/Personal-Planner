package model.planner;

import java.util.List;
//TODO add exceptions that should be thrown for invalid arguments
/**
 * An interface that represents the model for a planner. The model stores, updates,
 * and provides access to the user's data. It is parameterized by task and date objects.
 * @param <T> the theme object that the model uses
 * @param <D> the date object that the model uses
 */
public interface IPlannerModel<T, D> {
  /**
   * Adds the given task to the model at the specified date.
   * @param task the provided task to be added
   * @param date the provided date the task should be added to
   */
  public void addTask(String task, D date);

  /**
   * Gets the model's list of tasks.
   * @return the list of tasks the model has stored
   */
  public List<String> getTasks();

  /**
   * Removes the specified task at the given date.
   * @param task the provided task to be removed
   * @param date the date the task is removed from
   */
  public void removeTask(String task, D date);

  /**
   * Moves the task to the desired date.
   * @param task the task to be moved
   * @param initialDate the date the task should be moved from
   * @param newDate the date the task should be moved to
   */
  public void moveTask(String task, D initialDate, D newDate);

  /**
   * Adds the given number of points to the model's point total.
   * @param points the points to be added as an integer
   */
  public void addPoints(int points);

  /**
   * Gets the total points of this model.
   * @return the total number of points as an integer
   */
  public int getTotalPoints();

  /**
   * Removes the given number of points from the model's point total.
   * @param points the points to be removed as an integer
   */
  public void removePoints(int points);

  /**
   * Returns the user's name as a String.
   */
  public String getUserName();

  /**
   * Sets the user's name as the given String.
   * @param name the desired name to be set for the user as a String
   */
  public void setUserName(String name);

  /**
   * Returns a list of themes the user has unlocked.
   */
  public List<T> getThemes();

  /**
   * Adds a given theme to the user's list of unlocked themes.
   * @param theme the new unlocked theme to be added to the list of themes for this model.
   */
  public void addTheme(T theme);

  /**
   * Sets the current theme to the given theme.
   * @param theme the desired theme the planner should be set to use
   */
  public void setTheme(T theme);


  // get completed tasks

  // set task as complete

  // get uncompleted tasks

  // set task as uncompleted
}
