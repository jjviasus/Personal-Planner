package controller.database;

import controller.IPlannerController;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;
import model.task.IDatabaseTask;

public class DatabaseTaskKeyListener extends KeyAdapter {
  private IPlannerController controller;
  private IDatabaseTask task;
  private JTextField textField;

  public DatabaseTaskKeyListener(IPlannerController controller, IDatabaseTask task, JTextField textField)
  {
    this.controller = controller;
    this.task = task;
    this.textField = textField;
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      controller.updateDescription(task, textField.getText());
    }
  }
}
