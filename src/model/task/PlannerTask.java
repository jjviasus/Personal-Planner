package model.task;
//TODO mark this identifiable

import java.util.concurrent.atomic.AtomicLong;
import model.date.PlannerDate;

/**
 * An implementation of an ITask to be used for a SimplePlannerModel.
 * It has a description and completion status.
 */
public class PlannerTask implements ITask {
  static final AtomicLong NEXT_ID = new AtomicLong(0);
  final long id = NEXT_ID.getAndIncrement();
  private String description;
  private boolean status;

  public PlannerTask(String description) {
    this.description = description;
    this.status = false;
  }

  public PlannerTask(String description, boolean status) {
    this.description = description;
    this.status = status;
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
    return description == task.description && status == task.status && id == task.id;
  }

  @Override
  public int hashCode() {
    return description.length() * 100000 + (status ? 1 : 1000);
  }

  public long getId() {
    return id;
  }
}
