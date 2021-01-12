package model.planner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.date.IDate;
import model.date.PlannerDate;
import model.task.DatabasePlannerTask;
import model.task.IDatabaseTask;
import model.theme.ITheme;

public class DatabasePlannerModel implements IPlannerModel<ITheme, IDate, IDatabaseTask> {
  String url = "jdbc:mysql://localhost:3306/Planner";
  String user = "planner";
  String password = "planner"; // mult store it on a volt
  // store them as environment variables

  Connection myConn = null;
  PreparedStatement myStmt = null;
  ResultSet myRs = null;

  public DatabasePlannerModel() {
    try {
      // Get a connection to the data base
      myConn = DriverManager.getConnection(url, user, password);

    } catch (Exception exc) {
      exc.printStackTrace();
    }
  }

  @Override
  public void addTask(IDatabaseTask task, IDate date) throws IllegalArgumentException {
    if (task == null || date == null) {
      throw new IllegalArgumentException("task and date must be non-null");
    }

    // convert iDate to YYYY-MM-DD
    String dateFormatted = reformatDate(date);
    String taskDescription = task.getDescription();
    boolean taskStatus = task.getStatus();
    int taskId = task.getId();

    try {
      // check if the date exists already
      myStmt = myConn.prepareStatement("SELECT date FROM Dates WHERE date = ?");
      myStmt.setString(1, dateFormatted);
      myRs = myStmt.executeQuery();

      // insert the date into the dates table if it does not already exist
      if (!myRs.next()) {
        myStmt = myConn.prepareStatement("INSERT INTO Dates (date) VALUES (?)");
        myStmt.setString(1, dateFormatted);
        myStmt.executeUpdate();
      }

      // adds a new task to the Tasks table
      myStmt = myConn.prepareStatement("INSERT INTO Tasks (task_id, description, status, date) "
          + "VALUES (?, ?, ?, ?)");

      myStmt.setInt(1, taskId);
      myStmt.setString(2, taskDescription);
      myStmt.setBoolean(3, taskStatus);
      myStmt.setString(4, dateFormatted);

      // execute the data manipulation
      myStmt.executeUpdate();

      // get the latest task_id that was auto incremented
      myStmt = myConn.prepareStatement("SELECT task_id FROM Tasks WHERE task_id = LAST_INSERT_ID()");
      myRs = myStmt.executeQuery();

      int task_id = 0;
      while (myRs.next()) {
        task_id = myRs.getInt("task_id");
      }

      // set the task id that was given to us
      task.setId(task_id);

    } catch (Exception exc) {
      exc.printStackTrace();
    }
  }

  @Override
  public void removeTask(IDatabaseTask task, IDate date) throws IllegalArgumentException {
    if (task == null || date == null) {
      throw new IllegalArgumentException("task and date must be non-null");
    }

    // convert iDate to YYYY-MM-DD
    String dateFormatted = reformatDate(date);
    int taskId = task.getId();

    try {

      // delete the task
      myStmt = myConn.prepareStatement("DELETE FROM Tasks "
          + "WHERE task_id = ?");

      myStmt.setInt(1, taskId);

      // execute the data manipulation
      myStmt.executeUpdate();

      // delete the date if there are no tasks
      myStmt = myConn.prepareStatement("SELECT task_id FROM Tasks WHERE date = ?");

      myStmt.setString(1, dateFormatted);

      // execute the query
      myRs = myStmt.executeQuery();

      // if no task_id's were selected, delete the date
      if (!myRs.next()) {
        // delete the date
        PreparedStatement deleteDate = myConn.prepareStatement("DELETE FROM Dates WHERE date = ?");

        deleteDate.setString(1, dateFormatted);

        // execute the data manipulation
        deleteDate.executeUpdate();
      }


    } catch (Exception exc) {
      exc.printStackTrace();
    }
  }

  @Override
  public List<IDatabaseTask> getTasksAtDate(IDate date) throws IllegalArgumentException {
    if (date == null) {
      throw new IllegalArgumentException("the date must be non-null");
    }

    // convert iDate to YYYY-MM-DD
    String dateFormatted = reformatDate(date);

    // add each task to the tasksAtDate list
    List<IDatabaseTask> tasksAtDate = new ArrayList<>();

    try {
      // select all task_id's from Tasks table that match the given date
      myStmt = myConn.prepareStatement("SELECT task_id "
          + "FROM Tasks "
          + "WHERE date = ?");

      myStmt.setString(1, dateFormatted);

      // execute the query
      myRs = myStmt.executeQuery();

      while (myRs.next()) {
        int task_id;
        task_id = myRs.getInt("task_id");

        // get the description and status of the task with the given task_id
        PreparedStatement taskStmt = myConn.prepareStatement("SELECT description, status "
            + "FROM Tasks "
            + "WHERE task_id = ?");

        taskStmt.setInt(1, task_id);
        ResultSet taskResult = taskStmt.executeQuery();

        // create a new planner task with the selected values and insert it into the new list of tasks
        while (taskResult.next()) {
          IDatabaseTask newTask = new DatabasePlannerTask(taskResult.getString("description"), taskResult.getBoolean("status")); // needs an initial value
          // set the id
          newTask.setId(task_id);
          tasksAtDate.add(newTask);
        }
      }

    } catch (Exception exc) {
      exc.printStackTrace();
    }

    // return a copy?
    return tasksAtDate;
  }

