package view;

import controller.IPlannerController;
import controller.SimplePlannerController;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import model.date.IDate;
import model.task.ITask;
import model.task.PlannerTask;

/**
 * Displays a visual view of a simple planner.
 */
public class SimplePlannerView extends JFrame implements IPlannerView  {
  JLabel dateLabel;
  IPlannerController controller;

  /**
   * Constructs a simple planner view and takes an instance of a controller
   * to communicate with.
   * @param controller
   */
  public SimplePlannerView(IPlannerController controller) {
    this.controller = controller;
  }

  @Override
  public void render(IDate date, List<ITask> listOfTasks) {
    System.out.println(date);

    // main panel to be placed on the frame
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    mainPanel.setAlignmentY(Component.CENTER_ALIGNMENT);

    // edit the frame
    add(mainPanel);
    setResizable(true);
    setVisible(true);

    // date panel
    JPanel datePanel = new JPanel();
    this.dateLabel = new JLabel(date.toString());
    datePanel.add(dateLabel);
    datePanel.setAlignmentY(Component.CENTER_ALIGNMENT);
    datePanel.setBorder(BorderFactory.createEtchedBorder());
    mainPanel.add(datePanel);

    // tasks page
    JPanel tasksPage = new JPanel();
    tasksPage.setLayout(new BoxLayout(tasksPage, BoxLayout.Y_AXIS));
    tasksPage.setAlignmentY(Component.CENTER_ALIGNMENT);
    tasksPage.setBorder(BorderFactory.createEtchedBorder());
    mainPanel.add(tasksPage);
    // tasks header
    JPanel tasksHeader = new JPanel();
    tasksHeader.setLayout(new BoxLayout(tasksHeader, BoxLayout.X_AXIS));
    tasksHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
    JLabel tasksLabel = new JLabel("Tasks");
    tasksHeader.add(tasksLabel);
    tasksPage.add(tasksHeader);
    // task list
    JPanel taskList = new JPanel();
    taskList.setLayout(new BoxLayout(taskList, BoxLayout.Y_AXIS));
    taskList.setAlignmentY(Component.LEFT_ALIGNMENT);
    tasksPage.add(taskList);
    // individual task
    for (ITask t : listOfTasks) {
      JPanel taskRow = new JPanel();
      taskRow.setLayout(new BoxLayout(taskRow, BoxLayout.X_AXIS));
      taskRow.setAlignmentY(Component.CENTER_ALIGNMENT);
      JLabel taskDescription = new JLabel(t.getDescription());
      taskRow.add(taskDescription);
      taskList.add(taskRow);
    }

    // scroll pane
   /* JScrollPane scrollPane = new JScrollPane(taskRow);
    taskList.add(scrollPane);*/

    // completion button
    // edit button
    // delete button

    // left and right arrow panel
    JPanel dateChanger = new JPanel();
    mainPanel.add(dateChanger);
    dateChanger.setLayout(new BoxLayout(dateChanger, BoxLayout.X_AXIS));
    dateChanger.setAlignmentX(Component.CENTER_ALIGNMENT);
    dateChanger.setBorder(BorderFactory.createEtchedBorder());
    // left button
    JButton leftButton = new JButton("Left");
    leftButton.addActionListener((SimplePlannerController) controller);
    leftButton.setActionCommand("left");
    dateChanger.add(leftButton);
    // right button
    JButton rightButton = new JButton("Right");
    rightButton.addActionListener((SimplePlannerController) controller);
    rightButton.setActionCommand("right");
    dateChanger.add(rightButton);
  }

  // should the above method take a date and list of tasks?

  // should the controller be calling render everytime a change is made? Check pyramid solitaire

}
