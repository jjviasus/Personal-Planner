import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.awt.Color;
import java.security.cert.PolicyNode;
import java.util.ArrayList;
import java.util.Arrays;
import model.date.IDate;
import model.date.PlannerDate;
import model.planner.IPlannerModel;
import model.planner.SimplePlannerModel;
import model.task.ITask;
import model.task.PlannerTask;
import model.theme.ITheme;
import model.theme.PlannerTheme;
import org.junit.Test;

public class SimplePlannerModelTest {
  // addTask
  @Test
  public void addTask() {
    IPlannerModel<ITheme, IDate, ITask> model = new SimplePlannerModel();
    // check model has no tasks
    assertTrue(model.getAllTasks().isEmpty());
    // add the task
    model.addTask(new PlannerTask("Study"), new PlannerDate(11,11,2000));
    // check the model has the task
    assertEquals(Arrays.asList(new PlannerTask("Study")), model.getAllTasks());

    // add another task same date
    model.addTask(new PlannerTask("Run"), new PlannerDate(11,11,2000));
    // check the model has the task
    assertEquals(Arrays.asList(new PlannerTask("Study"), new PlannerTask("Run")), model.getAllTasks());

    // add another task later date
    model.addTask(new PlannerTask("Eat"), new PlannerDate(3,1,2020));
    // check the model has the task
    assertEquals(Arrays.asList(new PlannerTask("Study"), new PlannerTask("Run"), new PlannerTask("Eat")), model.getAllTasks());

    // add another task earlier date
    model.addTask(new PlannerTask("Sleep"), new PlannerDate(4,24,1990));
    assertEquals(Arrays.asList(new PlannerTask("Sleep"),new PlannerTask("Study"), new PlannerTask("Run"), new PlannerTask("Eat")), model.getAllTasks());
  }

  // addTask invalid
  @Test
  public void addTaskInvalid() {
    IPlannerModel<ITheme, IDate, ITask> model = new SimplePlannerModel();

    // null task
    try {
      model.addTask(null, new PlannerDate(11,11,2000));
      // exception not caught
      fail();
    } catch (IllegalArgumentException e) {
      // exception caught
    }

    // null date
    try {
      model.addTask(new PlannerTask("Run"), null);
      // exception not caught
      fail();
    } catch (IllegalArgumentException e) {
      // exception caught
    }
  }

  // getAllTasks
  @Test
  public void getAllTasks() {
    IPlannerModel<ITheme, IDate, ITask> model = new SimplePlannerModel();

    // get tasks on empty list of tasks
    assertTrue(model.getAllTasks().isEmpty());

    // add some tasks to varying days
    model.addTask(new PlannerTask("Run"), new PlannerDate(11,11,2000));
    model.addTask(new PlannerTask("Run again"), new PlannerDate(11,11,2000));
    model.addTask(new PlannerTask("Sleep"), new PlannerDate(1,11,2000));
    model.addTask(new PlannerTask("Sleep again"), new PlannerDate(1,12,2000));
    model.addTask(new PlannerTask("Roam the future"), new PlannerDate(11,11,2030));
    model.addTask(new PlannerTask("Roam the past"), new PlannerDate(6,1,1980));

    // get the tasks
    assertEquals(new ArrayList<>(Arrays.asList(new PlannerTask("Roam the past"), new PlannerTask("Sleep"), new PlannerTask("Sleep again"), new PlannerTask("Run"), new PlannerTask("Run again"), new PlannerTask("Roam the future"))), model.getAllTasks());
  }

