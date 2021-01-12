package controller.database;

import controller.IPlannerController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.task.DatabasePlannerTask;
import model.task.IDatabaseTask;

public class DatabaseTaskActionListener implements ActionListener {

  IPlannerController controller;
  IDatabaseTask task;

  public DatabaseTaskActionListener(IPlannerController controller) {
    this.controller = controller;
    this.task = new DatabasePlannerTask("", false);
  }

  public DatabaseTaskActionListener(IPlannerController controller, IDatabaseTask task) {
    this.controller = controller;
    this.task = task;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "delete":
        controller.deleteTask(task);
        break;
      case "toggle":
        controller.toggleTask(task);
        break;
      case "add":
        controller.addTask(task);
        break;
      default:
        throw new IllegalArgumentException("Illegal action performed");

    }
  }
}
