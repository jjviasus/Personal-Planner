package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.date.IDate;
import model.date.PlannerDate;
import model.planner.IPlannerModel;
import model.task.ITask;
import model.theme.ITheme;
import view.IPlannerView;
import view.SimplePlannerView;

public class SimplePlannerController implements IPlannerController<ITheme, IDate, ITask>, ActionListener {
  private IPlannerModel model;
  private IPlannerView view;
  private IDate dateToDisplay;

  @Override
  public <Theme, Date, Task> void createPlanner(IPlannerModel<Theme, Date, Task> model) throws IllegalArgumentException {
    if (model == null) throw new IllegalArgumentException("null model given");

    this.model = model;
    this.dateToDisplay = (PlannerDate) this.model.getCurrentDate();
    this.view = new SimplePlannerView(this);
    view.render(dateToDisplay, this.model.getTasksAtDate(dateToDisplay));
  }

  @Override
  public void decrementDate() {
    PlannerDate prevDate = new PlannerDate(this.dateToDisplay.getMonth(), this.dateToDisplay.getDay(), this.dateToDisplay.getYear());
    prevDate.subtractDay();
    dateToDisplay = prevDate;
    view.updateDate(prevDate);
    view.updateTasks(model.getTasksAtDate(prevDate));
  }

  @Override
  public void incrementDate() {
    PlannerDate nextDate = new PlannerDate(this.dateToDisplay.getMonth(), this.dateToDisplay.getDay(), this.dateToDisplay.getYear());
    nextDate.addDay();
    dateToDisplay = nextDate;
    view.updateDate(nextDate);
    view.updateTasks(model.getTasksAtDate(nextDate));
  }

  @Override
  public void deleteTask(ITask task) {
    model.removeTask(task, this.dateToDisplay);
    view.updateTasks(model.getTasksAtDate(dateToDisplay));
  }

  @Override
  public void toggleTask(ITask task) {
    // toggle the tasks status
    if (task.getStatus()) {
      model.setTaskAsIncomplete(task, this.dateToDisplay);
    } else {
      model.setTaskAsCompleted(task, this.dateToDisplay);
    }

    view.updateTasks(model.getTasksAtDate(this.dateToDisplay));
  }

  @Override
  public void addTask(ITask task) {
    model.addTask(task, this.dateToDisplay);
    view.updateTasks(model.getTasksAtDate(this.dateToDisplay));
  }

  @Override
  public void updateDescription(ITask task, String description) {
    model.updateTaskDescription(task, this.dateToDisplay, description);
    view.updateTasks(model.getTasksAtDate(this.dateToDisplay));
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "left":
        this.decrementDate();
        break;
      case "right":
        this.incrementDate();
        break;
      default:
        throw new IllegalArgumentException("Illegal action performed");
    }
  }
}