  // removeTask
  @Test
  public void removeTask() {
    IPlannerModel<ITheme, IDate, ITask> model = new SimplePlannerModel();

    // add a task
    model.addTask(new PlannerTask("Run"), new PlannerDate(11,11,2000));
    // check its there
    assertEquals(Arrays.asList(new PlannerTask("Run")), model.getAllTasks());
    // remove it
    model.removeTask(new PlannerTask("Run"), new PlannerDate(11,11,2000));
    // check it was remove
    assertTrue(model.getAllTasks().isEmpty());

    // add tasks different days
    model.addTask(new PlannerTask("Run"), new PlannerDate(11,11,2000));
    model.addTask(new PlannerTask("Run again"), new PlannerDate(1,12,2002));
    // check its there
    assertEquals(Arrays.asList(new PlannerTask("Run"), new PlannerTask("Run again")), model.getAllTasks());
    // remove it
    model.removeTask(new PlannerTask("Run"), new PlannerDate(11,11,2000));
    // check it was remove
    assertEquals(Arrays.asList(new PlannerTask("Run again")), model.getAllTasks());
  }

  // removeTask invalid
  @Test
  public void removeTaskInvalid() {
    IPlannerModel<ITheme, IDate, ITask> model = new SimplePlannerModel();

    // attempt to remove null task
    try {
      model.removeTask(null, new PlannerDate(11,11,2000));
      // exception not caught
      fail();
    } catch (IllegalArgumentException e) {
      // exception caught
    }

    // attempt to remove null date
    try {
      model.removeTask(new PlannerTask("Does not exist"), null);
      // exception not caught
      fail();
    } catch (IllegalArgumentException e) {
      // exception caught
    }

    // attempt to remove a task when the task list is empty
    try {
      model.removeTask(new PlannerTask("Does not exist"), new PlannerDate(11,11,2000));
      // exception not caught
      fail();
    } catch (IllegalArgumentException e) {
      // exception caught
    }

    model.addTask(new PlannerTask("Run"), new PlannerDate(11,11,2000));

    // attempt to remove a task where the task doesn't exist
    try {
      model.removeTask(new PlannerTask("Does not exist"), new PlannerDate(11,11,2000));
      // exception not caught
      fail();
    } catch (IllegalArgumentException e) {
      // exception caught
    }

    // attempt to remove a task where the date doesn't exist
    try {
      model.removeTask(new PlannerTask("Run"), new PlannerDate(11,12,2000));
      // exception not caught
      fail();
    } catch (IllegalArgumentException e) {
      // exception caught
    }

  }

  // moveTask
  @Test
  public void moveTask() {
    IPlannerModel<ITheme, IDate, ITask> model = new SimplePlannerModel();

    // move a task to a new date with no tasks in it
    model.addTask(new PlannerTask("Run"), new PlannerDate(11,11,2000));
    assertEquals(Arrays.asList(new PlannerTask("Run")), model.getTasksAtDate(new PlannerDate(11,11,2000)));
    model.moveTask(new PlannerTask("Run"), new PlannerDate(11,11,2000), new PlannerDate(11,12,2000));
    assertEquals(Arrays.asList(new PlannerTask("Run")), model.getTasksAtDate(new PlannerDate(11,12,2000)));
    assertTrue(model.getTasksAtDate(new PlannerDate(11,11,2000)).isEmpty());

    // move a task to a date that already has tasks in it
    model.addTask(new PlannerTask("Study"), new PlannerDate(11,11,2000));
    model.addTask(new PlannerTask("Work"), new PlannerDate(11,11,2000));
    model.moveTask(new PlannerTask("Study"), new PlannerDate(11,11,2000), new PlannerDate(11,12,2000));
    assertEquals(Arrays.asList(new PlannerTask("Run"), new PlannerTask("Study")), model.getTasksAtDate(new PlannerDate(11,12,2000)));
    assertEquals(Arrays.asList(new PlannerTask("Work")), model.getTasksAtDate(new PlannerDate(11,11,2000)));
  }