  @Override
  public void setTaskAsCompleted(IDatabaseTask task, IDate date)
      throws IllegalArgumentException, IllegalStateException {
    if (task == null || date == null) {
      throw new IllegalArgumentException("task and date must be non-null");
    }

    // convert iDate to YYYY-MM-DD
    String dateFormatted = reformatDate(date);
    int taskId = task.getId();

    try {
      // insert true into the task's status column
      myStmt = myConn.prepareStatement("UPDATE Tasks SET status = ? WHERE task_id = ?");
      myStmt.setBoolean(1, true);
      myStmt.setInt(2, taskId);
      myStmt.executeUpdate();

      // don't know if this is necessary !!!
      task.markComplete();

    } catch (Exception exc) {
      exc.printStackTrace();
    }

  }

  @Override
  public void setTaskAsIncomplete(IDatabaseTask task, IDate date)
      throws IllegalArgumentException, IllegalStateException {
    if (task == null || date == null) {
      throw new IllegalArgumentException("task and date must be non-null");
    }

    // convert iDate to YYYY-MM-DD
    String dateFormatted = reformatDate(date);
    int taskId = task.getId();

    try {
      // insert true into the task's status column
      myStmt = myConn.prepareStatement("UPDATE Tasks SET status = ? WHERE task_id = ?");
      myStmt.setBoolean(1, false);
      myStmt.setInt(2, taskId);
      myStmt.executeUpdate();

      // don't know if this is necessary !!!
      task.markIncomplete();

    } catch (Exception exc) {
      exc.printStackTrace();
    }
  }

  @Override
  public void updateTaskDescription(IDatabaseTask task, IDate date, String description)
      throws IllegalArgumentException, IllegalStateException {
    if (task == null || date == null || description == null) {
      throw new IllegalArgumentException("task, date, and description must be non-null");
    }

    // convert iDate to YYYY-MM-DD
    int taskId = task.getId();
    String taskDescription = description;

    try {
      // insert new description into the task's description column
      myStmt = myConn.prepareStatement("UPDATE Tasks SET description = ? WHERE task_id = ?");
      myStmt.setString(1, taskDescription);
      myStmt.setInt(2, taskId);
      myStmt.executeUpdate();

      // don't know if this is necessary !!!
      task.updateDescription(description);

    } catch (Exception exc) {
      exc.printStackTrace();
    }
  }

  // takes an IDate as an argument and returns a newly formatted date as a String in the form
  // of YYYY-MM-DD
  private String reformatDate(IDate date) throws IllegalArgumentException {
    if (date == null) {
      throw new IllegalArgumentException("date must be non-null");
    }

    int year = date.getYear();
    int month = date.getMonth();
    int day = date.getDay();

    // default values
    String yearStr = Integer.toString(year); // is my date implementation allowed to be greater than 9999?
    String monthStr = Integer.toString(month);
    String dayStr = Integer.toString(day);

    if (year < 10) {
      yearStr = "000" + Integer.toString(year);
    } else if (year < 100) {
      yearStr = "00" + Integer.toString(year);
    } else if (year < 1000) {
      yearStr = "0" + Integer.toString(year);
    }

    if (month < 10) {
      monthStr = "0" + Integer.toString(month);
    }

    if (day < 10) {
      dayStr = "0" + Integer.toString(day);
    }

    return yearStr + "-" + monthStr + "-" + dayStr;
  }

  @Override
  public IDate getCurrentDate() {
    LocalDateTime now = LocalDateTime.now();
    int month = now.getMonthValue();
    int day = now.getDayOfMonth();
    int year = now.getYear();
    return new PlannerDate(month, day, year);
  }

  // Unused methods ----------------------------------------------------------------------------------

  @Override
  public boolean tasksCompleteAtDate(IDate iDate) throws IllegalArgumentException {
    return false; // needs db
  }

  @Override
  public void buyTheme(ITheme iTheme) throws IllegalArgumentException, IllegalStateException {
// doesn't need db
  }

  @Override
  public List<ITheme> getAllThemes() {
    return null; // doesn't need db
  }

  @Override
  public void setCurrentTheme(ITheme iTheme)
      throws IllegalArgumentException, IllegalStateException {
    // doesn't need db
  }

  @Override
  public ITheme getCurrentTheme() {
    return null;
    // needs db
  }

  @Override
  public void moveTask(IDatabaseTask iTask, IDate initialDate, IDate newDate)
      throws IllegalArgumentException {
    // needs db
  }

  @Override
  public int getTotalPoints() {
    return 0; // needs db
  }

  @Override
  public String getUserName() {
    return null; // needs db
  }

  @Override
  public void setUserName(String name) throws IllegalArgumentException {
    // needs db
  }

  @Override
  public List<IDatabaseTask> getAllTasks() {
    return null; // needs db
  }
}
