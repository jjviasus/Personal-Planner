package controller;

import model.planner.IPlannerModel;

/**
 * Provides a controller for interacting with a personal planner. The controller handles
 * the action events and input made by the user. The controller will fetch as well as instruct
 * the model to update its data. The controller will interpret the data from the model and
 * instruct the view to display its views accordingly.
 */
public interface IPlannerController<Theme, Date, Task> {
  /**
   * The primary method for creating a personal planner that a user can interact with.
   * @param model the particular planner model
   * @throws IllegalArgumentException if the given model is null
   */
  public <Theme, Date, Task> void usePlanner(IPlannerModel<Theme, Date, Task> model);

  /**
   * Decrements the date the user is currently viewing in the planner by 1 day.
   * The date and task page should be updated for the viewer.
   */
  public void decrementDate();

  /**
   * Increments the date the user is currently view in the planner by 1 day.
   * The date and task page should be updated for the viewer.
   */
  public void incrementDate();

  /**
   * Deletes the given task from the planner at the date the user is currently viewing.
   * @param task the task to delete
   */
  public void deleteTask(Task task);

  /**
   * Toggles the completion status of the given task
   * @param task the task to toggle
   */
  public void toggleTask(Task task);

  /**
   * Adds the given task to the planner at the date the user is currently viewing.
   * @param task the task to add
   */
  public void addTask(Task task);

  /**
   * Updates the given task's description to the new description
   * @param task the task to update
   * @param description the new description
   */
  public void updateDescription(Task task, String description);

}




