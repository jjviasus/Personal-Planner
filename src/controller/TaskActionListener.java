package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.task.ITask;

/**
 * This class allows the view to communicate which task should be manipulated and how with the
 * controller.
 */
public class TaskActionListener implements ActionListener {

  IPlannerController controller;
  ITask task;

  public TaskActionListener(IPlannerController controller, ITask task) {
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
