package view;

import controller.IPlannerController;
import java.io.IOException;
import java.util.List;
import model.date.IDate;
import model.task.ITask;

/**
 * A visual view of a personal planner.
 */
public interface IPlannerView { // paramertize the date by Date not IDate

  /**
   * Renders the output of the personal planner.
   * @param date the date to display
   * @param listOfTasks the list of tasks to display
   *
   */
  public void render(IDate date, List<ITask> listOfTasks);

  /**
   * Updates the date label of the personal planner.
   * @param date the new date to display
   */
  public void updateDate(IDate date);

  /**
   * Updates the list of tasks of the personal planner.
   * @param listOfTasks the new list of tasks to display
   */
  public void updateTasks(List<ITask> listOfTasks);


}
