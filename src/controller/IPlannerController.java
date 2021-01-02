package controller;

import model.date.IDate;
import model.date.PlannerDate;
import model.planner.IPlannerModel;
import model.planner.SimplePlannerModel;

/**
 * Provides a controller for interacting with a personal planner. The controller handles
 * the action events and input made by the user. The controller will fetch as well as instruct
 * the model to update its data. The controller will interpret the data from the model and
 * instruct the view to display its views accordingly.
 */
public interface IPlannerController {
  /**
   * The primary method for creating a personal planner that a user can interact with.
   * @param model the particular planner model
   * @throws IllegalArgumentException if the given model is null
   */
  public <Theme, Date, Task> void createPlanner(IPlannerModel<Theme, Date, Task> model);

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
}


// What will happen in the view?
/*
* 1) add task button / text field
* 2) delete task button
* 3) mark task complete/incomplete button
* 4) display the date
* 5) left and right buttons to change dates (and refresh/fetch the tasks)
* 6) edit button?
 */


// What will happen in the controller?
/*
* instead of current date to display, increment, decrement methods in model. The controller could
* have a field current date to display and when left/right buttons are clicked we can increment/decrement
* accordingly to the field. And all we have to do is fetch the task data from the model at that
* updated date and instruct the view to display this updated date.


* 1) the add task button was clicked. The controller will then
* take user's input (task description) and send it to the model (by calling add task at date)
* 2) the delete task button was clicked. The controller will then
* tell the model to delete this task at the particular date. The model will send back the new list
* of tasks to display and the controller will pass this data onto the view to display it accordingly
* 3) the check box was checked and then it will tell the model
* to mark this task at the particular date as complete. Tell the view to display a checked box.
* If the check box was unchecked, then the controller will tell the model to mark this task at the
* particular date as incomplete. It will also tell the view to display an unchecked box
* 4) The controller will receive the data to display from the model. It will pass this information
* on to the view to display it accordingly
* 5) the left button was clicked, so tell the model to decrement the date to display by 1 day.
* Get the new date to display from the model and pass it onto the view. Likewise with right button
* exception increment the day.
* 6) The edit button was clicked and the user updated the task. Tell the model to update the task
* description at the current date. Instruct the view to display the new task.
*
 */