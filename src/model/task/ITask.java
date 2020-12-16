package model.task;

/**
 * An interface to represent a task. A task can have a description
 * and status (complete / incomplete). It can be marked as complete or incomplete.
 */
public interface ITask {
  /**
   * Gets this task's description.
   * @returns the task description as a String
   */
  public String getDescription();

  /**
   * Gets this task's status.
   * @returns true if the task is complete or false if it is incomplete
   */
  public boolean getStatus();

  /**
   * Marks this task's status as complete.
   */
  public void markComplete();

  /**
   * Marks this task's status as incomplete.
   */
  public void markIncomplete();

  /**
   * Updates this task's description.
   * @param description the new description
   */
  public void updateDescription(String description);
}
