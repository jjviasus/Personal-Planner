package view;

import controller.IPlannerController;
import controller.SimplePlannerController;
import controller.TaskActionListener;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
  Font boldTitle  = new Font(Font.SANS_SERIF,  Font.BOLD, 16);
  Font taskFont  = new Font(Font.SANS_SERIF,  Font.PLAIN, 12);

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

    // main panel to be placed on the frame
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    mainPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
    mainPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

    // edit the frame
    add(mainPanel);
    setResizable(false);
    setSize(500,700);
    setVisible(true);
    setContentPane(mainPanel);

    // date panel
    JPanel datePanel = new JPanel();
    this.dateLabel = new JLabel(date.toString());
    this.dateLabel.setFont(boldTitle);
    datePanel.add(dateLabel);
    BoxLayout layout = new BoxLayout(datePanel, BoxLayout.Y_AXIS);
    datePanel.setLayout(new BoxLayout(datePanel, BoxLayout.Y_AXIS));
    datePanel.setAlignmentY(Component.CENTER_ALIGNMENT);
    datePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
    datePanel.setBorder(BorderFactory.createEtchedBorder());
    datePanel.setPreferredSize(new Dimension(500, 100));
    mainPanel.add(datePanel);

    // tasks page
    JPanel tasksPage = new JPanel();
    tasksPage.setLayout(new BoxLayout(tasksPage, BoxLayout.Y_AXIS));
    tasksPage.setAlignmentY(Component.LEFT_ALIGNMENT);
    tasksPage.setAlignmentX(Component.CENTER_ALIGNMENT);
    tasksPage.setBorder(BorderFactory.createEtchedBorder());
    tasksPage.setSize(500,500);
    mainPanel.add(tasksPage);
    // tasks header
    JPanel tasksHeader = new JPanel();
    tasksHeader.setLayout(new BoxLayout(tasksHeader, BoxLayout.X_AXIS));
    tasksHeader.setAlignmentX(Component.LEFT_ALIGNMENT);
    JLabel tasksLabel = new JLabel("Tasks:");
    tasksLabel.setFont(boldTitle);
    tasksHeader.add(tasksLabel);
    tasksPage.add(tasksHeader);
    // task list
    JPanel taskList = new JPanel();
    taskList.setLayout(new BoxLayout(taskList, BoxLayout.Y_AXIS));
    taskList.setAlignmentX(Component.LEFT_ALIGNMENT);
    tasksPage.add(taskList);
    // individual task
    for (ITask t : listOfTasks) {
      JPanel taskRow = new JPanel();
      taskRow.setLayout(new BoxLayout(taskRow, BoxLayout.X_AXIS));
      taskRow.setAlignmentX(Component.LEFT_ALIGNMENT);
      taskRow.setAlignmentY(Component.CENTER_ALIGNMENT);

      // complete check box
      JCheckBox checkBox = new JCheckBox();
      checkBox.setSelected(t.getStatus());
      checkBox.addActionListener(new TaskActionListener(controller, t));
      checkBox.setActionCommand("toggle");
      taskRow.add(checkBox);
      System.out.println(t.getStatus());
      // task description
      JLabel taskDescription = new JLabel(t.getDescription());
      taskDescription.setFont(taskFont);
      taskRow.add(taskDescription);
      // edit button
      JButton editButton = new JButton("Edit");
      editButton.addActionListener(new TaskActionListener(controller, t)); // give it the update description
      editButton.setActionCommand("edit");
      taskRow.add(editButton);
      // delete button
      JButton deleteButton = new JButton("Delete");
      deleteButton.addActionListener(new TaskActionListener(controller, t));
      deleteButton.setActionCommand("delete");
      taskRow.add(deleteButton);

      taskList.add(taskRow);
    }

    // scroll pane
   /* JScrollPane scrollPane = new JScrollPane(taskRow);
    taskList.add(scrollPane);*/

    // left and right arrow panel
    JPanel dateChanger = new JPanel();
    dateChanger.setLayout(new BoxLayout(dateChanger, BoxLayout.X_AXIS));
    dateChanger.setAlignmentX(Component.CENTER_ALIGNMENT);
    dateChanger.setBorder(BorderFactory.createEtchedBorder());
    dateChanger.setSize(500,100);
    // left button
    JButton leftButton = new JButton("- 1 day");
    leftButton.addActionListener((SimplePlannerController) controller);
    leftButton.setActionCommand("left");
    dateChanger.add(leftButton);
    // right button
    JButton rightButton = new JButton("+ 1 day");
    rightButton.addActionListener((SimplePlannerController) controller);
    rightButton.setActionCommand("right");
    dateChanger.add(rightButton);
    mainPanel.add(dateChanger);

    //repaint();
    revalidate();
  }



}

// the size of the boxes might have to do with the alignment (the spacing between boxes)