  // moveTask invalid
  @Test
  public void moveTaskInvalid() {
    IPlannerModel<ITheme, IDate, ITask> model = new SimplePlannerModel();
    model.addTask(new PlannerTask("Run"), new PlannerDate(11,11,2000));
    model.addTask(new PlannerTask("Eat"), new PlannerDate(11,12,2000));

    // null task
    try {
      model.moveTask(null, new PlannerDate(11,11,2000), new PlannerDate(11,12,2000));
      fail();
    } catch (IllegalArgumentException e) {
      // exception caught
    }

    // null initial date
    try {
      model.moveTask(new PlannerTask("Run"), null, new PlannerDate(11,12,2000));
      fail();
    } catch (IllegalArgumentException e) {
      // exception caught
    }

    // null new date
    try {
      model.moveTask(new PlannerTask("Run"), new PlannerDate(11,11,2000), null);
      fail();
    } catch (IllegalArgumentException e) {
      // exception caught
    }

    // initial date does not exist
    try {
      model.moveTask(new PlannerTask("Run"), new PlannerDate(11,13,2000), new PlannerDate(11,12,2000));
      fail();
    } catch (IllegalArgumentException e) {
      // exception caught
    }

    // task does not exist
    try {
      model.moveTask(new PlannerTask("Does not exist"), new PlannerDate(11,11,2000), new PlannerDate(11,12,2000));
      fail();
    } catch (IllegalArgumentException e) {
      // exception caught
    }

    // same date
    try {
      model.moveTask(new PlannerTask("Run"), new PlannerDate(11,11,2000), new PlannerDate(11,11,2000));
      fail();
    } catch (IllegalArgumentException e) {
      // exception caught
    }
  }

  // getTotalPoints
  @Test
  public void getTotalPoints() {
    IPlannerModel<ITheme, IDate, ITask> model = new SimplePlannerModel();

    // zero points
    assertEquals(0, model.getTotalPoints());

    // non-zero points
    model.addTask(new PlannerTask("Run"), new PlannerDate(11,11,2000));
    model.addTask(new PlannerTask("Eat"), new PlannerDate(11,11,2000));
    model.addTask(new PlannerTask("Old task"), new PlannerDate(11,11,1950));
    // check that the points have not been added
    assertEquals(0, model.getTotalPoints());
    // mark the old task as complete and check the points
    model.setTaskAsCompleted(new PlannerTask("Old task"), new PlannerDate(11,11,1950));
    assertEquals(true, model.tasksCompleteAtDate(new PlannerDate(11,11,1950)));
    assertEquals(10, model.getTotalPoints());
    // mark only one task on 11/11/2000 as complete, not both
    model.setTaskAsCompleted(new PlannerTask("Run"), new PlannerDate(11,11,2000));
    assertEquals(10, model.getTotalPoints());
    assertEquals(false, model.tasksCompleteAtDate(new PlannerDate(11,11,2000)));
    // mark both tasks on 11/11/2000 as complete
    model.setTaskAsCompleted(new PlannerTask("Eat"), new PlannerDate(11,11,2000));
    assertEquals(20, model.getTotalPoints());
    assertEquals(true, model.tasksCompleteAtDate(new PlannerDate(11,11,2000)));
  }

  // getUserName
  @Test
  public void getUserName() {
    IPlannerModel<ITheme, IDate, ITask> model = new SimplePlannerModel();

    // default user's name
    assertEquals("Enter your name!", model.getUserName());

    // set user's name
    model.setUserName("Justin");
    assertEquals("Justin", model.getUserName());
  }

  // setUserName
  @Test
  public void setUserName() {
    IPlannerModel<ITheme, IDate, ITask> model = new SimplePlannerModel();

    // set user's name
    assertEquals("Enter your name!", model.getUserName());
    model.setUserName("Justin");
    assertEquals("Justin", model.getUserName());

    // update it
    model.setUserName("Viasus");
    assertEquals("Viasus", model.getUserName());
  }

  // setUserName invalid
  @Test
  public void setUserNameInvalid() {
    IPlannerModel<ITheme, IDate, ITask> model = new SimplePlannerModel();

    // set user's name to null
    assertEquals("Enter your name!", model.getUserName());

    try {
      model.setUserName(null);
      fail();
      // exception not thrown
    } catch (IllegalArgumentException e) {
      // exception thrown
    }

    assertEquals("Enter your name!", model.getUserName());
  }

