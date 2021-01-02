package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import model.date.IDate;
import model.date.PlannerDate;
import model.planner.IPlannerModel;
import model.task.ITask;
import view.IPlannerView;
import view.SimplePlannerView;

public class SimplePlannerController implements IPlannerController, ActionListener {
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
    this.dateToDisplay.subtractDay();
    view.render(this.dateToDisplay, model.getTasksAtDate(dateToDisplay));
  }

  @Override
  public void incrementDate() {
    this.dateToDisplay.addDay();
    view.render(this.dateToDisplay, model.getTasksAtDate(dateToDisplay));
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
      case "delete":
        //this.deleteTask();

      default:
        throw new IllegalArgumentException("Illegal action performed");

    }
  }
}
