package view;

import controller.IPlannerController;
import controller.SimplePlannerController;
import controller.TaskActionListener;
import controller.TaskKeyListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
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
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import model.date.IDate;
import model.task.ITask;
import model.task.PlannerTask;

/**
 * Displays a visual view of a simple planner.
 */
public class SimplePlannerView extends JFrame implements IPlannerView  {
  JPanel headerAndAddTaskRow;
  IPlannerController controller;
  JLabel dateLabel;
  JPanel taskList;
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

    // edit the frame
    setMinimumSize(new Dimension(500,700));
    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //setResizable(false);

    // date panel
    JPanel datePanel = new JPanel();
    dateLabel = new JLabel(date.toString());
    dateLabel.setForeground(new Color(250,250,250));
    dateLabel.setFont(boldTitle);
    datePanel.add(dateLabel, BorderLayout.CENTER);
    //datePanel.setBorder(BorderFactory.createEtchedBorder());
    datePanel.setMaximumSize(new Dimension(500, 100));
    datePanel.setBackground(new Color(40,40,40));
    this.add(datePanel, BorderLayout.NORTH);
    datePanel.revalidate();
    datePanel.repaint();

    // tasks page
    JPanel tasksPage = new JPanel();
    tasksPage.setBackground(new Color(50,50,50));
    tasksPage.setLayout(new FlowLayout(FlowLayout.CENTER));
    this.add(tasksPage, BorderLayout.CENTER);
    tasksPage.revalidate();
    tasksPage.repaint();

    // "Tasks:" header and add task button
    headerAndAddTaskRow = new JPanel();
    headerAndAddTaskRow.setBackground(new Color(50,50,50));
    tasksPage.add(headerAndAddTaskRow, BorderLayout.NORTH);
    // "Tasks:" header and Add task button
    refreshAddTask();

    // task list
    taskList = new JPanel();
    taskList.setBackground(new Color(60,60,60)); // all 60's
    taskList.setLayout(new FlowLayout(FlowLayout.LEADING));
    taskList.setPreferredSize(new Dimension(500,525));
    tasksPage.add(taskList, BorderLayout.CENTER);
    updateTasks(listOfTasks);
/*    // scroll pane
    JScrollPane scrollPane = new JScrollPane(taskList);
    tasksPage.add(scrollPane);*/



    // left and right arrow panel
    JPanel dateChanger = new JPanel();
    //dateChanger.setBorder(BorderFactory.createEtchedBorder());
    dateChanger.setBackground(new Color(50,50,50));
    dateChanger.setMaximumSize(new Dimension(500,200));
    // left button
    JButton leftButton = new JButton("Previous day");
    leftButton.addActionListener((SimplePlannerController) controller);
    leftButton.setActionCommand("left");
    //leftButton.setMaximumSize(new Dimension(200,50));
    leftButton.setFont(taskFont);
    leftButton.setBackground(new Color(60,60,60));
    leftButton.setForeground(new Color(250,250,250));
    leftButton.setOpaque(true);
    leftButton.setBorderPainted(false);
    dateChanger.add(leftButton, BorderLayout.WEST);
    // right button
    JButton rightButton = new JButton("Next day");
    rightButton.addActionListener((SimplePlannerController) controller);
    rightButton.setActionCommand("right");
    //rightButton.setMaximumSize(new Dimension(200,50));
    rightButton.setFont(taskFont);
    rightButton.setBackground(new Color(60,60,60));
    rightButton.setForeground(new Color(250,250,250));
    rightButton.setOpaque(true);
    rightButton.setBorderPainted(false);
    dateChanger.add(rightButton, BorderLayout.EAST);
    this.add(dateChanger, BorderLayout.SOUTH);
    dateChanger.revalidate();
    dateChanger.repaint();

    // current day button?


  }

  // make an update date method that edits date label and repaints
  @Override
  public void updateDate(IDate date) {
    dateLabel.setText(date.toString());

    repaint();
    revalidate();
  }

  @Override
  public void updateTasks(List<ITask> listOfTasks) {
    taskList.removeAll();
    refreshAddTask();

    for (ITask t : listOfTasks) {
      JPanel taskRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
      taskRow.setPreferredSize(new Dimension(490,60));
      taskRow.setBackground(new Color(60,60,60));

      // complete check box
      JCheckBox checkBox = new JCheckBox();
      checkBox.setSelected(t.getStatus());
      checkBox.addActionListener(new TaskActionListener(controller, t));
      checkBox.setActionCommand("toggle");
      taskRow.add(checkBox);
      // task description
      JTextField taskText = new JTextField(t.getDescription());
      taskText.setPreferredSize(new Dimension(310, 50));
      taskText.setFont(taskFont);
      taskText.setBackground(new Color(65,65,65));
      taskText.setForeground(new Color(250,250,250));
      taskText.addKeyListener(new TaskKeyListener(controller, t, taskText));
      taskRow.add(taskText);
      taskRow.add(Box.createHorizontalStrut(5));
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



    repaint();
    revalidate();
  }

  /**
   * Destroys and creates a new add task button. This is necessary because
   * the action of clicking the button creates a new instance of a blank task.
   * Without this, the same instance of a task will be incorrectly passed around.
   */
  private void refreshAddTask() {
    headerAndAddTaskRow.removeAll();

    JLabel tasksLabel = new JLabel("Tasks:");
    tasksLabel.setFont(title);
    tasksLabel.setForeground(new Color(250,250,250));
    headerAndAddTaskRow.add(tasksLabel, BorderLayout.WEST);
    // spacer
    headerAndAddTaskRow.add(Box.createHorizontalStrut(210));

    JButton addTask = new JButton("Add task");
    addTask.setFont(taskFont);
    addTask.setBackground(new Color(60,150,60));
    addTask.setForeground(new Color(250,250,250));
    addTask.setOpaque(true);
    addTask.setBorderPainted(false);
    addTask.setActionCommand("add");
    addTask.addActionListener(new TaskActionListener(this.controller, new PlannerTask("")));
    headerAndAddTaskRow.add(addTask, BorderLayout.EAST);
  }

  // make an update task page

  // update task description?

}

// the size of the boxes might have to do with the alignment (the spacing between boxes)
