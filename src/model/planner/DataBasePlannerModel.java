package model.planner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.date.IDate;
import model.task.ITask;
import model.task.PlannerTask;
import model.theme.ITheme;
import java.sql.*;

public class DataBasePlannerModel extends SimplePlannerModel { // don't need to rewrite getCurrentDate
  String url = "jdbc:mysql://localhost:3306/Planner";
  String user = "planner";
  String password = "planner"; // mult store it on a volt
  // store them as environment variables

  // might have to have the java PlannerTask implementation generate a unique int everytime
  // and we manually pass that into task_id in the db so we know exactly what task to remove from what date
  // Might have to also get rid of auto increment in the db

  Connection myConn = null;
  PreparedStatement myStmt = null;
  ResultSet myRs = null;

  public DataBasePlannerModel() {
    try {
      // Get a connection to the data base
      myConn = DriverManager.getConnection(url, user, password);

    } catch (Exception exc) {
      exc.printStackTrace();
    }
  }

  @Override
  public void addTask(ITask task, IDate date) throws IllegalArgumentException {
    if (task == null || date == null) {
      throw new IllegalArgumentException("task and date must be non-null");
    }

    // convert iDate to YYYY:MM:DD
    String dateFormatted = reformatDate(date);
    String taskDescription = task.getDescription();
    boolean taskStatus = task.getStatus();

    try {
      // adds a new task to the Tasks table
      myStmt = myConn.prepareStatement("INSERT INTO Tasks (description, status) "
          + "VALUES (?, ?)");

      myStmt.setString(1, taskDescription);
      myStmt.setBoolean(2, taskStatus);

      // execute the data manipulation
      myStmt.executeUpdate();

      // adds a new date with reference to the new task_id that was generated by the previous insert
      myStmt = myConn.prepareStatement("INSERT INTO Dates (date, task_id)"
          + "VALUES (?, LAST_INSERT_ID())");

      myStmt.setString(1, dateFormatted);

      // execute the data manipulation
      myStmt.executeUpdate();

    } catch (Exception exc) {
      exc.printStackTrace();
    }
  }

  @Override
  public void removeTask(ITask task, IDate date) throws IllegalArgumentException {
    if (task == null || date == null) {
      throw new IllegalArgumentException("task and date must be non-null");
    }

    // convert iDate to YYYY:MM:DD
    String dateFormatted = reformatDate(date);
    String taskDescription = task.getDescription();
    boolean taskStatus = task.getStatus();

    try {
      // find the task_id from the Tasks table by doing WHERE description =, status =

      myStmt = myConn.prepareStatement("SELECT task_id "
          + "FROM Tasks "
          + "WHERE description = ? AND status = ? LIMIT 1");

      myStmt.setString(1, taskDescription);
      myStmt.setBoolean(2, taskStatus);

      // execute the query
      myRs = myStmt.executeQuery();

      int task_id = 1;
      if  (myRs.next()) {
        task_id = myRs.getInt(1);
      }

      // use that task_id to find the date that contains that task_id, then delete both the date and task
      myStmt = myConn.prepareStatement("DELETE FROM Dates "
          + "WHERE task_id = ? AND date = ?");

      myStmt.setInt(1, task_id);
      myStmt.setString(2, dateFormatted);

      // execute the data manipulation
      myStmt.executeUpdate();

      // delete the task
      myStmt = myConn.prepareStatement("DELETE FROM Tasks "
          + "WHERE task_id = ?");

      myStmt.setInt(1, task_id);

      // execute the data manipulation
      myStmt.executeUpdate();

    } catch (Exception exc) {
      exc.printStackTrace();
    }
  }

  @Override
  public List<ITask> getTasksAtDate(IDate date) throws IllegalArgumentException {
    if (date == null) {
      throw new IllegalArgumentException("the date must be non-null");
    }

    // convert iDate to YYYY-MM-DD
    String dateFormatted = reformatDate(date);

    List<ITask> tasksAtDate = new ArrayList<>();
    // add each task to the tasksAtDate list

    try {
      // select all task_id's from Dates table
      myStmt = myConn.prepareStatement("SELECT task_id "
          + "FROM Dates "
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
          tasksAtDate.add(new PlannerTask(taskResult.getString("description"), taskResult.getBoolean("status")));
        }
      }

    } catch (Exception exc) {
      exc.printStackTrace();
    }

    // return a copy?
    return tasksAtDate;
  }

  @Override
  public void setTaskAsCompleted(ITask task, IDate date)
      throws IllegalArgumentException, IllegalStateException {
    if (task == null || date == null) {
      throw new IllegalArgumentException("task and date must be non-null");
    }

    // convert iDate to YYYY:MM:DD
    String dateFormatted = reformatDate(date);
    String taskDescription = task.getDescription();
    boolean taskStatus = task.getStatus();

    try {
      // find the task_id's that correspond to the given date

      // for each task_id that was returned, return the task_id if the task status and description match
      // the given ones

    } catch (Exception exc) {
      exc.printStackTrace();
    }

  }

  @Override
  public void setTaskAsIncomplete(ITask task, IDate date)
      throws IllegalArgumentException, IllegalStateException {
    if (task == null || date == null) {
      throw new IllegalArgumentException("task and date must be non-null");
    }

    // check that date exists

    // check that task exists at date


    // convert iDate to YYYY:MM:DD
    String dateFormatted = reformatDate(date);
    String taskDescription = task.getDescription();
    boolean taskStatus = task.getStatus();

    // check that the date is present

    // check that the task is present

    // set the status of the task to false
  }

  @Override
  public void updateTaskDescription(ITask task, IDate date, String description)
      throws IllegalArgumentException, IllegalStateException {
    if (task == null || date == null || description == null) {
      throw new IllegalArgumentException("task, date, and description must be non-null");
    }

    // check date is present

    // check task is present

    // check string is valid (fits within the 200 character limit in db?)

    // convert iDate to YYYY:MM:DD
    String dateFormatted = reformatDate(date);
  }

  // takes an IDate as an argument and returns a newly formatted date as a String in the form
  // of YYYY:MM:DD
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
  public void moveTask(ITask iTask, IDate initialDate, IDate newDate)
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
  public List<ITask> getAllTasks() {
    return null; // needs db
  }
}
