package model.task;
//TODO hashCode and equals

import model.date.PlannerDate;

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
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PlannerTask task = (PlannerTask) o;
    return description == task.description && status == task.status;
  }

  @Override
  public int hashCode() {
    return description.length() * 100000 + (status ? 1 : 1000);
  }
}
