package model.task;

/**
 * An implementation of an IDatabaseTask to be used with a planner that is linked to a database.
 * This class allows one to get and set the task_id values of this task, which will be used as a primary
 * key in the database to uniquely identify tasks.
 */
public class DatabasePlannerTask extends PlannerTask implements IDatabaseTask {
  private int task_id;

  public DatabasePlannerTask(String description, boolean status) {
    super(description, status);
  }

  public DatabasePlannerTask(String description) {
    super(description);
  }

  @Override
  public int getId() {
    return task_id;
  }

  @Override
  public void setId(int task_id) {
    this.task_id = task_id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DatabasePlannerTask task = (DatabasePlannerTask) o;
    return description == task.description && status == task.status && task_id == task.task_id;
  }
}
