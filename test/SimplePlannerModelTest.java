import static org.junit.Assert.*;
// TODO update tests so the tasks are not strings and are task objects themselves
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.date.PlannerDate;
import model.planner.IPlannerModel;
import model.planner.SimplePlannerModel;
import model.task.PlannerTask;
import model.theme.PlannerTheme;
import org.junit.Test;

public class SimplePlannerModelTest {
  // addTask
  @Test
  public void addTask() {
    IPlannerModel<PlannerTheme, PlannerDate, PlannerTask> model = new SimplePlannerModel();
    // check model has no tasks
    assertTrue(model.getAllTasks().isEmpty());
    // add the task
    model.addTask(new PlannerTask("Study"), new PlannerDate(11,11,2000));
    // check the model has the task
    assertEquals(Arrays.asList("Study (incomplete)"), model.getAllTasks());

    // add another task same date
    model.addTask(new PlannerTask("Run"), new PlannerDate(11,11,2000));
    // check the model has the task
    assertEquals(Arrays.asList("Study", "Run"), model.getAllTasks());

    // add another task later date
    model.addTask(new PlannerTask("Eat"), new PlannerDate(3,1,2020));
    // check the model has the task
    assertEquals(Arrays.asList("Study", "Run", "Eat"), model.getAllTasks());

    // add another task earlier date
    model.addTask(new PlannerTask("Sleep"), new PlannerDate(4,24,1990));
    assertEquals(Arrays.asList("Sleep", "Study", "Run", "Eat"), model.getAllTasks());
  }

  // addTask invalid
  @Test
  public void addTaskInvalid() {
    IPlannerModel<PlannerTheme, PlannerDate, PlannerTask> model = new SimplePlannerModel();

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
    IPlannerModel<PlannerTheme, PlannerDate, PlannerTask> model = new SimplePlannerModel();

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
    assertEquals(new ArrayList<>(Arrays.asList("Roam the past (incomplete), Sleep (incomplete), Sleep again (incomplete), Run (incomplete), Run again (incomplete), Roam the future (incomplete)")), model.getAllTasks());
  }

  // removeTask
  @Test
  public void removeTask() {
    IPlannerModel<PlannerTheme, PlannerDate, PlannerTask> model = new SimplePlannerModel();

    // add a task
    model.addTask(new PlannerTask("Run"), new PlannerDate(11,11,2000));
    // check its there
    assertEquals(Arrays.asList("Run (incomplete)"), model.getAllTasks());
    // remove it
    model.removeTask(new PlannerTask("Run"), new PlannerDate(11,11,2000));
    // check it was remove
    assertTrue(model.getAllTasks().isEmpty());

    // add tasks different days
    model.addTask(new PlannerTask("Run"), new PlannerDate(11,11,2000));
    model.addTask(new PlannerTask("Run again"), new PlannerDate(1,12,2002));
    // check its there
    assertEquals(Arrays.asList("Run", "Run again"), model.getAllTasks());
    // remove it
    model.removeTask(new PlannerTask("Run"), new PlannerDate(11,11,2000));
    // check it was remove
    assertEquals(Arrays.asList("Run again"), model.getAllTasks());
  }

  // removeTask invalid
  @Test
  public void removeTaskInvalid() {
    IPlannerModel<PlannerTheme, PlannerDate, PlannerTask> model = new SimplePlannerModel();

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
    IPlannerModel<PlannerTheme, PlannerDate, PlannerTask> model = new SimplePlannerModel();

    // move a task to a new date with no tasks in it
    model.addTask(new PlannerTask("Run"), new PlannerDate(11,11,2000));
    assertEquals(Arrays.asList("Run (incomplete)"), model.getTasksAtDate(new PlannerDate(11,11,2000)));
    model.moveTask(new PlannerTask("Run"), new PlannerDate(11,11,2000), new PlannerDate(11,12,2000));
    assertEquals(Arrays.asList("Run"), model.getTasksAtDate(new PlannerDate(11,12,2000)));
    assertTrue(model.getTasksAtDate(new PlannerDate(11,11,2000)).isEmpty());

    // move a task to a date that already has tasks in it
    model.addTask(new PlannerTask("Study"), new PlannerDate(11,11,2000));
    model.addTask(new PlannerTask("Work"), new PlannerDate(11,11,2000));
    model.moveTask(new PlannerTask("Study"), new PlannerDate(11,11,2000), new PlannerDate(11,12,2000));
    assertEquals(Arrays.asList("Run", "Study"), model.getTasksAtDate(new PlannerDate(11,12,2000)));
    assertEquals(Arrays.asList("Work"), model.getTasksAtDate(new PlannerDate(11,11,2000)));
  }

  // moveTask invalid
  @Test
  public void moveTaskInvalid() {
    IPlannerModel<PlannerTheme, PlannerDate, PlannerTask> model = new SimplePlannerModel();
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

  // getTasksAtDate
  @Test
  public void getTasksAtDate() {
    IPlannerModel<PlannerTheme, PlannerDate, PlannerTask> model = new SimplePlannerModel();
    model.addTask(new PlannerTask("Run"), new PlannerDate(11,11,2000));
    model.addTask(new PlannerTask("Eat"), new PlannerDate(11,12,2000));
    model.addTask(new PlannerTask("Eat"), new PlannerDate(11,12,2000));
    model.addTask(new PlannerTask("Eat again"), new PlannerDate(11,12,2000));

    assertEquals(Arrays.asList("Run (incomplete)"), model.getTasksAtDate(new PlannerDate(11,11,2000)));
    assertEquals(Arrays.asList("Eat", "Eat", "Eat again"), model.getTasksAtDate(new PlannerDate(11,12,2000)));
  }

  // getTasksAtDate invalid
  @Test
  public void getTasksAtDateInvalid() {
    IPlannerModel<PlannerTheme, PlannerDate, PlannerTask> model = new SimplePlannerModel();

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

  // getTotalPoints

  // getUserName

  // setUserName

  // setUserName invalid

  // getThemes

  // setTheme

  // setTheme invalid
}