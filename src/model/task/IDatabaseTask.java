package model.task;

/**
 * An extension of an ITask to be used for a planner that is linked to a database. Offers additional
 * capabilities such as getting and setting the id of a task.
 */
public interface IDatabaseTask extends ITask {

  /**
   * Gets the id of the task.
   * @return the id of the task as an integer
   */
  public int getId();

  /**
   * Sets the id of the task.
   * @param id the id to be set
   */
  public void setId(int id);
}
