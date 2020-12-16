package model.task;
//TODO hashCode and equals
/**
 * An implementation of an ITask to be used for a SimplePlannerModel.
 * It has a description and completion status.
 */
public class PlannerTask implements ITask {
  private String description;
  private boolean status;

  public PlannerTask(String description) {
    this.description = description;
    this.status = false;
  }

  @Override
  public String getDescription() {
    return this.description;
  }

  @Override
  public boolean getStatus() {
    return this.status;
  }

  @Override
  public void markComplete() {
    this.status = true;
  }

  @Override
  public void markIncomplete() {
    this.status = false;
  }

  @Override
  public void updateDescription(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return this.description + " " + (this.status ? "(complete)" : "(incomplete)");
  }

  @Override
  public boolean equals(Object o) {
    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }
}