  // getAllThemes !!!
  @Test
  public void getAllThemes() {
    IPlannerModel<ITheme, IDate, ITask> model = new SimplePlannerModel();

    // TODO: themes are not ordered in the tree map by the Integer (their cost) they are ordered according to the compareTo method in PlannerTheme class

    // TODO: fix why the equals is not working

    // dark theme
    ITheme light = new PlannerTheme("Light", "Times New Roman",
        new Color(30,30,30), 12, new Color(250,250,250),
        new Color(200,200,200), new Color(150,150,150), 0);
    ITheme dark = new PlannerTheme("Dark", "Courier New",
        new Color(255,255,255), 12, new Color(30,30,30),
        new Color(50,50,50), new Color(75,75,75), 10);
    // get themes
    //assertTrue(model.getAllThemes().contains(light));

    // attempt to modify themes

    // check that they haven't been modified
  }

  // setCurrentTheme

  // setCurrentTheme invalid

  // getCurrentTheme

  // getTasksAtDate
  @Test
  public void getTasksAtDate() {
    IPlannerModel<ITheme, IDate, ITask> model = new SimplePlannerModel();
    model.addTask(new PlannerTask("Run"), new PlannerDate(11,11,2000));
    model.addTask(new PlannerTask("Eat"), new PlannerDate(11,12,2000));
    model.addTask(new PlannerTask("Eat"), new PlannerDate(11,12,2000));
    model.addTask(new PlannerTask("Eat again"), new PlannerDate(11,12,2000));

    assertEquals(Arrays.asList(new PlannerTask("Run")), model.getTasksAtDate(new PlannerDate(11,11,2000)));
    assertEquals(Arrays.asList(new PlannerTask("Eat"), new PlannerTask("Eat"), new PlannerTask("Eat again")), model.getTasksAtDate(new PlannerDate(11,12,2000)));
  }

  // getTasksAtDate invalid
  @Test
  public void getTasksAtDateInvalid() {
    IPlannerModel<ITheme, IDate, ITask> model = new SimplePlannerModel();

    // null date
    try {
      model.getTasksAtDate(null);
      // exception not thrown
      fail();
    } catch (IllegalArgumentException e) {
      // exception thrown
    }

    // a date that has no tasks
    try {
      model.getTasksAtDate(new PlannerDate(11,11,2000));
      // exception not thrown
      fail();
    } catch (IllegalArgumentException e) {
      // exception thrown
    }
  }

  // setTaskAsCompleted
  @Test
  public void setTaskAsCompleted() {
    IPlannerModel<ITheme, IDate, ITask> model = new SimplePlannerModel();

    ITask run = new PlannerTask("Run");
    IDate date = new PlannerDate(11,11,2000);

    // add task
    model.addTask(run, date);
    // check it is not complete
    assertFalse(model.tasksCompleteAtDate(date));
    // mark it as complete
    model.setTaskAsCompleted(run, date);
    assertTrue(model.tasksCompleteAtDate(date));

    // mark an already complete task as complete
    model.setTaskAsCompleted(run, date);
    // check the completion status has not changed
    assertTrue(model.tasksCompleteAtDate(date));
  }

  // setTaskAsCompleted Invalid

  // setTaskAsIncomplete
  @Test
  public void setTaskAsIncomplete() {
    IPlannerModel<ITheme, IDate, ITask> model = new SimplePlannerModel();

    ITask run = new PlannerTask("Run");
    IDate date = new PlannerDate(11,11,2000);

    // add task
    model.addTask(run, date);
    // check it is not complete
    assertFalse(model.tasksCompleteAtDate(date));
    // mark it as complete
    model.setTaskAsCompleted(run, date);
    assertTrue(model.tasksCompleteAtDate(date));
    // mark it as incomplete
    model.setTaskAsIncomplete(run, date);
    // check it is not complete
    assertFalse(model.tasksCompleteAtDate(date));

    // mark an already incomplete task as incomplete
    model.setTaskAsIncomplete(run, date);
    // check the completion status has not changed
    assertFalse(model.tasksCompleteAtDate(date));
  }

  // setTaskAsIncomplete Invalid

  // buyTheme

  // buyTheme invalid

  // tasksCompleteAtDate

  // tasksCompleteAtDate Invalid


}