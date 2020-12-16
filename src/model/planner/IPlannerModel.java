package model.planner;

import java.util.List;

/**
 * An interface that represents the model for a planner. The model stores, updates,
 * and provides access to the user's data. It is parameterized by task and date objects.
 * @param <Theme> the theme object that the model uses
 * @param <Date> the date object that the model uses
 * @param <Task> the task object that the model uses
 */
public interface IPlannerModel<Theme, Date, Task> {
  /**
   * Adds the given task to the model at the specified date.
   * @param task the provided task to be added
   * @param date the provided date the task should be added to
   * @throws IllegalArgumentException thrown if the given task or date are null
   */
  public void addTask(Task task, Date date) throws IllegalArgumentException;

  /**
   * Gets a list of all the model's tasks.
   * @return the list of tasks the model has stored
   */
  public List<Task> getAllTasks();

  /**
   * Removes the specified task at the given date.
   * @param task the provided task to be removed
   * @param date the date the task is removed from
   * @throws IllegalArgumentException thrown if the given task or date are null
   */
  public void removeTask(Task task, Date date) throws IllegalArgumentException;

  /**
   * Moves the task to the desired date.
   * @param task the task to be moved
   * @param initialDate the date the task should be moved from
   * @param newDate the date the task should be moved to
   * @throws IllegalArgumentException thrown if the given task, initial date, or new date are null
   */
  public void moveTask(Task task, Date initialDate, Date newDate) throws IllegalArgumentException;

  /**
   * Gets the total points of this model.
   * @return the total number of points as an integer
   */
  public int getTotalPoints();


  /**
   * Returns the user's name as a String.
   */
  public String getUserName();

  /**
   * Sets the user's name as the given String.
   * @param name the desired name to be set for the user as a String
   * @throws IllegalArgumentException thrown if the given name is null
   */
  public void setUserName(String name) throws IllegalArgumentException;

  /**
   * Returns a list of all themes.
   * @return the list of all themes
   */
  public List<Theme> getAllThemes();

  /**
   * Sets the current theme to the given theme.
   * @param theme the desired theme the planner should be set to use
   * @throws IllegalArgumentException thrown if the given theme is null
   * @throws IllegalStateException thrown if the theme has not been bought yet
   */
  public void setCurrentTheme(Theme theme) throws IllegalArgumentException, IllegalStateException;

  /**
   * Gets the current theme of the model.
   * @returns the current theme of the model
   */
  public Theme getCurrentTheme();

  /**
   * Gets the list of tasks in a given day.
   * @param date the given day
   * @return the list of tasks at the given date
   */
  public List<Task> getTasksAtDate(Date date) throws IllegalArgumentException;

  /**
   * Sets the given task at the given date as completed
   * @param task the task to be marked as completed
   * @param date the date where the task exists
   * @throws IllegalArgumentException thrown if the given task is null, date is null,
   * or if the task does not exist at the given date
   */
  public void setTaskAsCompleted(Task task, Date date) throws IllegalArgumentException;

  /**
   * Sets the given task at the given date as incomplete
   * @param task the task to be marked as incomplete
   * @param date the date where the task exists
   * @throws IllegalArgumentException thrown if the given task is null, date is null,
   * or if the task does not exist at the given date
   */
  public void setTaskAsIncomplete(Task task, Date date) throws IllegalArgumentException;

  /**
   * Buys the given theme and deducts points from the model.
   * @param theme the theme to buy
   * @throws IllegalArgumentException if the theme is null or invalid
   * @throws IllegalStateException if the cost of the theme is greater than the
   * total points in the model
   */
  public void buyTheme(Theme theme) throws IllegalArgumentException, IllegalStateException;

  /**
   * Returns true if all tasks at given date are complete.
   * (Should I be adding points to the total points if the day is over? How will the model
   * know the day is over?)
   * @param date the given date to check
   * @returns true if all tasks are complete, false otherwise
   * @throws IllegalArgumentException thrown if the given date is null or contains no tasks
   */
  public boolean tasksCompleteAtDate(Date date) throws IllegalArgumentException;

  // get completed tasks

  // get uncompleted tasks

  // should there be a method that checks what day it is, and if a given date has passed containing
  // tasks, it checks if they are all complete and then awards the user points?

}
