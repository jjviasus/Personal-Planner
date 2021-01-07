package model.planner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.date.IDate;
import model.task.ITask;
import model.theme.ITheme;
import java.sql.*;

public class DataBasePlannerModel extends SimplePlannerModel { // don't need to rewrite getCurrentDate
  String url = "jdbc:mysql://localhost:3306/_";
  String user = "_";
  String password = "_";

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
    // convert iDate to YYYY:MM:DD
    String dateFormatted = "";
    String taskDescription = task.getDescription();
    boolean taskStatus = task.getStatus();

    // add the task at the given date

    // do we have to check if the dateFormatted is present in the data base already?
    // if present, add the task
    // if not present, add the date and then add the task
  }

  @Override
  public void removeTask(ITask task, IDate date) throws IllegalArgumentException {
    // convert iDate to YYYY:MM:DD
    String dateFormatted = "";
    String taskDescription = task.getDescription();
    boolean taskStatus = task.getStatus();

    // remove the task at the given date

    // how do we know if the task is not present in the db?

    // how do we know if the date is not present in the db?
  }

  @Override
  public List<ITask> getTasksAtDate(IDate iDate) throws IllegalArgumentException {
    // convert iDate to YYYY:MM:DD
    String dateFormatted = "";

    List<ITask> tasksAtDate = new ArrayList<>();
    // add each task to the tasksAtDate list

    return null;
  }

  @Override
  public void setTaskAsCompleted(ITask task, IDate date)
      throws IllegalArgumentException, IllegalStateException {
    // convert iDate to YYYY:MM:DD
    String dateFormatted = "";
    String taskDescription = task.getDescription();
    boolean taskStatus = task.getStatus();

    // check that the date is present

    // check that the task is present

    // set the status of the task to true
  }

  @Override
  public void setTaskAsIncomplete(ITask task, IDate date)
      throws IllegalArgumentException, IllegalStateException {
    // convert iDate to YYYY:MM:DD
    String dateFormatted = "";
    String taskDescription = task.getDescription();
    boolean taskStatus = task.getStatus();

    // check that the date is present

    // check that the task is present

    // set the status of the task to false
  }

  @Override
  public void updateTaskDescription(ITask task, IDate date, String description)
      throws IllegalArgumentException, IllegalStateException {
    // check date is present

    // check task is present

    // check string is valid / non-null
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
