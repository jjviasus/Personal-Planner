package view;

import controller.IPlannerController;
import controller.SimplePlannerController;
import controller.TaskActionListener;
import controller.TaskKeyListener;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import model.date.IDate;
import model.task.ITask;
import model.task.PlannerTask;

/**
 * Displays a visual view of a simple planner.
 */
public class SimplePlannerView extends JFrame implements IPlannerView  {
  IPlannerController controller;
  Font boldTitle  = new Font("Courier new",  Font.BOLD, 30);
  Font title  = new Font("Courier",  Font.PLAIN, 30);
  Font taskFont  = new Font("Courier new",  Font.PLAIN, 20);

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
    JLabel dateLabel = new JLabel(date.toString());
    dateLabel.setForeground(new Color(250,250,250));
    dateLabel.setFont(boldTitle);
    datePanel.add(dateLabel);
    datePanel.setLayout(new BoxLayout(datePanel, BoxLayout.Y_AXIS));
    datePanel.setAlignmentY(Component.CENTER_ALIGNMENT);
    datePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
    datePanel.setBorder(BorderFactory.createEtchedBorder());
    datePanel.setMaximumSize(new Dimension(500, 70));
    datePanel.setBackground(new Color(40,40,40));
    mainPanel.add(datePanel);

    // tasks page
    JPanel tasksPage = new JPanel();
    tasksPage.setLayout(new BoxLayout(tasksPage, BoxLayout.Y_AXIS));
    tasksPage.setAlignmentY(Component.LEFT_ALIGNMENT);
    tasksPage.setAlignmentX(Component.CENTER_ALIGNMENT);
    tasksPage.setBorder(BorderFactory.createEtchedBorder());
    tasksPage.setMaximumSize(new Dimension(500,500));
    tasksPage.setBackground(new Color(50,50,50));
    mainPanel.add(tasksPage);

    // "Tasks:" header and add task button
    JPanel headerAndAddTaskRow = new JPanel();
    headerAndAddTaskRow.setLayout(new BoxLayout(headerAndAddTaskRow, BoxLayout.X_AXIS));
    headerAndAddTaskRow.setMaximumSize(new Dimension(700,50)); // why does width 700 fix it? it should be 500
    headerAndAddTaskRow.setBackground(new Color(50,50,50));
    tasksPage.add(headerAndAddTaskRow);
    // "Tasks:" header
    JLabel tasksLabel = new JLabel("Tasks:");
    tasksLabel.setFont(title);
    tasksLabel.setForeground(new Color(250,250,250));
    headerAndAddTaskRow.add(tasksLabel);
    // Add task button
    JButton addTask = new JButton("Add task");
    addTask.setFont(taskFont);
    addTask.setBackground(new Color(60,150,60));
    addTask.setForeground(new Color(250,250,250));
    addTask.setOpaque(true);
    addTask.setBorderPainted(false);
    addTask.setActionCommand("add");
    addTask.addActionListener(new TaskActionListener(this.controller, new PlannerTask(""))); // this should trigger a new text field to be generated so that the user can add the task
    headerAndAddTaskRow.add(addTask);

    // task list
    JPanel taskList = new JPanel();
    taskList.setLayout(new BoxLayout(taskList, BoxLayout.Y_AXIS));
    taskList.setAlignmentX(Component.LEFT_ALIGNMENT);
    taskList.setBackground(new Color(60,60,60));
    tasksPage.add(taskList);
    // individual task
    for (ITask t : listOfTasks) {
      JPanel taskRow = new JPanel();
      taskRow.setLayout(new BoxLayout(taskRow, BoxLayout.X_AXIS));
      taskRow.setAlignmentX(Component.LEFT_ALIGNMENT);
      taskRow.setAlignmentY(Component.CENTER_ALIGNMENT);
      taskRow.setBackground(new Color(60,60,60));

      // complete check box
      JCheckBox checkBox = new JCheckBox();
      checkBox.setSelected(t.getStatus());
      checkBox.addActionListener(new TaskActionListener(controller, t));
      checkBox.setActionCommand("toggle");
      taskRow.add(checkBox);
      // task description
      JTextField taskText = new JTextField(t.getDescription());
      taskText.setMaximumSize(new Dimension(500, 35));
      taskText.setFont(taskFont);
      taskText.setBackground(new Color(60,60,60));
      taskText.setForeground(new Color(250,250,250));
      taskText.addKeyListener(new TaskKeyListener(controller, t, taskText));
      taskRow.add(taskText);
      // delete button
      JButton deleteButton = new JButton("Delete");
      deleteButton.addActionListener(new TaskActionListener(controller, t));
      deleteButton.setActionCommand("delete");
      deleteButton.setFont(taskFont);
      deleteButton.setBackground(new Color(100,60,60));
      deleteButton.setForeground(new Color(250,250,250));
      deleteButton.setOpaque(true);
      deleteButton.setBorderPainted(false);
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
    dateChanger.setAlignmentY(Component.CENTER_ALIGNMENT);
    dateChanger.setBorder(BorderFactory.createEtchedBorder());
    dateChanger.setBackground(new Color(40,40,40));
    dateChanger.setMaximumSize(new Dimension(500,120));
    // left button
    JButton leftButton = new JButton("Previous day");
    leftButton.addActionListener((SimplePlannerController) controller);
    leftButton.setActionCommand("left");
    leftButton.setMaximumSize(new Dimension(200,50));
    leftButton.setFont(taskFont);
    leftButton.setBackground(new Color(60,60,60));
    leftButton.setForeground(new Color(250,250,250));
    leftButton.setOpaque(true);
    leftButton.setBorderPainted(false);
    dateChanger.add(leftButton);
    // right button
    JButton rightButton = new JButton("Next day");
    rightButton.addActionListener((SimplePlannerController) controller);
    rightButton.setActionCommand("right");
    rightButton.setMaximumSize(new Dimension(200,50));
    rightButton.setFont(taskFont);
    rightButton.setBackground(new Color(60,60,60));
    rightButton.setForeground(new Color(250,250,250));
    rightButton.setOpaque(true);
    rightButton.setBorderPainted(false);
    dateChanger.add(rightButton);
    mainPanel.add(dateChanger);

    // current day button?

    //repaint();
    revalidate();
  }



}

// the size of the boxes might have to do with the alignment (the spacing between boxes)
