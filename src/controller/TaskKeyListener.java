package controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JTextField;
import model.task.ITask;

public class TaskKeyListener extends KeyAdapter {
  private IPlannerController controller;
  private ITask task;
  private JTextField textField;

  public TaskKeyListener(IPlannerController controller, ITask task, JTextField textField)
  {
    this.controller = controller;
    this.task = task;
    this.textField = textField;
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      try {
        controller.updateDescription(task, textField.getText());
        textField.setText("");
      } catch (IllegalArgumentException illegalArgumentException) {
        illegalArgumentException.printStackTrace();
      }
    }
  }
}
